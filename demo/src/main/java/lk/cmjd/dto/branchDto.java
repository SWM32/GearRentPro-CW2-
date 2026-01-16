package lk.cmjd.dto;

public class branchDto {
    private String branchID;
    private String name;
    private String address;
    private String contact;

    public branchDto() {
    }

    public branchDto(String branchID, String name, String address, String contact) {
        this.branchID = branchID;
        this.name = name;
        this.address = address;
        this.contact = contact;
    }

    public String getBranchID() {
        return branchID;
    }

    public void setBranchID(String branchID) {
        this.branchID = branchID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "branchDto{" +
                "branchID='" + branchID + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
