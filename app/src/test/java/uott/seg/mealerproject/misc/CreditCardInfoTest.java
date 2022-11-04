package uott.seg.mealerproject.misc;

import junit.framework.TestCase;

public class CreditCardInfoTest extends TestCase {

    public void testGetCardNum() {
        String holderName , cardNum, expireDate,secCode;
        holderName="Client1";
        cardNum = "1111 0000";
        expireDate="01/22";
        secCode="001";
        String expCardNum= "1111 0000";
        CreditCardInfo ccn= new CreditCardInfo(holderName,cardNum, expireDate,secCode );
        String retCardNum = ccn.getCardNum();

        assertEquals(expCardNum, retCardNum);
    }
}