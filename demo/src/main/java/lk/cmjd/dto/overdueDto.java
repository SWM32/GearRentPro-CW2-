package lk.cmjd.dto;

import java.time.LocalDate;

public class overdueDto {
    private String cusID;
    private String branchID;
    private String eqID;
    private LocalDate due;
    private long dueDates;
    private String contact;
    private String email;

    public overdueDto() {
    }

    public overdueDto(String cusID, String branchID, String eqID, LocalDate due, long dueDates, String contact,
            String email) {
        this.cusID = cusID;
        this.branchID = branchID;
        this.eqID = eqID;
        this.due = due;
        this.dueDates = dueDates;
        this.contact = contact;
        this.email = email;
    }

    public String getCusID() {
        return cusID;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getEqID() {
        return eqID;
    }

    public void setEqID(String eqID) {
        this.eqID = eqID;
    }

    public LocalDate getDue() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due = due;
    }

    public long getDueDates() {
        return dueDates;
    }

    public void setDueDates(long dueDates) {
        this.dueDates = dueDates;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "overdueDto{" +
                "cusID='" + cusID + '\'' +
                ", branchID='" + branchID + '\'' +
                ", eqID='" + eqID + '\'' +
                ", due=" + due +
                ", dueDates=" + dueDates +
                ", contact='" + contact + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
