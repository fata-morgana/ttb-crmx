package me.kopkaj.ttb.cmrx.exception;

public class CrmxDataValidationException extends CrmxDataException {
	private static final long serialVersionUID = 1394466969557582484L;

	protected CrmxDataValidationException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

	protected CrmxDataValidationException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public static CrmxDataValidationException inputTooLong(String field, int maxLength) {
        return new CrmxDataValidationException(ErrorCode.INPUT_TOO_LONG, "Input for " + field + " is too long. Max length: " + maxLength);
    }

    public static CrmxDataValidationException invalidFormat(String field) {
        return new CrmxDataValidationException(ErrorCode.INVALID_FORMAT, "Invalid format for " + field);
    }
}
