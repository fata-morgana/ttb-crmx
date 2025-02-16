package me.kopkaj.ttb.cmrx.constant;

import me.kopkaj.ttb.cmrx.exception.CrmxDataMappingException;

public enum RequestPriority implements MappableCode {
    LOW ("LO", "Low"),
    MEDIUM ("ME", "Medium"),
    HIGH ("HI", "High");

    private final String code;
    private final String description;

    RequestPriority(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RequestPriority fromCode(String code) {
        for (RequestPriority status : RequestPriority.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw CrmxDataMappingException.invalidEnumCode(RequestPriority.class.getName(), code);
    }

    public static RequestPriority fromDescription(String description) {
        for (RequestPriority status : RequestPriority.values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        throw CrmxDataMappingException.invalidEnumCode(RequestPriority.class.getName(), description);
    }
}
