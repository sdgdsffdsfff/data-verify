package zx.soft.data.verify.io;

public class WriteException extends Exception {

    private static final long serialVersionUID = 3778962257802903576L;

    public WriteException() {}
    
    public WriteException(String message) {
        super(message);
    }

    public WriteException(Throwable cause) {
        super(cause);
    }
    
    public WriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
