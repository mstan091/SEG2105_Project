package uott.seg.mealerproject.misc;

import uott.seg.mealerproject.enums.EnumMealStatus;
import uott.seg.mealerproject.users.MealerUserCook;

public class Meal {

    public static final String MEAL_ID = "rowID";

    public static final String MEAL_NAME = "mealName";
    public static final String MEAL_TYPE = "mealType";
    public static final String CUISINE_TYPE = "cuisineType";

    public static final String MEAL_STATUS = "status";
    public static final String COOK_EMAIL = "cookEmail";
    public static final String MEAL_ALLERGENS = "allergens";

    public static final String MEAL_PRICE = "price";
    public static final String MEAL_INGREDIENTS = "ingredients";
    public static final String MEAL_DESCRIPTION =  "description";

    private long mealID;
    private String mealName;
    private String mealType;
    private String cuisineType;

    private EnumMealStatus mealStatus;
    private String cookEmail;
    private String allergens;

    private float price;
    private String ingredients;
    private String desc;

    private MealerUserCook cook;

    public Meal ( String mealName, String mealType, String cuisineType, String cookEmail, String allergens, float price, String ingredients, String desc  ) {
        this.mealName = mealName;
        this.mealType = mealType;
        this.cuisineType=cuisineType;
        this.mealStatus = EnumMealStatus.AVAILABLE;
        this.cookEmail = cookEmail;
        this.allergens=allergens;
        this.price=price;
        this.ingredients=ingredients;
        this.desc=desc;
    }

    public Meal ( String mealName, String mealType, String cuisineType, String cookEmail, String allergens, float price, String ingredients, String desc, EnumMealStatus status  ) {
        this.mealName = mealName;
        this.mealType = mealType;
        this.cuisineType=cuisineType;
        this.mealStatus = status;
        this.mealStatus = status;
        this.cookEmail = cookEmail;
        this.allergens=allergens;
        this.price=price;
        this.ingredients=ingredients;
        this.desc=desc;
    }

    public Meal (String cookEmail, String mealName, EnumMealStatus status) {
        this.cookEmail = cookEmail;
        this.mealName = mealName;
        this.mealStatus = status;
    }

    public EnumMealStatus getMealStatus() {
        return mealStatus;
    }

    public void setMealStatus(EnumMealStatus mealStatus) {
        this.mealStatus = mealStatus;
    }

    public String getCookEmail() {
        return cookEmail;
    }

    public void setCookEmail(String cookEmail) {
        this.cookEmail = cookEmail;
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

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String allergens) {
        this.ingredients = ingredients;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String allergens) {
        this.desc = desc;
    }

    public long geMealID() {
        return mealID;
    }

    public void setMealID(long mealID) {
        this.mealID = mealID;
    }


    public MealerUserCook getCook() {
        return cook;
    }

    public void setCook(MealerUserCook cook) {
        this.cook = cook;
    }

}
