package net.ckmk.api.prototypes;

public class User extends SafeUser{

    private final String uat;

    public User(int id, String email, String uat, boolean isAdmin, String isAllowed, String fullName, int dbSpaceTaken) {
        super(id, email, isAdmin, isAllowed, fullName, dbSpaceTaken);
        this.uat = uat;
    }

    public String getUat() {
        return uat;
    }
}
