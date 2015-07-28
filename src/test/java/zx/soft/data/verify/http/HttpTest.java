package zx.soft.data.verify.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.jsoup.nodes.Document;
import org.junit.Test;

public class HttpTest {

	@Test
	public void test() throws URISyntaxException, IOException, HttpException {
		//        Proxy proxy = new Proxy("192.168.25.254", 28129, "yproxyq", "zproxyx0#");
		Http http = new Http(null);
		URI uri = new URI("http://www.baidu.com");
		Document doc = http.get(uri.toString());
		System.out.println(doc.html());
	}

}
