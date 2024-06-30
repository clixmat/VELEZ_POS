package VELEZ_POS.enums;

public enum Colors {
    RED('R', "RED"),
    GREEN('G', "GREEN"),
    BLACK('B', "BLACK"),
    WHITE('W', "WHITE");

    private final char code;
    private final String description;

    Colors(char code, String description) {
        this.code = code;
        this.description = description;
    }

    public static Colors fromCode(char code) {
        for (Colors color : Colors.values()) {
            if (color.code == code)  return color;
        }
        throw new IllegalArgumentException("COLOR NOT FOUND TRY AGAIN: " + code);
    }

    public String getDescription() {
        return this.description;
    }
}
