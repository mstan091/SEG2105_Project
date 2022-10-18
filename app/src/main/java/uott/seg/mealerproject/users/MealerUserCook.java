package uott.seg.mealerproject.users;

import android.media.Image;

import java.util.List;

import uott.seg.mealerproject.enums.EnumOrderStatus;
import uott.seg.mealerproject.enums.EnumRegisterStatus;
import uott.seg.mealerproject.misc.MealOrder;

public class MealerUserCook extends  MealerUser{


    private String cookDescription;
    private Image imgCheck;


    byte[] byteImgCheck;

    private List<MealOrder> orders;

    public MealerUserCook(String fName, String lName, String email, String pwd, String addr) {
        super(fName, lName, email, pwd, addr);
    }

    @Override
    public EnumRegisterStatus register() {
        EnumRegisterStatus regStatus = EnumRegisterStatus.SUCCESS;

        return regStatus;
    }

    public void processOrder(MealOrder order, EnumOrderStatus status) {
        order.setStatus(status);
    }

    public void updateOrder(MealOrder order, EnumOrderStatus status) {
        order.setStatus(status);
    }


    public Image getImgCheck() {
        return imgCheck;
    }

    public void setImgCheck(Image imgCheck) {
        this.imgCheck = imgCheck;
    }

    public String getCookDescription() {
        return cookDescription;
    }

    public void setCookDescription(String cookDescription) {
        this.cookDescription = cookDescription;
    }


    public byte[] getByteImgCheck() {
        return byteImgCheck;
    }

    public void setByteImgCheck(byte[] byteImgCheck) {
        this.byteImgCheck = byteImgCheck;
    }
}
