package zx.soft.data.verify.common;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import zx.soft.data.verify.common.Configuration;

import com.thoughtworks.xstream.XStream;

public class TestConfiguration {

    @Test
    public void test() throws IOException {
        XStream xStream = new XStream();
        InputStream is = TestConfiguration.class.getClassLoader().getResourceAsStream("conf.xml");
        xStream.alias("configuration", Configuration.class);
        Configuration conf = (Configuration) xStream.fromXML(is);
        System.out.println(conf.getCapacity());
    }
}
