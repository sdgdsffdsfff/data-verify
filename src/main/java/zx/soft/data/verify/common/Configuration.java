package zx.soft.data.verify.common;

import zx.soft.data.verify.http.Proxy;
import zx.soft.data.verify.io.MysqlConf;

public class Configuration {

    // maximum tasks
    private int capacity;
    
    private String solrWriteAddr;
    private String solrReadAddr;
    
    private MysqlConf mysqlConf;
    private Proxy proxy;
    
    private int queueSize;
    

    public int getQueueSize() {
        return queueSize;
    }

    public int getQueueSize(int defaultSize) {
        if (queueSize == 0)
            queueSize = defaultSize;
        return queueSize;
    }


    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }


    public Proxy getProxy() {
        return proxy;
    }


    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }


    public int getCapacity() {
        return capacity;
    }


    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }


    public String getSolrWriteAddr() {
        return solrWriteAddr;
    }


    public void setSolrWriteAddr(String solrTaget) {
        this.solrWriteAddr = solrTaget;
    }


    public String getSolrReadAddr() {
        return solrReadAddr;
    }


    public void setSolrReadAddr(String solrReadAddr) {
        this.solrReadAddr = solrReadAddr;
    }


    public MysqlConf getMysqlConf() {
        return mysqlConf;
    }


    public void setMysqlConf(MysqlConf mysqlConf) {
        this.mysqlConf = mysqlConf;
    }
    
    
    
    
}
