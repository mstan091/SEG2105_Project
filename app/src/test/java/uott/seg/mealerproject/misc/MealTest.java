package uott.seg.mealerproject.misc;

import junit.framework.TestCase;

import uott.seg.mealerproject.enums.EnumMealStatus;

public class MealTest extends TestCase {

    public void testGetMealStatus() {

        String inCookEmail = "cook2@mealer.com";
        String inMealName = "Meal2";
        EnumMealStatus inMealStatus=EnumMealStatus.UNAVAILABLE;
        String outMealStatus;

        String expectStatus = "UnAvailable";
        Meal meal = new Meal(inCookEmail,inMealName ,inMealStatus);
        outMealStatus = String.valueOf(meal.getMealStatus());
        assertEquals(outMealStatus, expectStatus );
    }
}
