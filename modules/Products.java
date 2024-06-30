package VELEZ_POS_EDA.modules;

import java.util.LinkedList;
import java.util.Scanner;

import VELEZ_POS_EDA.utils.InputUtils;
import VELEZ_POS_EDA.enums.Colors;
import VELEZ_POS_EDA.enums.Genders;
import VELEZ_POS_EDA.enums.Sizes;

public class Products {
    private Scanner scanner;
    private static final LinkedList<Products> products = new LinkedList<>();

    private Colors color;
    private double sellPrice;
    private Genders gender;
    private int sku;
    private int stock;
    private Sizes size;
    private String model;
    private String name;

    public Products(Scanner scanner) {
        this.scanner = scanner;
    }

    public Products(int sku, int stock, String name, String model, Colors color, Genders gender, double sellPrice, Sizes size) {
        this.color = color;
        this.gender = gender;
        this.model = model;
        this.name = name;
        this.sellPrice = sellPrice;
        this.size = size;
        this.sku = sku;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public double validateSellPrice() {
        double sellPrice;
        do {
            System.out.print("ENTER SELL PRICE: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("THAT'S NOT A VALID NUMBER");
                scanner.next();
            }
            sellPrice = scanner.nextDouble();
        } while (sellPrice <= 0);
        return sellPrice;
    }

    public Genders getGender() {
        Genders gender = null;
        boolean firstAttempt = true;
        while (gender == null) {
            if (firstAttempt) {
                System.out.print("GENDER: M for MEN F for WOMEN or U for UNISEX: ");
                firstAttempt = false;
            }
            String genderInput = scanner.nextLine().toUpperCase();
            try {
                if (!genderInput.isEmpty()) {
                    gender = Genders.fromCode(genderInput.charAt(0));
                }
            } catch (IllegalArgumentException e) {
                System.out.println("INVALID GENDER. GENDER: M FOR MEN F FOR WOMEN OR U FOR UNISEX: ");
                System.out.print("GENDER: M for MEN F for WOMEN or U for UNISEX: ");
            }
        }
        return gender;
    }

    public Sizes getSize() {
        Sizes size = null;
        boolean firstAttempt = true;
        while (size == null) {
            if (firstAttempt) {
                System.out.print("SIZE: XL FOR X-LARGE, L FOR LARGE, M FOR MEDIUM, S FOR SMALL: ");
                firstAttempt = false;
            }
            String sizeInput = scanner.nextLine().toUpperCase();
            try {
                if (!sizeInput.isEmpty()) {
                    size = Sizes.fromCode(sizeInput.charAt(0));
                }
            } catch (IllegalArgumentException e) {
                System.out.println("INVALID SIZE. Size: XL FOR X-LARGE, L FOR LARGE, M FOR MEDIUM, S FOR SMALL:");
                System.out.print("SIZE: XL FOR X-LARGE, L FOR LARGE, M FOR MEDIUM, S FOR SMALL: ");
            }
        }
        return size;
    }

    public int getSku() {
        int sku;
        do {
            System.out.print("ENTER SKU NUMBER (4 digits): ");
            while (!scanner.hasNextInt()) {
                System.out.println("THAT'S NOT A VALID NUMBER");
                scanner.next();
            }
            sku = scanner.nextInt();
        } while (String.valueOf(sku).length() != 4);
        return sku;
    }

    public Colors getColor() {
        Colors color = null;
        boolean firstAttempt = true;
        while (color == null) {
            if (firstAttempt) {
                System.out.print("SELECT COLOR: R for RED, G for GREEN, B for BLACK, W for WHITE: ");
                firstAttempt = false;
            }
            String colorInput = scanner.nextLine().toUpperCase();
            try {
                if (!colorInput.isEmpty()) {
                    color = Colors.fromCode(colorInput.charAt(0));
                }
            } catch (IllegalArgumentException e) {
                System.out.println("INVALID COLOR. COLOR: R for RED, G for GREEN, B for BLACK, W for WHITE: ");
                System.out.print("SELECT COLOR: R for RED, G for GREEN, B for BLACK, W for WHITE: ");
            }
        }
        return color;
    }

    public int getStock() {
        int stock;
        do {
            System.out.print("ENTER STOCK: ");
            while (!scanner.hasNextInt()) {
                System.out.println("THAT'S NOT A VALID NUMBER");
                scanner.next();
            }
            stock = scanner.nextInt();
        } while (stock <= 0);
        return stock;
    }

    public static LinkedList<Products> getProducts() {
        LinkedList<Products> sortedProducts = new LinkedList<>(products);
        for (int i = 0; i < sortedProducts.size() - 1; i++) {
            for (int j = 0; j < sortedProducts.size() - i - 1; j++) {
                if (sortedProducts.get(j).getName().compareTo(sortedProducts.get(j + 1).getName()) > 0) {
                    Products temp = sortedProducts.get(j);
                    sortedProducts.set(j, sortedProducts.get(j + 1));
                    sortedProducts.set(j + 1, temp);
                }
            }
        }
        return sortedProducts;
    }

    public void addProduct(Products product) {
        products.add(product);
    }

    public Products findProductBySku(int sku) {
        for (Products product : products) {
            if (product.sku == sku) return product;
        }
        return null;
    }

    private void printMenu() {
        System.out.println("----------------------------");
        System.out.println("VELEZ POS - PRODUCT MODULE");
        System.out.println("----------------------------");
        System.out.println("1. LIST TO PRODUCTS");
        System.out.println("2. CREATE NEW PRODUCT");
        System.out.println("0. EXIT");
        System.out.println("----------------------------");
        System.out.print("ENTER YOUR CHOICE: ");
    }

    void handleListAllProducts() {
        if (getProducts().isEmpty()) {
            System.out.println("NO PRODUCTS FOUND \n");
            System.out.println("PLEASE CREATE NEW PRODUCTS TO DISPLAY THIS LIST");
        } else {
            System.out.println("ACTIVE PRODUCTS: \n");
            printTableHeader();
            for (Products product : getProducts()) {
                System.out.println(product);
            }
        }
    }

    private void handleProductCreation() {
        Products newProduct = createNewProduct();
        addProduct(newProduct);
        printTableHeader();
        for (Products product : getProducts()) {
            System.out.println(product);
        }
    }

    public void runProductsCreationLoop() {
        printMenu();
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                handleListAllProducts();
                break;
            case 2:
                handleProductCreation();
                break;
            case 0:
                System.out.println("EXITING PRODUCT MODULE...");
                return;
            default:
                System.out.println("INVALID CHOICE. PLEASE ENTER 1 OR 0.");
                break;
        }
        runProductsCreationLoop();
    }

    public Products createNewProduct() {
        int sku = getSku();
        scanner.nextLine();
        int stock = getStock();
        scanner.nextLine();
        String name = InputUtils.getInput(scanner,"ENTER NAME PRODUCT: ", "INVALID NAME. ONLY LETTERS, SPACES, AND TILDES ARE ALLOWED.");
        String model = InputUtils.getInput(scanner,"ENTER PRODUCT MODEL: ", "INVALID MODEL. ONLY LETTERS, SPACES, AND TILDES ARE ALLOWED.");
        Colors color = getColor();
        Genders gender = getGender();
        double sellPrice = validateSellPrice();
        Sizes size = getSize();

        System.out.println("NEW PRODUCT CREATED SUCCESSFULLY:\n");
        return new Products(sku, stock, name, model, color, gender, sellPrice, size);
    }

    public void discountStock (int quantity,Products product){
        for(Products products: products){
            if(products == product) products.setStock(products.stock-quantity);
        }
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public static void printTableHeader() {
        System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s %-15s %-15s\n",
                "SKU",
                "STOCK",
                "NAME",
                "MODEL",
                "COLOR",
                "GENDER",
                "PRICE",
                "SIZE");
    }

    @Override
    public String toString() {
        return String.format("%-15s %-15s %-15s %-15s %-15s %-15s %-15.2f %-15s",
                this.sku,
                this.stock,
                this.name,
                this.model,
                this.color.getDescription(),
                this.gender.getDescription(),
                this.sellPrice,
                this.size.getDescription());
    }
}
