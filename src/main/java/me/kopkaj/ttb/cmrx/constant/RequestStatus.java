package me.kopkaj.ttb.cmrx.constant;

import me.kopkaj.ttb.cmrx.exception.CrmxDataMappingException;

public enum RequestStatus implements MappableCode {
	PENDING("PEN", "Pending"),
    IN_PROGRESS("INP", "In Progress"),
    COMPLETED("COM", "Completed"),
    CANCELED("CAN", "Canceled");

    private final String code;
    private final String description;

    RequestStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static RequestStatus fromCode(String code) {
        for (RequestStatus status : RequestStatus.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        throw CrmxDataMappingException.invalidEnumCode(RequestStatus.class.getName(), code);
    }

    public static RequestStatus fromDescription(String description) {
        for (RequestStatus status : RequestStatus.values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        throw CrmxDataMappingException.invalidEnumDescription(RequestStatus.class.getName(), description);
    }
}
