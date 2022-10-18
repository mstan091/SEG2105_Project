package uott.seg.mealerproject.enums;

public enum EnumRegisterStatus {
    SUCCESS("Success"),
    USER_EXIST("UserExist");

    private String regStatus;
    private EnumRegisterStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    @Override
    public String toString() {
        return regStatus;
    }

}
