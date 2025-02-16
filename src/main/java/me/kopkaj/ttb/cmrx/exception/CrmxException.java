package me.kopkaj.ttb.cmrx.exception;

public abstract class CrmxException extends RuntimeException {
	private static final long serialVersionUID = -1762651729366504619L;
	private final String code;

	protected CrmxException(String code, String message) {
		super(message);
		this.code = code;
	}
	
	protected CrmxException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

	public String getCode() {
		return code;
	}
}
