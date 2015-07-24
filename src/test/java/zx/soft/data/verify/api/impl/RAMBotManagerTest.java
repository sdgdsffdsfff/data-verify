package zx.soft.data.verify.api.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import zx.soft.data.verify.common.VerifiedData;
import zx.soft.data.verify.common.VerifiedDataCollection;
import zx.soft.utils.json.JsonUtils;

public class RAMBotManagerTest {

	@Test
	public void testDuplicateByUrl() {
		List<VerifiedData> records = new ArrayList<>();
		Map<String, String> r1 = new HashMap<>();
		r1.put("url", "abc");
		Map<String, String> r2 = new HashMap<>();
		r2.put("url", "abc");
		Map<String, String> r3 = new HashMap<>();
		r3.put("url", "abcd");
		VerifiedData cd1 = new VerifiedData("test", "测试", r1);
		VerifiedData cd2 = new VerifiedData("test", "测试", r2);
		VerifiedData cd3 = new VerifiedData("test", "测试", r3);
		records.add(cd1);
		records.add(cd2);
		records.add(cd3);
		VerifiedDataCollection vdc = new VerifiedDataCollection(records.size(), records);

		String right = "{\"num\":2,\"records\":[{\"filename\":\"test\",\"record\":{\"url\":\"abc\"},\"keyword\":\"测试\"},{\"filename\":\"test\",\"record\":{\"url\":\"abcd\"},\"keyword\":\"测试\"}]}";

		assertEquals(right, JsonUtils.toJsonWithoutPretty(RAMBotManager.duplicateByUrl(vdc)));
	}

}
