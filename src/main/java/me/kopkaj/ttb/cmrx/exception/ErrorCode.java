package me.kopkaj.ttb.cmrx.exception;

public interface ErrorCode {
    // Data Validation
	String INPUT_TOO_LONG = "D0001";
	String INVALID_FORMAT = "D0002";
	
	// Data Mapping
	String INVALID_ENUM_CODE = "D0101";
	String INVALID_ENUM_DESCRIPTION = "D0102";
	String DATA_MAPPING_FAIL = "D0103";
	
	// Criteria
	String SEARCH_REQUEST_BY_TYPES = "B0001";
	
	// Condition
	String CUSTOMER_REQUEST_NOT_FOUND = "B1001";
	String CUSTOMER_REQUEST_ALREADY_COMPLETED = "B1002";
	
	// Internal System Error
	String DATABASE_CONNECTION_FAIL = "S0001";
	String INTERNAL_SERVER_ERROR = "S0002";
	
	// Connection Error
	String SERVICE_TIMEOUT = "S0101";
	String SERVICE_UNAVAILABLE = "S0102";
}
