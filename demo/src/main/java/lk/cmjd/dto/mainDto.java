package lk.cmjd.dto;

public class mainDto {
    private String userId;
    private String username;
    private String Role;

    public mainDto() {
    }

    public mainDto(String userId, String username, String role) {
        this.userId = userId;
        this.username = username;
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

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "mainDto{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }
}
