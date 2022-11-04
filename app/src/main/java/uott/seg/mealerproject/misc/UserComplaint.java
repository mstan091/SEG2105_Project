package uott.seg.mealerproject.misc;

import java.util.Date;

import uott.seg.mealerproject.enums.EnumComplaintStatus;

public class UserComplaint {

    public static final String ROWID = "rowID";
    public static final String COOK_EMAIL = "cookEmail";
    public static final String CLIENT_EMAIL = "clientEmail";
    public static final String COMPLAINT = "userCompl";

    String cookEmail;
    String clientEmail;
    String userCompl;
    long complId;

    EnumComplaintStatus complStatus;

    Date complDate;

    public UserComplaint() {

    }

    public UserComplaint (String cookEmail, String clientEmail, String userCompl) {
        this.cookEmail = cookEmail;
        this.clientEmail = clientEmail;
        this.userCompl = userCompl;
        this.complStatus = EnumComplaintStatus.NEW;
    }

    public EnumComplaintStatus getComplStatus() {
        return complStatus;
    }

    public void setComplStatus(EnumComplaintStatus complStatus) {
        this.complStatus = complStatus;
    }

    public String getCookEmail() {
        return cookEmail;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setCookEmail(String cookEmail) {
        this.cookEmail = cookEmail;
    }

    public String getUserCompl() {
        return userCompl;
    }

    public void setUserCompl(String userCompl) {
        this.userCompl = userCompl;
    }

    public long getComplId() {
        return complId;
    }

    public void setComplId(long complId) {
        this.complId = complId;
    }



}
