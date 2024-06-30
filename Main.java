package VELEZ_POS;

import java.util.Scanner;

import VELEZ_POS.modules.Reports;
import VELEZ_POS.modules.Users;
import VELEZ_POS.modules.Products;
import VELEZ_POS.modules.Sales;
import VELEZ_POS.enums.Roles;


public class Main {
    private final Scanner scanner;
    private final Users users;
    private final Products products;
    private final Sales sales;
    private final Reports report;

    public Main(Scanner scanner, Users users, Products products, Sales sales, Reports report) {
        this.scanner = scanner;
        this.users = users;
        this.products = products;
        this.sales = sales;
        this.report = report;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Users users = new Users(scanner);
        Products products = new Products(scanner);
        Sales sales = new Sales(scanner, products);
        Reports report = new Reports();
        Main main = new Main(scanner, users, products, sales, report);
        main.runRoleSelectionMenu();
    }

    public void runRoleSelectionMenu() {
        int choice;
        System.out.println("------------------------------------------------------------");
        System.out.println("VELEZ POS - ROLE SELECTION");
        System.out.println("------------------------------------------------------------");
        System.out.println("1. ADMINISTRATOR");
        System.out.println("2. SALES");
        System.out.println("0. EXIT");
        System.out.println("------------------------------------------------------------");
        System.out.print("ENTER YOUR CHOICE (1-2): ");
        if (scanner.hasNextInt()) {
            choice = scanner.nextInt();
            scanner.nextLine();
        } else {
            scanner.next();
            runRoleSelectionMenu();
            return;
        }
        switch (choice) {
            case 1:
                runRoleBasedMenu(Roles.ADMINISTRATOR);
                break;
            case 2:
                runRoleBasedMenu(Roles.SALES);
                break;
            case 0:
                System.out.println("EXITING...");
                return;
            default:
                System.out.println("INVALID CHOICE. PLEASE ENTER 1, 2 OR 0.");
                break;
        }
        runRoleSelectionMenu();
    }

    public void runRoleBasedMenu(Roles role) {
        int choice;
        if (role == Roles.ADMINISTRATOR) {
            System.out.println("------------------------------------------------------------");
            System.out.println("VELEZ POS - ADMIN MENU");
            System.out.println("------------------------------------------------------------");
            System.out.println("1. USER MODULE");
            System.out.println("2. PRODUCT MODULE");
            System.out.println("3. REPORT MODULE");
            System.out.println("0. EXIT TO ROLE SELECTION");
            System.out.println("------------------------------------------------------------");
            System.out.print("ENTER YOUR CHOICE (1-3): ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.next();
                runRoleBasedMenu(role);
                return;
            }
            switch (choice) {
                case 1:
                    users.runUserCreationLoop();
                    break;
                case 2:
                    products.runProductsCreationLoop();
                    break;
                case 3:
                    report.runReportViewLoop();
                    break;
                case 0:
                    System.out.println("EXITING ADMIN MENU...");
                    return;
                default:
                    System.out.println("ENTER YOUR CHOICE (1-3): ");
                    break;
            }
            runRoleBasedMenu(role);
        } else if (role == Roles.SALES) {
            System.out.println("----------------------------");
            System.out.println("VELEZ POS - SALES MENU");
            System.out.println("----------------------------");
            System.out.println("1. SALES MODULE");
            System.out.println("0. EXIT TO ROLE SELECTION");
            System.out.println("----------------------------");
            System.out.print("ENTER YOUR CHOICE: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.next();
                runRoleBasedMenu(role);
                return;
            }
            switch (choice) {
                case 1:
                    sales.runSaleCreationLoop();
                    break;
                case 0:
                    System.out.println("EXITING SALES MENU...");
                    return;
                default:
                    System.out.println("INVALID CHOICE. PLEASE ENTER 1 OR 0.");
                    break;
            }
            runRoleBasedMenu(role);
        }
    }
}