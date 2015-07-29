package zx.soft.data.verify.io;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import org.junit.Test;

import zx.soft.data.verify.common.RecordInfoCollection;
import zx.soft.data.verify.http.HttpAdvanced;
import zx.soft.data.verify.http.HttpException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SolrClientTest {

	private HttpAdvanced http = new HttpAdvanced(null);

	@Test
	public void testPost() throws IOException, URISyntaxException, HttpException {
		SolrClient client = new SolrClient(http, "192.168.31.11:8900", "192.168.31.11:8983");

		String json = "{\"num\":1,\"records\":[{\"id\":\"dataverificationbot\",\"platform\":9,\"mid\":\"123456789987654321\",\"username\":\"zxsoft\",\"nickname\":\"中新舆情\",\"original_id\":\"original_sentiment\",\"original_uid\":\"original_zxsoft\",\"original_name\":\"original_中新软件\",\"original_title\":\"original_标题\",\"original_url\":\"http://www.orignal_url.com\",\"url\":\"http://www.url.com\",\"home_url\":\"http://www.home_url.com\",\"title\":\"标题\",\"type\":\"所属类型\",\"isharmful\":true,\"content\":\"测试内容\",\"comment_count\":10,\"read_count\":20,\"favorite_count\":30,\"attitude_count\":40,\"repost_count\":50,\"video_url\":\"http://www.video_url.com\",\"pic_url\":\"htpp://www.pic_url.com\",\"voice_url\":\"http://www.voice_url.com\",\"timestamp\":1419755627695,\"source_id\":70,\"lasttime\":1419842027695,\"server_id\":90,\"identify_id\":100,\"identify_md5\":\"abcdefg123456789\",\"keyword\":\"关键词\",\"first_time\":1419928427695,\"update_time\":1420014827695,\"ip\":\"192.168.32.45\",\"location\":\"安徽省合肥市\",\"geo\":\"经纬度信息\",\"receive_addr\":\"receive@gmail.com\",\"append_addr\":\"append@gmail.com\",\"send_addr\":\"send@gmail.com\",\"source_name\":\"新浪微博\",\"source_type\":121,\"country_code\":122,\"location_code\":123,\"province_code\":124,\"city_code\":125}]}";

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		RecordInfoCollection co = gson.fromJson(json, RecordInfoCollection.class);

		client.write(co);
	}

	@Test
	public void testGet() throws IOException, URISyntaxException, HttpException {
		SolrClient client = new SolrClient(http, "192.168.31.11:8900", "192.168.31.11:8983");
		Map info = client.read("dataverificationbot");
		System.out.println(info.toString());
	}

}
