package zx.soft.data.verify.core;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.data.verify.common.Record;
import zx.soft.data.verify.http.HttpAdvanced;
import zx.soft.data.verify.io.MysqlClient;
import zx.soft.data.verify.io.SolrClient;
import zx.soft.data.verify.io.WriteException;
import zx.soft.utils.log.LogbackUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HandlerThread implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(HandlerThread.class);

	private HttpAdvanced http;

	private MysqlClient mysqlClient;
	private SolrClient solrClient;

	private String filename;
	private List<Record> records;

	public HandlerThread(HttpAdvanced http, SolrClient solrClient, MysqlClient mysqlClient, String filename,
			List<Record> records) {
		this.http = http;
		this.solrClient = solrClient;
		this.mysqlClient = mysqlClient;
		this.records = records;
		this.filename = filename;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void run() {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		logger.info(filename + " fetch　record count:" + records.size());
		for (Record record : records) {
			try {
				String id = record.getRecordId();
				Map indexModel = solrClient.read(id);

				if (indexModel == null) {
					logger.warn("No record find in solr index: " + id);
					continue;
				}

				String url = (String) indexModel.get("url");

				double country_code = indexModel.get("country_code") == null ? 1 : (Double) indexModel
						.get("country_code");
				if (country_code == 0 || url == null || url.matches("\\S+(twitter.com|weibo.com)\\S+")) {
					String json = gson.toJson(indexModel, Map.class);
					mysqlClient.write(filename, record.getKeyword(), json);
					continue;
				}

				Document doc = http.get(url);
				String content = "";
				if (doc == null) {
					continue;
				}

				content = TextExtract.parse(doc.html());
				if (content == null) {
					content = "";
				}

				indexModel.put("content", content);
				String json = gson.toJson(indexModel, Map.class);
				if (record.getKeyword() == null || record.getKeyword().trim().length() == 0) {
					if (content.trim().length() != 0)
						mysqlClient.write(filename, record.getKeyword(), json);
					continue;
				}

				String[] words = record.getKeyword().split("\\s+");
				String title = indexModel.get("title") == null ? "" : (String) indexModel.get("title");
				String tc = title + content;
				boolean contain = true;
				for (String word : words) {
					if (!tc.contains(word.toUpperCase()) && !tc.contains(word.toLowerCase())) {
						logger.info("`" + url + "` 不包含关键字:" + word);
						contain = false;
						break;
					}
				}
				if (contain)
					mysqlClient.write(filename, record.getKeyword(), json);
			} catch (WriteException | URISyntaxException | IOException e) {
				logger.error("", e);
			} catch (Exception e) {
				logger.error("Catch exception", LogbackUtil.expection2Str(e));
			}
		}
		logger.info("Finish fetch.");
	}

}
