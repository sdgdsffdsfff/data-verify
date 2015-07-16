package zx.soft.data.verify.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import javax.net.ssl.SSLException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http {
    private static Logger LOG = LoggerFactory.getLogger(Http.class);
    private HttpClient http;

    public Http(Proxy proxy) {
        HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount,
                            HttpContext context) {
                if (executionCount >= 5 || exception instanceof InterruptedIOException
                                || exception instanceof UnknownHostException
                                || exception instanceof ConnectTimeoutException
                                || exception instanceof SSLException)
                    return false;
                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
                if (idempotent) {
                    return true;
                }
                return false;
            }
        };

        CredentialsProvider credsProvider = null;
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
        DefaultProxyRoutePlanner routePlanner = null;
        if (proxy != null) {
            HttpHost _proxy = new HttpHost(proxy.getIp(), proxy.getPort());
            routePlanner = new DefaultProxyRoutePlanner(_proxy);
            if (proxy.getUsername() != null && proxy.getUsername().trim().length() != 0) {
                credsProvider = new BasicCredentialsProvider();
                credsProvider.setCredentials(AuthScope.ANY,
                                new UsernamePasswordCredentials(proxy.getUsername(),
                                                proxy.getPassword()));
            }
        }
        // RequestConfig config = new Request;
        // ConnectionSocketFactory plainsf = null;
        // LayeredConnectionSocketFactory sslsf = null;
        // Registry<ConnectionSocketFactory> r =
        // RegistryBuilder.<ConnectionSocketFactory>create()
        // .register("http", plainsf)
        // .register("https", sslsf)
        // .build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        // Increase max total connection to 200
        cm.setMaxTotal(200);
        // Increase default max connection per route to 20
        cm.setDefaultMaxPerRoute(20);

        http = HttpClients.custom().setRetryHandler(myRetryHandler)
                        .setRoutePlanner(routePlanner).setConnectionManager(cm)
                        .setDefaultCredentialsProvider(credsProvider)
                        .setRedirectStrategy(redirectStrategy).build();

    }

    public Document get(HttpGet get) throws HttpException, IOException {

        get.setHeader("User-Agent",
                        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.90 Safari/537.36");
        get.setHeader("Accept-Encoding","gzip, deflate, sdch");
        get.setHeader("Accept-Language","en-US,en;q=0.8,zh-CN;q=0.6,zh;q=0.4,zh-TW;q=0.2");
        URI uri = get.getURI();
        HttpClientContext context = HttpClientContext.create();

        HttpResponse resp = null;
        Document document = null;
        try {
            resp = http.execute(get, context);
            int code = resp.getStatusLine().getStatusCode();
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
                LOG.warn("400 Bad request: " + uri.toString());
            } else if (code == 401) {
                LOG.warn("401 Authentication Required: " + uri.toString());
            } else if (code == 403) {
                LOG.warn(" 403 Forbidden: " + uri.toString());
            } else if (code == 404) {
                LOG.warn("404 not found: " + uri.toString());
            } else if (code == 410) { // permanently GONE
                LOG.warn("410 Permanently gone: " + uri.toString());
            } else if (code == 502 || code == 504) {
                LOG.warn("Bad Gateway: " + uri.toString());
            } else if (code == -2) {
                LOG.warn("Connection reset: " + uri.toString());
            } else {
                LOG.warn(uri.toString() + " return code: " + code);
            }

            if (!deal) {
                throw new HttpException("code:" + code);
            }

            HttpEntity ent = resp.getEntity();
            ByteBuffer byteData = DataUtil.readToByteBuffer(ent.getContent());
            Header header = ent.getContentEncoding();
            String charset = header == null ? null : header.getValue();
            document = DataUtil.parseByteData(byteData, charset, uri.toString(),
                            Parser.htmlParser());
            byteData.rewind();
        } catch (IOException e) {
            throw new HttpException("访问除错:" + e.getMessage(), e);
        } finally {
            get.releaseConnection();
        }

        return document;
    }

    public Document post(HttpPost post) throws IOException, HttpException {

        URI uri = post.getURI();
        HttpClientContext context = HttpClientContext.create();

        HttpResponse resp = null;
        Document document = null;

        try {
            resp = http.execute(post, context);
            int code = resp.getStatusLine().getStatusCode();
            boolean deal = false;
            if (code == 200 || code == 201) {
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
                LOG.warn("400 Bad request: " + uri.toString());
            } else if (code == 401 || code == 403) { // requires authorization,
                                                     // but
                                                     // no valid auth provided.
                LOG.warn("401 Authentication Required: " + uri.toString());
            } else if (code == 404) {
                LOG.warn("404 not found: " + uri.toString());
            } else if (code == 410) { // permanently GONE
                LOG.warn("410 Permanently gone: " + uri.toString());
            } else if (code == 502 | code == 504) {
                LOG.warn("Bad Gateway: " + uri.toString());
            } else if (code == -2) {
                LOG.warn("Connection reset: " + uri.toString());
            } else {
                LOG.warn(uri.toString() + " return code: " + code);
            }

            if (!deal)
                throw new HttpException("http status code:" + code);

            HttpEntity ent = resp.getEntity();
            ByteBuffer byteData = DataUtil.readToByteBuffer(ent.getContent());
            Header header = ent.getContentEncoding();
            String charset = header == null ? null : header.getValue();
            document = DataUtil.parseByteData(byteData, charset, uri.toString(),
                            Parser.htmlParser());
            byteData.rewind();
        } catch (IOException e) {
            throw new IOException("访问除错:" + e.getMessage());
        } finally {
            post.releaseConnection();
        }
        return document;
    }

}
