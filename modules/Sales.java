package VELEZ_POS.modules;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Sales {
    private Scanner scanner;
    private static LinkedList<Sales> sales;
    private Queue<Sales> pendingSales;
    private Products product;

    private String prodName;
    private double subTotal;
    private double tax;
    private double total;
    private int quantity;
    private Users seller;

    public Sales(Scanner scanner, Products product) {
        this.scanner = scanner;
        sales = new LinkedList<>();
        pendingSales = new LinkedList<>();
        this.product = product;
    }

    public Sales(String product , double subTotal, double tax, double total, int quantity, Users seller) {
        this.subTotal = subTotal;
        this.tax = tax;
        this.total = total;
        this.quantity = quantity;
        this.seller = seller;
        this.prodName = product;
    }

    public Products getProduct() {
        return product;
    }

    public double getTotal() {
        return total;
    }

    public int getQuantity() {
        return quantity;
    }

    public Users getSeller() {
        return seller;
    }

    public static LinkedList<Sales> getSales() {
        LinkedList<Sales> sortedSales = new LinkedList<>(sales);
        for (int i = 0; i < sortedSales.size() - 1; i++) {
            for (int j = 0; j < sortedSales.size() - i - 1; j++) {
                if (sortedSales.get(j).getTotal() < sortedSales.get(j + 1).getTotal()) {
                    Sales temp = sortedSales.get(j);
                    sortedSales.set(j, sortedSales.get(j + 1));
                    sortedSales.set(j + 1, temp);
                }
            }
        }
        return sortedSales;
    }

    public void addSale(Sales sale) {
        pendingSales.add(sale);
    }

    public void processSales() {
        while (!pendingSales.isEmpty()) {
            Sales sale = pendingSales.remove();
            sales.add(sale);
        }
    }

    private void printMenu() {
        System.out.println("----------------------------");
        System.out.println("VELEZ POS - SALES MODULE");
        System.out.println("----------------------------");
        System.out.println("1. LIST OF SALES");
        System.out.println("2. CREATE NEW SALE");
        System.out.println("0. EXIT");
        System.out.println("----------------------------");
        System.out.print("ENTER YOUR CHOICE: ");
    }

    private void handleListAllSales() {
        if (getSales().isEmpty()) {
            System.out.println("NO SALES FOUND \n");
            System.out.println("PLEASE CREATE NEW SALE TO DISPLAY THIS LIST");
        } else {
            System.out.println("ALL SALES: \n");
            printTableHeader();
            LinkedList<Sales> salesList = getSales();
            for (int i = 0; i < salesList.size(); i++) {
                System.out.println((i + 1) + ". " + salesList.get(i));
            }
        }
    }

    public void handleSaleCreation() {
        if (Products.getProducts().isEmpty()) {
            System.out.println("NO PRODUCTS FOUND. PLEASE CREATE A PRODUCT FIRST.");
            return;
        }
        System.out.println("CREATE NEW SALE: ");
        System.out.println("-----------------------------------------\n");
        System.out.print("ENTER THE SELLER DOCUMENT NUMBER: ");
        int documentNumber = scanner.nextInt();
        scanner.nextLine();
        Users seller = Users.findUserByDocumentNumber(documentNumber);
        System.out.println(seller);
        if (seller == null) {
            System.out.println("SELLER NOT FOUND. PLEASE CREATE THE SELLER FIRST.");
            return;
        }
        product.handleListAllProducts();


        int sku = product.getSku();
        Products tempProduct;
        tempProduct = product.findProductBySku(sku);
        if (tempProduct != null) {
            String prodName = tempProduct.getName();
            System.out.print("ENTER THE QUANTITY: ");

            int quantity = scanner.nextInt();
            scanner.nextLine();

            double subTotal = tempProduct.getSellPrice() * quantity;
            double tax = subTotal * 0.18;
            double total = subTotal + tax;
            Sales sale = new Sales(prodName, subTotal, tax, total, quantity, seller);
            addSale(sale);
            processSales();
            product.discountStock(quantity,tempProduct);
            System.out.println("SALE CREATED SUCCESSFULLY:\n");
        } else System.out.println("PRODUCT NOT FOUND. PLEASE CREATE THE PRODUCT FIRST.");

    }

    public void runSaleCreationLoop() {
        int choice;
        do {
            printMenu();
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    handleListAllSales();
                    break;
                case 2:
                    handleSaleCreation();
                    break;
                case 0:
                    System.out.println("EXITING USER MODULE...");
                    break;
                default:
                    System.out.println("INVALID CHOICE. PLEASE ENTER 1 OR 0.");
                    break;
            }
        } while (choice != 0);
    }

    public static void printTableHeader() {
        System.out.printf("%-20s %-15s %-15s %-15s %-15s %-15s\n",
                "PRODUCT",
                "QUANTITY",
                "SUB-TOTAL",
                "IGV",
                "TOTAL",
                "SELLER");
    }

    @Override
    public String toString() {
        return String.format("%-15s %-15s %-15.2f %-15.2f %-15.2f %-15s",
                this.prodName,
                this.quantity,
                this.subTotal,
                this.tax,
                this.total,
                this.seller.getName());
    }


}
