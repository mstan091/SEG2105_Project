package uott.seg.mealerproject.enums;

public enum EnumCookStatus {
    SUSPENDED("Suspended"),
    REVOKED("Revoked");

    private String complaintStatus;
    private EnumCookStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    @Override
    public String toString(){
        return complaintStatus;
    }


}
