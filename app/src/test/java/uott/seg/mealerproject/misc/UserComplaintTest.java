package uott.seg.mealerproject.misc;

import junit.framework.TestCase;

import org.junit.Test;

import uott.seg.mealerproject.enums.EnumComplaintStatus;

public class UserComplaintTest extends TestCase {
    @Test
    public void testGetComplStatus() {
        String incookEmail , inclientEmail, inuserCompl ;
        String outComplStatus;

        //EnumComplaintStatus expectStatus = EnumComplaintStatus.valueOf("NEW");
        String expectStatus = "New";

        UserComplaint userComplaint = new UserComplaint("cook2@mealer.com", "client2@mealer.com", "bad food");
        outComplStatus = String.valueOf(userComplaint.getComplStatus());
        assertEquals(outComplStatus, expectStatus );

    }
}