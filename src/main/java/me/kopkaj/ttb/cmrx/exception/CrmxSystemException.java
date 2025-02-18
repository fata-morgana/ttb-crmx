package me.kopkaj.ttb.cmrx.exception;

public class CrmxSystemException extends CrmxException {
    private static final long serialVersionUID = -5845512592314514041L;

	public CrmxSystemException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public CrmxSystemException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

}
