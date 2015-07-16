package zx.soft.data.verify.http;

public class HttpException extends Exception {

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
