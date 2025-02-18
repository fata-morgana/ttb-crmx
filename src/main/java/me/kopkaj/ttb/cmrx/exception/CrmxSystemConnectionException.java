package me.kopkaj.ttb.cmrx.exception;

public class CrmxSystemConnectionException extends CrmxSystemException {
    private static final long serialVersionUID = 4672358474119870079L;

	protected CrmxSystemConnectionException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

	protected CrmxSystemConnectionException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public static CrmxSystemConnectionException serviceTimeout(String serviceName) {
        return new CrmxSystemConnectionException(ErrorCode.SERVICE_TIMEOUT, "Connection to " + serviceName + " timed out");
    }

    public static CrmxSystemConnectionException serviceUnavailable(String serviceName) {
        return new CrmxSystemConnectionException(ErrorCode.SERVICE_UNAVAILABLE, serviceName + " is unavailable");
    }
}
