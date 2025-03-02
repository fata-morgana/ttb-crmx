package me.kopkaj.ttb.cmrx.exception;

public class CrmxSystemInternalException extends CrmxSystemException {
	private static final long serialVersionUID = 6947523526048176876L;

	public CrmxSystemInternalException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    public CrmxSystemInternalException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public static CrmxSystemInternalException databaseConnectionFailed() {
        return new CrmxSystemInternalException("S0001", "Database connection failed");
    }

    public static CrmxSystemInternalException internalServerError() {
        return new CrmxSystemInternalException("S0002", "Internal server error");
    }
}
