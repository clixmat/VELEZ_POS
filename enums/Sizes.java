package VELEZ_POS.enums;

public enum Sizes {
    XLARGE('X', "X-LARGE"),
    MEDIUM('M', "MEDIUM"),
    SMALL('S', "SMALL"),
    LARGE('L', "LARGE");

    private final char code;
    private final String description;

    Sizes(char code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Sizes fromCode(char code) {
        for (Sizes size : Sizes.values()) {
            if (size.code == code) {
                return size;
            }
        }
        throw new IllegalArgumentException("SIZE NOT FOUND TRY AGAIN:" + code);
    }

    public String getDescription() {
        return this.description;
    }
}
