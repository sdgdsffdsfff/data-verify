package zx.soft.data.verify.api;

import org.jsoup.nodes.Document;

import zx.soft.data.verify.core.TextExtract;
import zx.soft.data.verify.http.HttpAdvanced;

public class SpiderDemo {

	public static void main(String[] args) throws Exception {

		String url = "http://falutin.net/2014/04/06/the-two-most-common-solr-performance-blunders-and-a-rant-about-the-dumbification-of-computer-programming/";
		HttpAdvanced http = new HttpAdvanced(null);

		Document doc = http.get(url);
		String content = TextExtract.parse(doc.html());

		System.out.println(content);
	}

}
