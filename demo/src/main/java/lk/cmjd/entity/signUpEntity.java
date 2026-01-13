package lk.cmjd.entity;

public class signUpEntity {
    private String userId;
    private String username;
    private String password;
    private String Role;

    public signUpEntity() {
    }

    public signUpEntity(String userId, String username, String password, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        Role = role;
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

    @Override
    public String toString() {
        return "signUpEntity{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }
}
