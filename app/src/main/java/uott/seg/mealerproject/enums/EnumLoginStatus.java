package uott.seg.mealerproject.enums;

public enum EnumLoginStatus {
    NOT_LONGIN((short)  -1, "Not login"),
    SUCCESS((short) 0, "Success"),
    USER_NOT_FOUND((short) 1, "UserNotFound"),
    INVALID_USER_TYPE((short) 2, "WrongUserType"),
    INVALID_PWD((short) 3, "InvalidPwd");

    private String loginStatus;
    private short statusCode;

    EnumLoginStatus(short statusCode, String loginStatus) {
        this.statusCode = statusCode;
        this.loginStatus = loginStatus;
    }

    @Override
    public String toString() {
        return loginStatus;
    }

    public short getStatusCode() {
        return statusCode;
    }
}
