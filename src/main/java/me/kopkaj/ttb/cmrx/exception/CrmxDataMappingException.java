package me.kopkaj.ttb.cmrx.exception;

public class CrmxDataMappingException extends CrmxDataException {
	private static final long serialVersionUID = -3596955300116171313L;

	protected CrmxDataMappingException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }

	protected CrmxDataMappingException(String errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

    public static CrmxDataMappingException invalidEnumCode(String enumType, String codeString) {
        return new CrmxDataMappingException(ErrorCode.INVALID_ENUM_CODE, String.format("Enum code of type %s with value %s not found.", enumType, codeString));
    }

    public static CrmxDataMappingException invalidEnumDescription(String enumType, String codeString) {
        return new CrmxDataMappingException(ErrorCode.INVALID_ENUM_DESCRIPTION, String.format("Cannot find enum code of type %s with description %s.", enumType, codeString));
    }

    public static CrmxDataMappingException dataMappingFailure(String source, String destination) {
        return new CrmxDataMappingException(ErrorCode.DATA_MAPPING_FAIL, "Failed to map data from " + source + " to " + destination);
    }
}
