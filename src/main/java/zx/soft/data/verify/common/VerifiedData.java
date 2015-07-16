package zx.soft.data.verify.common;

import java.util.Map;

/**
 * 验证数据的结果
 */
public class VerifiedData {

	private String filename;
	private Map<?, ?> record;
	private String keyword;

	public VerifiedData(String filename2, String keyword2, Map<?, ?> record2) {
		this.filename = filename2;
		this.keyword = keyword2;
		this.record = record2;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Map<?, ?> getRecord() {
		return record;
	}

	public void setRecord(Map<?, ?> record) {
		this.record = record;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
