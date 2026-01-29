package lk.cmjd.util;

public class sessionUtil {
    private static sessionUtil session;
    private String role;
    private String branch;

    private sessionUtil() {
    }

    public static sessionUtil getSession() {
        if (session == null) {
            session = new sessionUtil();
            return session;
        }
        return session;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBranch() {
        return branch;
    }

    public void clearSession() {
        role = null;
        branch = null;
    }
}
