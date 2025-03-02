package me.kopkaj.ttb.cmrx.exception;

public class CrmxBusinessCriteriaException extends CrmxBusinessException {

	private static final long serialVersionUID = 9181672206637338555L;

	public CrmxBusinessCriteriaException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

    public CrmxBusinessCriteriaException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }
}
