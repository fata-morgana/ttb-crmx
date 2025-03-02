package me.kopkaj.ttb.cmrx.exception;

public class CrmxBusinessConditionException extends CrmxBusinessException {

	private static final long serialVersionUID = -1560786456455052079L;

	public CrmxBusinessConditionException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

    public CrmxBusinessConditionException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public static CrmxBusinessConditionException customerRequestNotFound(Long customerRequestId) {
    	return new CrmxBusinessConditionException(ErrorCode.CUSTOMER_REQUEST_NOT_FOUND, String.format("Request with ID: %s is not found.", customerRequestId));
    }

    public static CrmxBusinessConditionException customerRequestAlreadyCompleted(Long customerRequestId) {
    	return new CrmxBusinessConditionException(ErrorCode.CUSTOMER_REQUEST_ALREADY_COMPLETED, String.format("Request with ID: %s is already completed.", customerRequestId));
    }
}
