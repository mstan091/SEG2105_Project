package uott.seg.mealerproject.users;

import java.util.List;

import uott.seg.mealerproject.enums.EnumRegisterStatus;
import uott.seg.mealerproject.misc.CreditCardInfo;
import uott.seg.mealerproject.misc.Meal;
import uott.seg.mealerproject.misc.MealOrder;

public class MealerUserClinet extends  MealerUser{



    private CreditCardInfo cardInfo;


    private List<MealOrder> currentOrder;
    private List<MealOrder> historyOrder;

    public MealerUserClinet(String fName, String lName, String email, String pwd, String addr) {
        super(fName, lName, email, pwd, addr);
    }

    @Override
    public EnumRegisterStatus register() {
        EnumRegisterStatus regStatus = EnumRegisterStatus.SUCCESS;

        return regStatus;
    }

    public CreditCardInfo getCardInfo() {
        return cardInfo;
    }

    public void setCardInfo(CreditCardInfo cardInfo) {
        this.cardInfo = cardInfo;
    }

}
