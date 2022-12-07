package uott.seg.mealerproject.enums;

import java.util.HashMap;

public enum EnumOrderStatus {

    PENDING((short) 0, "Pending"),
    APPROVED((short) 1, "Approved"),
    READY_PICKUP((short) 2, "Ready"),

    PICKED((short) 3, "Picked"),
    REJECTED((short) 99, "Rejected"),
    COMPLETED((short) 100, "Completed");



    private String orderStatus;
    private short statusCode;

    EnumOrderStatus(short statusCode, String orderStatus) {
            this.orderStatus = orderStatus;
            this.statusCode = statusCode;
    }

    private static final HashMap<Short, EnumOrderStatus> fromCode = new HashMap<>();
    private static final HashMap<String, EnumOrderStatus> fromValue = new HashMap<>();

    static {
        for (EnumOrderStatus e : values()) {
            fromCode.put(e.statusCode, e);
            fromValue.put(e.orderStatus, e);
        }
    }


    @Override
    public String toString(){
        return orderStatus;
    }

    public short getStatusCode() {
        return statusCode;
    }

    public static EnumOrderStatus getOrderStatus(short statusCode) {
        return fromCode.get(statusCode);
    }

    public static EnumOrderStatus getOrderStatus(String status) {
        return fromCode.get(status);
    }


}
