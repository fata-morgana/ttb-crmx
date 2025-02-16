package me.kopkaj.ttb.cmrx.constant;

import me.kopkaj.ttb.cmrx.exception.CrmxDataMappingException;

public enum RequestChannel implements MappableCode {
	PHONE("PHN","Phone"),
	EMAIL("EML", "Email"),
	BRANCH("BRN", "Branch"),
	ONLINE("ONL", "Online");
	
	private final String code;
    private final String description;

    RequestChannel(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RequestChannel fromCode(String code) {
        for (RequestChannel status : RequestChannel.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw CrmxDataMappingException.invalidEnumCode(RequestChannel.class.getName(), code);
    }

    public static RequestChannel fromDescription(String description) {
        for (RequestChannel status : RequestChannel.values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        throw CrmxDataMappingException.invalidEnumDescription(RequestChannel.class.getName(), description);
    }
}
