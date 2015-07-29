package zx.soft.data.verify.io;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.apache.http.client.utils.URIBuilder;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.data.verify.common.RecordInfoCollection;
import zx.soft.data.verify.http.HttpAdvanced;
import zx.soft.data.verify.http.HttpException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SolrClient {

	private static Logger logger = LoggerFactory.getLogger(SolrClient.class);

	private HttpAdvanced http;
	private String writeAddr;
	private String readAddr;

	public SolrClient(HttpAdvanced http, String writeAddr, String readAddr) {
		this.http = http;
		this.writeAddr = writeAddr;
		this.readAddr = readAddr;
	}

	public Map<?, ?> read(String id) throws IOException, URISyntaxException, HttpException {
		String para_id = String.format("id:%s", id);
		URI uri = new URIBuilder().setScheme("http").setHost(readAddr).setPath("/solr/sentiment/select")
				.setParameter("q", para_id).setParameter("wt", "json")
				// .setParameter("indent", "true")
				.build();
		Document doc = http.get(uri.toString());
		String json = doc.body().text();
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();

		JsonObject resp = gson.fromJson(json, JsonObject.class);
		JsonArray arr = resp.get("response").getAsJsonObject().get("docs").getAsJsonArray();

		if (arr == null || arr.size() == 0)
			return null;

		Map<?, ?> map = gson.fromJson(arr.get(0), Map.class);
		return map;
	}

	public void write(RecordInfoCollection co) throws IOException, URISyntaxException, HttpException {
		URI uri = new URIBuilder().setScheme("http").setHost(writeAddr).setPath("/sentiment/index").build();
		String doc = http.post(uri.toString(), co.toString());
		logger.info(doc);
	}

}
