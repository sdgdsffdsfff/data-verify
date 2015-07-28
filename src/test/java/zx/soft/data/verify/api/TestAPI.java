package zx.soft.data.verify.api;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;

import zx.soft.data.verify.common.Record;
import zx.soft.data.verify.common.RequestParam;

public class TestAPI {

	private static String baseUrl = "http://localhost:8279/bot/";
	private static BotServer server;

	@BeforeClass
	public static void before() throws Exception {
		server = new BotServer(8279);
		server.start();
	}

	@AfterClass
	public static void after() throws Exception {
		for (int i = 1; i < 10; i++) {
			System.err.println("Waiting for jobs to complete - " + i + "s");
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
			server.stop();
		}
	}

	@Test
	public void testUpload() throws Exception {
		ClientResource client = new ClientResource(baseUrl + "verify");

		List<Record> recs = new ArrayList<Record>();
		Record record = new Record("67263CE8357F83BDA4DCC666F41FC88D", "黑火药 东莞");
		recs.add(record);

		RequestParam param = new RequestParam("test0", recs);
		Representation r = client.post(param);
		System.out.println(r.getText());
	}

	@Test
	public void testDownload() throws Exception {
		ClientResource client = new ClientResource(baseUrl + "verify");
		client.addQueryParameter("filename", "test0");
		System.out.println(client.get().getText());
	}

}
