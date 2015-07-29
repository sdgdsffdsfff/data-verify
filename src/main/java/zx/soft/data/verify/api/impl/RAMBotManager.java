package zx.soft.data.verify.api.impl;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import zx.soft.data.verify.api.BotManager;
import zx.soft.data.verify.common.Configuration;
import zx.soft.data.verify.common.RequestParam;
import zx.soft.data.verify.common.RetCode;
import zx.soft.data.verify.common.VerifiedData;
import zx.soft.data.verify.common.VerifiedDataCollection;
import zx.soft.data.verify.core.HandlerThread;
import zx.soft.data.verify.http.HttpAdvanced;
import zx.soft.data.verify.io.MysqlClient;
import zx.soft.data.verify.io.MysqlConf;
import zx.soft.data.verify.io.SolrClient;

import com.mysql.jdbc.Driver;
import com.thoughtworks.xstream.XStream;

public class RAMBotManager implements BotManager {

	private static Logger logger = LoggerFactory.getLogger(RAMBotManager.class);

	private static ThreadPoolExecutor exec;
	private static int capacity;
	private static HttpAdvanced http;
	private static SolrClient solrClient;
	private static MysqlClient mysqlClient;

	static {
		XStream xStream = new XStream();
		InputStream is = RAMBotManager.class.getClassLoader().getResourceAsStream("conf.xml");
		xStream.alias("configuration", Configuration.class);
		Configuration conf = (Configuration) xStream.fromXML(is);
		capacity = conf.getCapacity();

		exec = new ThreadPoolExecutor(10, capacity, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(capacity));

		// 未使用代理
		http = new HttpAdvanced(null);
		solrClient = new SolrClient(http, conf.getSolrWriteAddr(), conf.getSolrReadAddr());

		Driver driver = null;
		try {
			driver = new Driver();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		MysqlConf mc = conf.getMysqlConf();
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource(driver, mc.getUrl(), mc.getUsername(),
				mc.getPassword());

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		mysqlClient = new MysqlClient(jdbcTemplate);
	}

	@Override
	public RetCode upload(RequestParam param) {
		exec.execute(new HandlerThread(http, solrClient, mysqlClient, param.getFilename(), param.getRecs()));
		return new RetCode(0, "success");
	}

	@Override
	public VerifiedDataCollection download(String filename, int start, int rows) {
		VerifiedDataCollection co = mysqlClient.get(filename, start, rows);
		logger.info("Filename:{} original size:{}", filename, co.getRecords().size());
		// 数据返回之前需要作数据去重
		return duplicateByUrl(co);
	}

	public static VerifiedDataCollection duplicateByUrl(VerifiedDataCollection original) {
		List<VerifiedData> records = new ArrayList<>();
		Set<String> set = new TreeSet<>();
		for (VerifiedData record : original.getRecords()) {
			if (!set.contains(record.getRecord().get("url").toString())) {
				records.add(record);
			}
			set.add(record.getRecord().get("url").toString());
		}
		return new VerifiedDataCollection(records.size(), records);
	}

}
