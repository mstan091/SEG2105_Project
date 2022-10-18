package uott.seg.mealerproject.enums;

public enum EnumLoginStatus {
    SUCCESS("Success"),
    USER_NOT_FOUND("UserNotFound"),
    INVALID_USER_TYPE("WrongUserType"),
    INVALID_PWD("InvalidPwd");

    private String loginStatus;
    EnumLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    @Override
    public String toString() {
        return loginStatus;
    }

}
