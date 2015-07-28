package zx.soft.data.verify.core;

import java.io.IOException;
import java.net.URISyntaxException;

import org.jsoup.nodes.Document;
import org.junit.Test;

import zx.soft.data.verify.http.Http;
import zx.soft.data.verify.http.HttpException;

public class TextExtractTest {

	@Test
	public void test() throws URISyntaxException, IOException, HttpException {
		//        Proxy proxy = new Proxy("192.168.25.254", 28129, "yproxyq", "zproxyx0#");
		Http http = new Http(null);
		Document doc = http.get("http://blog.sina.com.cn/s/blog_148159fab0102vt5n.html");
		//        System.out.println(doc.html());
		String content = TextExtract.parse(doc.html());
		System.out.println(content);
	}

}
