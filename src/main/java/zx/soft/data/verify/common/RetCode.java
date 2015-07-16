package zx.soft.data.verify.common;

public class RetCode {

	private int code;
	private String msg;
	private Object data;

	public RetCode() {
	}

	public RetCode(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public RetCode(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
