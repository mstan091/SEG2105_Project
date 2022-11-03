package uott.seg.mealerproject.users;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.List;

import uott.seg.mealerproject.enums.EnumCookStatus;
import uott.seg.mealerproject.enums.EnumOrderStatus;
import uott.seg.mealerproject.enums.EnumRegisterStatus;
import uott.seg.mealerproject.enums.EnumUserType;
import uott.seg.mealerproject.misc.MealOrder;

public class MealerUserCook extends  MealerUser{


    private String cookDescription;
    private EnumCookStatus status;
    private Bitmap imgCheck;


    byte[] byteImgCheck;

    private List<MealOrder> orders;

    public MealerUserCook(String fName, String lName, String email, String pwd, String addr) {
        super(fName, lName, email, pwd, addr);
        super.setUserType(EnumUserType.Cook);
        this.status = EnumCookStatus.NORMAL;
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


    public Bitmap getImgCheck() {
        return imgCheck;
    }

    public void setImgCheck(Bitmap imgCheck) {
        this.imgCheck = imgCheck;
    }

    public String getCookDescription() {
        return cookDescription;
    }

    public void setCookDescription(String cookDescription) {
        this.cookDescription = cookDescription;
    }

    public EnumCookStatus getStatus() {
        return status;
    }

    public void setStatus(EnumCookStatus status) {
        this.status = status;
    }

    public byte[] getByteImgCheck() {
        return byteImgCheck;
    }

    public void setByteImgCheck(byte[] byteImgCheck) {
        this.byteImgCheck = byteImgCheck;
    }
}
