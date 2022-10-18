package uott.seg.mealerproject.enums;

public enum EnumOrderStatus {

    READY_PICKUP("Ready"),
    PICKED("Picked"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    PENDING("Pending");

    private String orderStatus;
    private EnumOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
    }

    @Override
    public String toString(){
        return orderStatus;
    }


}
