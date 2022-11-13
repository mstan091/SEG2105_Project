package uott.seg.mealerproject.enums;

import java.util.HashMap;

public enum EnumMealStatus {
    AVAILABLE((short) 0, "Available"),
    UNAVAILABLE((short) -1, "UnAvailable");


    private String mealStatus;
    private short statusCode;

    private EnumMealStatus(short statusCode, String mealStatus) {
        this.statusCode = statusCode;
        this.mealStatus = mealStatus;
    }

    private static final HashMap<Short, EnumMealStatus> fromCode = new HashMap<>();
    private static final HashMap<String, EnumMealStatus> fromValue = new HashMap<>();

    static {
        for (EnumMealStatus e : values()) {
            fromCode.put(e.statusCode, e);
            fromValue.put(e.mealStatus, e);
        }
    }

    @Override
    public String toString(){
        return mealStatus;
    }

    public short getStatusCode() {
        return statusCode;
    }

    public static EnumMealStatus getMealStatus(short statusCode) {
        return fromCode.get(statusCode);
    }

    public static EnumMealStatus getMealStatus(String status) {
        return fromCode.get(status);
    }


}