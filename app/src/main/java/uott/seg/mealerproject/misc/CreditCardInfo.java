package uott.seg.mealerproject.misc;

import java.util.Date;

public class CreditCardInfo {

    String holderName;
    String cardNum;
    String expireDate;  //"Mon/Year"
    String secCode;

    public CreditCardInfo (String hoderName, String cardNum, String expireDate, String secCode) {
        this.holderName = hoderName;
        this.cardNum = cardNum;
        this.expireDate = expireDate;
        this.secCode = secCode;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getSecCode() {
        return secCode;
    }

    public void setSecCode(String secCode) {
        this.secCode = secCode;
    }



}
