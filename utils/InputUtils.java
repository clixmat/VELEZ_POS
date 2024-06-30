package VELEZ_POS_EDA.utils;

import java.util.Scanner;

public class InputUtils {
    private static Scanner scanner;

    public InputUtils(Scanner scanner) {
        InputUtils.scanner = scanner;
    }

    public static String getInput(Scanner scanner, String prompt, String errorMessage) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (!input.matches("^[\\p{L} .'-]+$")) {
                System.out.println(errorMessage);
                input = null;
            }
        } while (input == null);
        return input;
    }
}