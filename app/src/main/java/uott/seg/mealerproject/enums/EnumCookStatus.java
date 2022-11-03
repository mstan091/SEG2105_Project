package uott.seg.mealerproject.enums;

import java.util.HashMap;

public enum EnumCookStatus {
    NORMAL((short) 0, "Active"),
    SUSPENDED((short) -1, "Suspended"),
    REVOKED((short) -2, "Revoked");

    private String cookStatus;
    private short statusCode;

    private EnumCookStatus(short statusCode, String cookStatus) {
        this.statusCode = statusCode;
        this.cookStatus = cookStatus;
    }

    private static final HashMap<Short, EnumCookStatus> fromCode = new HashMap<>();
    private static final HashMap<String, EnumCookStatus> fromValue = new HashMap<>();

    static {
        for (EnumCookStatus e : values()) {
            fromCode.put(e.statusCode, e);
            fromValue.put(e.cookStatus, e);
        }
    }

    @Override
    public String toString(){
        return cookStatus;
    }

    public short getStatusCode() {
        return statusCode;
    }

    public static EnumCookStatus getCookStatus(short statusCode) {
        return fromCode.get(statusCode);
    }

    public static EnumCookStatus getCookStatus(String status) {
        return fromCode.get(status);
    }


}
