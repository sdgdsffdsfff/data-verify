package zx.soft.data.verify.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.nodes.Document;
import org.junit.Test;

import zx.soft.data.verify.http.Http;
import zx.soft.data.verify.http.HttpException;

public class HttpTest {

    @Test
    public void test() throws URISyntaxException, IOException, HttpException {
//        Proxy proxy = new Proxy("192.168.25.254", 28129, "yproxyq", "zproxyx0#");
        Http http = new Http(null);
        URI uri = new URI("http://www.baidu.com");
        String path = URLEncoder.encode(uri.getPath(),"UTF-8");
//        uri = new URIBuilder().
        HttpGet get = new HttpGet(uri);
        Document doc = http.get(get);
        System.out.println(doc.html());
    }
}
