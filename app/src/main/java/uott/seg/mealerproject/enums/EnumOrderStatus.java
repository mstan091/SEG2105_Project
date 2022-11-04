package uott.seg.mealerproject.enums;

public enum EnumOrderStatus {

    PENDING((short) 0, "Pending"),
    APPROVED((short) 1, "Approved"),
    READY_PICKUP((short) 2, "Ready"),

    PICKED((short) 3, "Picked"),
    REJECTED((short) 99, "Rejected");


    private String orderStatus;
    private short statusCode;

    EnumOrderStatus(short statusCode, String orderStatus) {
            this.orderStatus = orderStatus;
            this.statusCode = statusCode;
    }

    @Override
    public String toString(){
        return orderStatus;
    }

    public short getStatusCode() {
        return statusCode;
    }

}
