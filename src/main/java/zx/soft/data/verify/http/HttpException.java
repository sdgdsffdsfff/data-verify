package zx.soft.data.verify.http;

public class HttpException extends Exception {

	private static final long serialVersionUID = 3325677634096195607L;

	public HttpException() {
		super();
	}

	public HttpException(String msg) {
		super(msg);
	}

	public HttpException(Throwable e) {
		super(e);
	}

	public HttpException(String msg, Throwable e) {
		super(msg, e);
	}

}
