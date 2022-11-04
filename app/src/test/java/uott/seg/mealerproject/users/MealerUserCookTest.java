package uott.seg.mealerproject.users;

import junit.framework.TestCase;

import uott.seg.mealerproject.enums.EnumCookStatus;
import uott.seg.mealerproject.enums.EnumUserType;

public class MealerUserCookTest extends TestCase {

    public void testGetStatus() {
        String fName, lName, email , pwd , addr;
        fName="Cook1";
        lName="Mealer";
        email="cook1@mealear.com";
        pwd="cook1";
        addr="1 cook street mealer city";
        EnumUserType UserType = EnumUserType.Cook;
        EnumCookStatus expCookstatus = EnumCookStatus.NORMAL;
        MealerUserCook cu = new MealerUserCook(fName,lName,email,pwd,addr);
        EnumCookStatus retCookstatus = cu.getStatus();

        assertEquals(expCookstatus, retCookstatus);

    }
}