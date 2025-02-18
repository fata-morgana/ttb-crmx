package me.kopkaj.ttb.cmrx.exception;

public class CrmxBusinessException extends CrmxException {
    private static final long serialVersionUID = 2888254337302934260L;

	public CrmxBusinessException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public CrmxBusinessException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }
}
