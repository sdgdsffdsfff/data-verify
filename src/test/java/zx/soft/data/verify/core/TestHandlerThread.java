package zx.soft.data.verify.core;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import zx.soft.data.verify.common.Record;
import zx.soft.data.verify.core.HandlerThread;
import zx.soft.data.verify.http.Http;
import zx.soft.data.verify.io.MysqlClient;
import zx.soft.data.verify.io.MysqlConf;
import zx.soft.data.verify.io.SolrClient;

import com.mysql.jdbc.Driver;

public class TestHandlerThread {

    @Test
    public void test() throws InterruptedException {
        Http http = new Http(null);
        SolrClient solrClient = new SolrClient(http, "192.168.31.11:8900", "192.168.31.11:8983");
        Driver driver = null;
        try {
            driver = new Driver();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        MysqlConf mc = new MysqlConf("jdbc:mysql://192.168.5.202:3306/crawler","crawler","zxsoft");
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource(driver,
                        mc.getUrl(), mc.getUsername(), mc.getPassword());

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        MysqlClient mysqlClient = new MysqlClient(jdbcTemplate);
        String filename = "test0";
        
        List<Record> records = new ArrayList<Record>();
        Record record = new Record("A8ADC23395058847D3BB84A31678DCD3", "一战 结束后");
        records.add(record);
        Thread t = new Thread(new HandlerThread(http, solrClient, mysqlClient, filename, records));
        t.start();
        t.join();
    }
    
}
