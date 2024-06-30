package VELEZ_POS.modules;

import VELEZ_POS.enums.Roles;
import VELEZ_POS.utils.InputUtils;

import java.util.LinkedList;
import java.util.Scanner;

public class Users {
    private Scanner scanner;
    private static LinkedList<Users> users;
    private String name;
    private String lastName;
    private int documentNumber;
    private Roles role;

    public Users(Scanner scanner) {
        this.scanner = scanner;
        users = new LinkedList<>();
    }

    public Users(String name, String lastName, int documentNumber, Roles role) {
        this.name = name;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.role = role;
    }

    public static LinkedList<Users> getUsers() {
        LinkedList<Users> sortedUsers = new LinkedList<>(users);
        for (int i = 0; i < sortedUsers.size() - 1; i++) {
            for (int j = 0; j < sortedUsers.size() - i - 1; j++) {
                if (sortedUsers.get(j).getLastName().compareTo(sortedUsers.get(j + 1).getLastName()) > 0) {
                    Users temp = sortedUsers.get(j);
                    sortedUsers.set(j, sortedUsers.get(j + 1));
                    sortedUsers.set(j + 1, temp);
                }
            }
        }
        return sortedUsers;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getDocumentNumber() {
        return documentNumber;
    }

    private int validateDocumentNumber() {
        System.out.print("ENTER DOCUMENT NUMBER (8 digits): ");
        if (!scanner.hasNextInt()) {
            System.out.println("THAT'S NOT A VALID NUMBER");
            scanner.next();
            return validateDocumentNumber();
        }
        int documentNumber = scanner.nextInt();
        if (String.valueOf(documentNumber).length() != 8) {
            System.out.println("DOCUMENT NUMBER MUST BE 8 DIGITS");
            return validateDocumentNumber();
        }
        return documentNumber;
    }

    public Roles validateRole(boolean firstAttempt) {
        Roles role = null;
        while (role == null) {
            if (firstAttempt) {
                System.out.print("PLEASE ENTER YOUR ROLE (A) FOR ADMIN (S) FOR SALES: ");
                firstAttempt = false;
            }
            String roleInput = scanner.nextLine().toUpperCase();
            try {
                if (!roleInput.isEmpty()) role = Roles.fromCode(roleInput.charAt(0));
            } catch (IllegalArgumentException e) {
                System.out.println("INVALID ROLE. PLEASE ENTER A FOR ADMIN OR S FOR SALES.");
                System.out.print("PLEASE ENTER YOUR ROLE (A) FOR ADMIN (S) FOR SALES: ");
            }
        }
        return role;
    }

    public static Users findUserByDocumentNumber(int documentNumber) {
        for (Users user : users) {
            if (user.documentNumber == documentNumber) return user;
        }
        return null;
    }

    public void addUser(Users user) {
        users.add(user);
    }

    private void printMenu() {
        System.out.println("----------------------------");
        System.out.println("VELEZ POS - USER MODULE");
        System.out.println("----------------------------");
        System.out.println("1. LIST USERS");
        System.out.println("2. CREATE NEW USER");
        System.out.println("0. EXIT");
        System.out.println("----------------------------");
        System.out.print("ENTER YOUR CHOICE: ");
    }

    private void handleListAllUsers() {
        if (getUsers().isEmpty()) {
            System.out.println("NO USERS FOUND \n");
            System.out.println("PLEASE CREATE NEW USERS TO DISPLAY THIS LIST");
        } else {
            System.out.println("ACTIVE USERS: \n");
            printTableHeader();
            for (Users user : getUsers()) {
                System.out.println(user);
            }
        }
    }

    private void handleUserCreation() {
        Users newUser = createNewUser();
        addUser(newUser);
        printTableHeader();
        for (Users user : getUsers()) {
            System.out.println(user);
        }
    }

    public void runUserCreationLoop() {
        printMenu();
        while (!scanner.hasNextInt()) {
            System.out.println("INVALID CHOICE. PLEASE ENTER A NUMBER.");
            scanner.next();
        }
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                handleListAllUsers();
                break;
            case 2:
                handleUserCreation();
                break;
            case 0:
                System.out.println("EXITING USER MODULE...");
                return;
            default:
                System.out.println("INVALID CHOICE. PLEASE ENTER 1, 2 OR 0.");
                break;
        }
        runUserCreationLoop();
    }

    public Users createNewUser() {
        String name = InputUtils.getInput(scanner, "ENTER NAME: ", "INVALID NAME. ONLY LETTERS, SPACES, AND TILDES ARE ALLOWED.");
        String lastName = InputUtils.getInput(scanner,"ENTER LAST NAME: ", "INVALID LAST NAME. ONLY LETTERS, SPACES, AND TILDES ARE ALLOWED.");
        int documentNumber = validateDocumentNumber();
        Roles role = validateRole(true);

        System.out.println("NEW USER CREATED SUCCESSFULLY:\n");
        return new Users(name, lastName, documentNumber, role);
    }

    public static void printTableHeader() {
        System.out.printf("%-15s %-15s %-15s %-15s\n",
                "LAST NAME",
                "NAME",
                "DOCUMENT",
                "ROLE");
    }

    @Override
    public String toString() {
        return String.format("%-15s %-15s %-15d %-15s",
                this.lastName,
                this.name,
                this.documentNumber,
                this.role.getRolDescription());
    }
}
