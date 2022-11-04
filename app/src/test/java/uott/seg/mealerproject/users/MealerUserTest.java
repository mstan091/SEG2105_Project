package uott.seg.mealerproject.users;

import junit.framework.TestCase;

import uott.seg.mealerproject.enums.EnumRegisterStatus;
import uott.seg.mealerproject.enums.EnumUserType;

public class MealerUserTest extends TestCase {

    public void testGetUserType() {
        String fName, lName, email , pwd , addr;
        fName="Admin";
        lName="Mealer";
        email="admin@gmail.com";
        pwd="admin";
        addr="1 admin street mealer city";
        EnumUserType expUserType = EnumUserType.Admin;

        MealerUserAdmin mu = new MealerUserAdmin(fName,lName,email,pwd,addr);
        EnumUserType retUserType = mu.getUserType();

        assertEquals(expUserType, retUserType);

    }
}