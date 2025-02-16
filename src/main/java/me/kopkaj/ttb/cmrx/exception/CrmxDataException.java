package me.kopkaj.ttb.cmrx.exception;

public class CrmxDataException extends CrmxException {
    private static final long serialVersionUID = -5520281715170592452L;

    protected CrmxDataException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

    protected CrmxDataException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }
}
