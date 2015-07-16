package zx.soft.data.verify.http;

import java.io.IOException;

import org.junit.Test;

import zx.soft.data.verify.http.HtmlUnit;
import zx.soft.data.verify.http.HttpException;

public class HtmlUnitTest {
    
    private static HtmlUnit htmlUnit = new HtmlUnit();

    @Test
    public void test() throws IOException, HttpException {
        String content = htmlUnit.makeRequest("http://weibo.com/2804401290/CiF4a72Xm?type=comment");
        System.out.println(content);
    }
}
