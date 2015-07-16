package zx.soft.data.verify.api.impl;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import zx.soft.data.verify.api.BotManager;
import zx.soft.data.verify.common.Configuration;
import zx.soft.data.verify.common.Record;
import zx.soft.data.verify.common.RequestParam;
import zx.soft.data.verify.common.RetCode;
import zx.soft.data.verify.common.VerifiedData;
import zx.soft.data.verify.common.VerifiedDataCollection;
import zx.soft.data.verify.core.HandlerThread;
import zx.soft.data.verify.http.Http;
import zx.soft.data.verify.io.MysqlClient;
import zx.soft.data.verify.io.MysqlConf;
import zx.soft.data.verify.io.SolrClient;

import com.mysql.jdbc.Driver;
import com.thoughtworks.xstream.XStream;

public class RAMBotManager implements BotManager {

    private static ThreadPoolExecutor exec;
    private static int capacity;
    private static Http http;
    private static SolrClient solrClient;
    private static MysqlClient mysqlClient;

    static {
        XStream xStream = new XStream();
        InputStream is = RAMBotManager.class.getClassLoader().getResourceAsStream("conf.xml");
        xStream.alias("configuration", Configuration.class);
        Configuration conf = (Configuration) xStream.fromXML(is);
        capacity = conf.getCapacity();

        exec = new ThreadPoolExecutor(10, capacity, 60,
                        TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(capacity));

        solrClient = new SolrClient(new Http(null), conf.getSolrWriteAddr(), conf.getSolrReadAddr());
        http = new Http(null);

        Driver driver = null;
        try {
            driver = new Driver();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MysqlConf mc = conf.getMysqlConf();
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource(driver,
                        mc.getUrl(), mc.getUsername(), mc.getPassword());

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
        return co;
    }

}
