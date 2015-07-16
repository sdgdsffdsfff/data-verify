package zx.soft.data.verify.core;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.jsoup.nodes.Document;
import org.junit.Test;

import zx.soft.data.verify.core.TextExtract;
import zx.soft.data.verify.http.Http;
import zx.soft.data.verify.http.HttpException;
import zx.soft.data.verify.http.Proxy;

public class TextExtractTest {

    @Test
    public void test() throws URISyntaxException, IOException, HttpException {
//        Proxy proxy = new Proxy("192.168.25.254", 28129, "yproxyq", "zproxyx0#");
        Http http = new Http(null);
        URI uri = new URI("http://blog.sina.com.cn/s/blog_148159fab0102vt5n.html");
        HttpGet get = new HttpGet(uri);
        Document doc = http.get(get);
//        System.out.println(doc.html());
        String content = TextExtract.parse(doc.html());
        System.out.println(content);
        
    }
}
