package uott.seg.mealerproject.misc;

import junit.framework.TestCase;

import uott.seg.mealerproject.enums.EnumMealStatus;

public class MealPriceTest extends TestCase {

    public void testGetPrice() {
        String inMealName = "Meal1";
        String inMealType="Main";
        String inCuisineType="Italian";
        String inCookEmail = "cook1@mealer.com";
        String inAllergens="alleg";
        float inPrice=10.5f;
        String inIngredients="beans,onion,meat";
        String inDesc="Chili";
        float expPrice = 10.5f;
        float outPrice;
        Meal meal = new Meal(inMealName, inMealType, inCuisineType,inCookEmail, inAllergens, inPrice,inIngredients, inDesc);
        outPrice = meal.getPrice();
        assertEquals(outPrice, expPrice );

    }
}

