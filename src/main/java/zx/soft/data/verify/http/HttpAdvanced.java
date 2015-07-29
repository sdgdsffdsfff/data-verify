package zx.soft.data.verify.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zx.soft.utils.http.HttpClientDaoImpl;
import zx.soft.utils.log.LogbackUtil;

public class HttpAdvanced {

	private static Logger logger = LoggerFactory.getLogger(HttpAdvanced.class);

	HttpClientDaoImpl httpClientDaoImpl;
	private Proxy proxy;

	public HttpAdvanced(Proxy proxy) {
		this.proxy = proxy;
		httpClientDaoImpl = new HttpClientDaoImpl();
	}

	public Document get(String url) {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		get.setHeader("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
		get.setHeader("Accept-Encoding", "gzip, deflate, sdch");
		get.setHeader("Accept-Language", "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");
		URI uri = get.getURI();

		CloseableHttpResponse resp = null;
		Document document = null;
		try {
			try {
				resp = httpClient.execute(get);
			} catch (IOException e) {
				logger.error("IOException:{},url:{}", LogbackUtil.expection2Str(e), url);
				return null;
			}
			int code = resp.getStatusLine().getStatusCode();
			boolean deal = false;
			if (code == 200) {
				deal = true;
			} else if (code == 410) { // page is gone
				//
			} else if (code >= 300 && code < 400) { // handle redirect
				// String location = resp.getFirstHeader("Location").getValue();
				// // some broken servers, such as MS IIS, use lowercase header
				// // name...
				// if (location == null)
				// location = resp.getFirstHeader("location").getValue();
				// if (location == null)
				// location = "";
				switch (code) {
				case 300: // multiple choices, preferred value in Location
					break;
				case 305: // use proxy (Location is URL of proxy)
					break;
				case 301: // moved permanently
				case 302: // found (temporarily moved), such as tianya mobile
				case 303: // see other (redirect after POST)
				case 307: // temporary redirect
					break;
				case 304: // not modified
					deal = true;
				default:
				}
				// handle this in the higher layer.
			} else if (code == 400) { // bad request, mark as GONE
				logger.warn("400 Bad request: " + uri.toString());
			} else if (code == 401) {
				logger.warn("401 Authentication Required: " + uri.toString());
			} else if (code == 403) {
				logger.warn("403 Forbidden: " + uri.toString());
			} else if (code == 404) {
				logger.warn("404 not found: " + uri.toString());
			} else if (code == 410) { // permanently GONE
				logger.warn("410 Permanently gone: " + uri.toString());
			} else if (code == 502 || code == 504) {
				logger.warn("Bad Gateway: " + uri.toString());
			} else if (code == -2) {
				logger.warn("Connection reset: " + uri.toString());
			} else {
				logger.warn(uri.toString() + " return code: " + code);
			}

			if (!deal) {
				logger.error("Error code:{}, url:{}", code, url);
				return null;
			}

			HttpEntity ent = resp.getEntity();

			ByteBuffer byteData = null;
			try (InputStream is = ent.getContent();) {
				byteData = DataUtil.readToByteBuffer(is);
			} catch (IllegalStateException | IOException e) {
				logger.error("IOException:{}", LogbackUtil.expection2Str(e));
				return null;
			}
			Header header = ent.getContentEncoding();
			String charset = (header == null) ? null : header.getValue();
			document = DataUtil.parseByteData(byteData, charset, uri.toString(), Parser.htmlParser());
			byteData.rewind();
		} finally {
			//			get.abort();
			get.releaseConnection();
			try {
				if (resp != null) {
					EntityUtils.consume(resp.getEntity());
					resp.close();
				}
				httpClient.close();
			} catch (IOException e) {
				logger.error("IOException:{},url:{}", LogbackUtil.expection2Str(e), url);
			}
		}

		return document;
	}

	public String post(String url, String data) throws IOException, HttpException {
		return httpClientDaoImpl.doPost(url, data);
	}

}
