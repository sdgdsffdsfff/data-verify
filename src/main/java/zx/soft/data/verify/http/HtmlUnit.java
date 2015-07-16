package zx.soft.data.verify.http;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.NameValuePair;

public class HtmlUnit {

    private static Logger LOG = LoggerFactory.getLogger(HtmlUnit.class);
    
    private WebClient client ;

    public HtmlUnit() {
        client = new WebClient(BrowserVersion.FIREFOX_24);
        client.getOptions().setJavaScriptEnabled(true);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setRedirectEnabled(true);
        client.setAjaxController(new NicelyResynchronizingAjaxController());
        // client.getOptions().setTimeout(50000);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setPrintContentOnFailingStatusCode(false);
        client.getCookieManager().setCookiesEnabled(true); // 开启cookie管理

    }

    public String makeRequest(String u) throws IOException, HttpException {

        URL url = new URL(u);

        WebRequest request = new WebRequest(url);
        request.setAdditionalHeader(
                        "User-Agent",
                        "Mozilla/5.0 (X11; Linux i686) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.116 Safari/537.36");
        request.setAdditionalHeader("Accept-Language",
                        "en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");
        request.setAdditionalHeader("Accept-Charset", "utf-8,ISO-8859-1;q=0.7,*;q=0.7");
        request.setAdditionalHeader("Accept",
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        request.setAdditionalHeader("Connection", "keep-alive");

        HtmlPage page = client.getPage(request);

        WebResponse response = page.getWebResponse();
        String charset = response.getContentCharset();
        int code = response.getStatusCode();
        String contentType = response.getContentType();
        List<NameValuePair> pairs = response.getResponseHeaders();
        // for (NameValuePair pair : pairs) {
        // headers.set(pair.getName(), pair.getValue());
        // }
        boolean deal = false;
        if (code == 200) {
            deal = true;
        } else if (code == 410) { // page is gone

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
            LOG.warn("400 Bad request: " + u);
        } else if (code == 401 || code == 403) { 
            LOG.warn("401 Authentication Required: " + u);
        } else if (code == 404) {
            LOG.warn("404 not found: " + u);
        } else if (code == 410) { // permanently GONE
            LOG.warn("410 Permanently gone: " + u);
        } else if (code == 502 || code == 504) {
            LOG.warn("Bad Gateway: " + u);
        } else if (code == -2) {
            LOG.warn("Connection reset: " + u);
        } else {
            LOG.warn(u + " return code: " + code);
        }

        if (!deal) {
            throw new HttpException("code:" + code);
        }

        String html = page.asXml();
        return html;
    }
}
