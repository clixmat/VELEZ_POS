package VELEZ_POS.enums;

public enum Roles {
    ADMINISTRATOR('A', "ADMINISTRATOR"),
    SALES('S', "SALES");

    private final char code;
    private final String description;

    Roles(char code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Roles fromCode(char code) {
        for (Roles role : Roles.values()) {
            if (role.code == code) return role;
        }
        throw new IllegalArgumentException("INVALID ROLE TRY AGAIN: " + code);
    }

    public String getRolDescription() {
        return this.description;
    }
}
