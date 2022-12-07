package uott.seg.mealerproject.users;

import junit.framework.TestCase;

import uott.seg.mealerproject.enums.EnumCookStatus;
import uott.seg.mealerproject.enums.EnumUserType;

public class MealerUserCookDescTest extends TestCase {

    public void testGetCookDescription() {
        String fName, lName, email , pwd , addr;
        fName="Cook1";
        lName="Mealer";
        email="cook1@mealear.com";
        pwd="cook1";
        addr="1 cook street mealer city";
        EnumUserType userType = EnumUserType.Cook;
        EnumCookStatus cookstatus = EnumCookStatus.NORMAL;
        MealerUserCook cu = new MealerUserCook(fName,lName,email,pwd,addr);
        cu.setCookDescription("Italian cook");
        String expCookDesc="Italian cook";
        String outCookDesc = cu.getCookDescription();
        assertEquals(outCookDesc, expCookDesc );

    }
}
