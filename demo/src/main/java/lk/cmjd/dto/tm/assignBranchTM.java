package lk.cmjd.dto.tm;

public class assignBranchTM {
    private String userID;
    private String name;
    private String role;
    private String branchID;

    public assignBranchTM() {
    }

    public assignBranchTM(String userID, String name, String role, String branchID) {
        this.userID = userID;
        this.name = name;
        this.role = role;
        this.branchID = branchID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    @Override
    public String toString() {
        return "assignBranchDto{" +
                "userID='" + userID + '\'' +
                ", name='" + name + '\'' +
                ", role='" + role + '\'' +
                ", branchID='" + branchID + '\'' +
                '}';
    }
}
