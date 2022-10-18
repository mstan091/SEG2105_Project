package uott.seg.mealerproject.enums;

public enum EnumUserType {
    Client(1, "Client"),
    Cook(2, "Cook"),
    Admin(3, "Administrator");

    private int userType;
    private String typeName;

    private EnumUserType(int userType, String typeName) {

        this.userType = userType;
        this.typeName = typeName;
    }

    @Override
    public String toString(){
        return typeName;
    }

    public int getUserTypeCode() {
        return userType;
    }

}
