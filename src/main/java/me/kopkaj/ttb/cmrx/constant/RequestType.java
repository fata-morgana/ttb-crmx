package me.kopkaj.ttb.cmrx.constant;

import me.kopkaj.ttb.cmrx.exception.CrmxDataMappingException;

public enum RequestType {
	CREDIT_CARD_PAYMENT ("PAY", "Credit card payment"),
	BALANCE_INQUIRY ("BIN", "Balance Inqiury"),
	ADDRESS_CHANGE ("ADC", "Address Change");
	
	private final String code;
    private final String description;

    RequestType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RequestType fromCode(String code) {
        for (RequestType status : RequestType.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw CrmxDataMappingException.invalidEnumCode(RequestType.class.getName(), code);
    }

    public static RequestType fromDescription(String description) {
        for (RequestType status : RequestType.values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        throw CrmxDataMappingException.invalidEnumDescription(RequestType.class.getName(), description);
    }
}
