package uott.seg.mealerproject.users;

import uott.seg.mealerproject.enums.EnumRegisterStatus;
import uott.seg.mealerproject.enums.EnumUserType;

public class MealerUserAdmin extends  MealerUser {

    public MealerUserAdmin(String fName, String lName, String email, String pwd, String addr) {
        super(fName, lName, email, pwd, addr);
        super.setUserType(EnumUserType.Admin);
    }


    @Override
    public EnumRegisterStatus register() {
        return null;
    }
}
