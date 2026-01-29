package lk.cmjd.dto;

public class mainDto {
    private String Role;
    private String Branch;

    public mainDto() {
    }

    public mainDto(String role, String branch) {
        Role = role;
        Branch = branch;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getBranch() {
        return Role;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    @Override
    public String toString() {
        return "mainDto{" +
                ", Role='" + Role + '\'' +
                ", Branch='" + Branch + '\'' +
                '}';
    }
}
