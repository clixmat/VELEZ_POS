package VELEZ_POS_EDA.enums;

public enum Genders {
    MALE('M', "MALE"),
    FEMALE('F', "FEMALE"),
    UNISEX('U', "UNISEX");

    private final char code;
    private final String description;

    Genders(char code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Genders fromCode(char code) {
        for (Genders gender : Genders.values()) {
            if (gender.code == code) {
                return gender;
            }
        }
        throw new IllegalArgumentException("GENDER NOT FOUND, TRY AGAIN: " + code);
    }

    public String getDescription() {
        return this.description;
    }
}
