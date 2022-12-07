package uott.seg.mealerproject.misc;

import junit.framework.TestCase;

import uott.seg.mealerproject.enums.EnumOrderStatus;

public class MealOrderTest extends TestCase {

    public void testGetStatus() {
        String inMealName="Meal2";
        String inMealType="Desert";
        String inCuisineType="Italian";
        String inCookEmail ="cook2@mealer.com";
        String inClientEmail="cleint@mealer.com";
        String inPickupTime = "12/12/12";

        EnumOrderStatus inMealStatus=EnumOrderStatus.PENDING;
        String outOrderStatus;
        String expOrderStatus = String.valueOf(EnumOrderStatus.PENDING);

        MealOrder order = new MealOrder(inMealName, inMealType, inCuisineType, inCookEmail, inClientEmail, inPickupTime);
        order.setStatus(EnumOrderStatus.PENDING);
        outOrderStatus = String.valueOf(order.getStatus());
        assertEquals(outOrderStatus, expOrderStatus );



    }
}
