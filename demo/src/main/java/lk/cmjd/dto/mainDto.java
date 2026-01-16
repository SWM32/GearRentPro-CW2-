package lk.cmjd.dto;

public class mainDto {
    private String userId;
    private String username;
    private String Role;
    private String Branch;

    public mainDto() {
    }

    public mainDto(String userId, String username, String role, String branch) {
        this.userId = userId;
        this.username = username;
        Role = role;
        Branch = branch;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", Role='" + Role + '\'' +
                ", Branch='" + Branch + '\'' +
                '}';
    }
}
