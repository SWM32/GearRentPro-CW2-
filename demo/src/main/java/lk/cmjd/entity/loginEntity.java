package lk.cmjd.entity;

public class loginEntity {
    private String userId;
    private String username;
    private String password;
    private String Role;
    private String Branch;

    public loginEntity() {
    }

    public loginEntity(String userId, String username, String password, String role, String branch) {
        this.userId = userId;
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    @Override
    public String toString() {
        return "signUpEntity{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", Role='" + Role + '\'' +
                ", Branch='" + Branch + '\'' +
                '}';
    }
}
