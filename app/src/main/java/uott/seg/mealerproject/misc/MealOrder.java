package uott.seg.mealerproject.misc;

import java.util.Date;

import uott.seg.mealerproject.enums.EnumOrderStatus;
import uott.seg.mealerproject.users.MealerUserClient;

public class MealOrder {

    public static final String MEAL_NAME = "MealName";
    public static final String MEAL_TYPE = "MealType";
    public static final String CUISINE_TYPE = "CuisineType";

    public static final String ORDER_TIME = "Order_Time";
    public static final String PICKUP_TIME = "Pickup_Time";
    public static final String ORDER_STATUS = "OrderStatus";

    public static final String COOK_EMAIL = "CookEmail";
    public static final String CLIENT_EMAIL = "ClientEmail";
    public static final String ORDER_ID = "RowID";

    private long rowID;
    private String mealName;
    private String mealType;
    private String cuisineType;
    private String orderTime;
    private String pickupTime;
    private EnumOrderStatus status;
    private String cookEmail;
    private String clientEmail;

    public MealOrder (String mealName, String mealType, String cuisineType, String cookEmail, String  clientEmail, String pickupTime) {
        this.mealName = mealName;
        this.mealType = mealType;
        this.cuisineType = cuisineType;

        this.cookEmail = cookEmail;
        this.clientEmail = clientEmail;
        this.pickupTime = pickupTime;
    }

    public long getRowID() {
        return rowID;
    }

    public void setRowID(long rowID) {
        this.rowID = rowID;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getCuisineType() {
        return cuisineType;
    }

    public void setCuisineType(String cuisineType) {
        this.cuisineType = cuisineType;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public EnumOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EnumOrderStatus status) {
        this.status = status;
    }

    public String getCookEmail() {
        return cookEmail;
    }

    public void setCookEmail(String cookEmail) {
        this.cookEmail = cookEmail;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }



}
