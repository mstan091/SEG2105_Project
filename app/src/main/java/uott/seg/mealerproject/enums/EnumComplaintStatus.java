package uott.seg.mealerproject.enums;

import java.util.HashMap;

public enum EnumComplaintStatus {

    NEW((short) 1, "New"),
    DISMISS((short) 0, "Dismiss"),
    SUSPENDED_COOK((short) -1, "Suspend"),
    REVOKED_COOK((short) -2, "Revoked");

    private String complaintStatus;
    private short statusCode;

    private static final HashMap<Short, EnumComplaintStatus> fromCode = new HashMap<>();
    private static final HashMap<String, EnumComplaintStatus> fromValue = new HashMap<>();

    private EnumComplaintStatus(short statusCode, String complaintStatus) {
        this.statusCode = statusCode;
        this.complaintStatus = complaintStatus;
    }

    static {
        for (EnumComplaintStatus e : values()) {
            fromCode.put(e.statusCode, e);
            fromValue.put(e.complaintStatus, e);
        }
    }

    @Override
    public String toString(){

        return complaintStatus;
    }

    public short getStatusCode() {

        return statusCode;
    }


}
