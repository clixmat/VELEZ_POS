package VELEZ_POS.modules;

import java.util.ArrayList;
import java.util.List;

public class Reports {
    private static final Reports reports = new Reports();

    public Reports() {
    }

    private void getTotalAmountSold(List<Sales> sales) {
        double total = 0;
        for (Sales sale : sales) {
            total += sale.getTotal();
        }
        reports.showTotalAmountSold(total);
    }

    private void getTotalProductsSold(List<Sales> sales) {
        int quantity = 0;
        for (Sales sale : sales) {
            quantity += sale.getQuantity();
        }
        reports.showTotalProductsSold(quantity);
    }

    private List<Users> getSellerWithSales(List<Sales> sales) {
        List<Users> sellers = new ArrayList<>();

        for (Sales sale : sales) {
            Users currentSale = sale.getSeller();
            boolean exists = false;
            for (Users seller : sellers) {
                if (seller.getDocumentNumber() == currentSale.getDocumentNumber()) {
                    exists = true;
                    break;
                }
            }
            if (!exists) sellers.add(currentSale);
        }
        return sellers;
    }

    private void getSellerWithMostSales(List<Users> sellers, List<Sales> sales) {
        Users sellerWithMostSales = null;
        int maxSales = 0;
        for (Users seller : sellers) {
            int salesQuantity = 0;
            for (Sales sale : sales) {
                if (sale.getSeller().getDocumentNumber() == seller.getDocumentNumber()) salesQuantity += 1;
            }
            if (salesQuantity > maxSales) {
                maxSales = salesQuantity;
                sellerWithMostSales = seller;
            }
        }
        if (sellerWithMostSales != null) {
            reports.showSellerWithMostSales(sellerWithMostSales.getName(), sellerWithMostSales.getLastName(),
                    maxSales);
        } else reports.showSellerWithMostSales(null, null, maxSales);
    }

    private List<Products> getProductsSold(List<Sales> sales) {
        List<Products> products = new ArrayList<>();

        for (Sales sale : sales) {
            Products currentProduct = sale.getProduct();
            if (currentProduct != null) {
                boolean exists = false;
                for (Products product : products) {
                    if (currentProduct.getSku() == product.getSku()) exists = true;
                }
                if (!exists) products.add(currentProduct);
            }
        }
        return products;
    }

    private void getMostSoldProduct(List<Products> productsSold, List<Sales> sales) {
        Products mostSoldProduct = null;
        int maxQuantity = 0;
        for (Products product : productsSold) {
            int quantity = 0;
            for (Sales sale : sales) {
                if (sale.getProduct().getSku() == product.getSku()) quantity += sale.getQuantity();
            }
            if (quantity > maxQuantity) {
                maxQuantity = quantity;
                mostSoldProduct = product;
            }
        }
    if (mostSoldProduct != null) reports.showMostSoldProduct(mostSoldProduct.toString(), maxQuantity);
        else reports.showMostSoldProduct(null, maxQuantity);
    }

    private void showSummaryBySeller(List<Users> sellers, List<Sales> sales) {
        reports.showSellerSummaryTitle();
        if (sellers.isEmpty()) reports.showErrorMessage();
        else {
            for (Users seller : sellers) {
                double total = 0;
                double commission = 0;
                for (Sales sale : sales) {
                    if (sale.getSeller().getDocumentNumber() == seller.getDocumentNumber()) total += sale.getTotal();
                }
                if (total > 2000) commission = total * 0.20;
                printTableHeader();
                showSellerSummary(seller, total, commission);
            }
        }
    }

    private void printMenu() {
        System.out.println("----------------------------");
        System.out.println("VELEZ POS - REPORT MODULE");
        System.out.println("----------------------------\n");
    }

    public void showTotalAmountSold(double totalAmountSold) {
        System.out.println("1. TOTAL AMOUNT OF SALES: S/ " + String.format("%.2f", totalAmountSold));
    }

    public void showTotalProductsSold(int totalProductsSold) {
        System.out.printf("2. PRODUCTS SOLD: %d", totalProductsSold);
    }

    public void showSellerWithMostSales(String sellerName, String lastName, int totalSales) {
        if (sellerName != null) {
            System.out.printf("\n3. SELLER WITH THE MOST SALES: %s %s, WITH %d SALES", sellerName, lastName, totalSales);
        } else System.out.println("\n3. THERE IS NO SELLER WITH MORE SALES");
    }

    public void showMostSoldProduct(String productDetail, int totalSold) {
        if (productDetail != null) System.out.printf("\n4. BEST SELLING PRODUCT: %s SOLD UNITS: %d ", productDetail, totalSold);
        else System.out.println("\n4. THERE IS NO BEST SELLING PRODUCT");
    }

    public void showSellerSummaryTitle() {
        System.out.println("5. SALES SUMMARY BY SELLER:");
    }

    public void runReportViewLoop() {
        printMenu();
        getTotalAmountSold(Sales.getSales());
        getTotalProductsSold(Sales.getSales());
        getSellerWithMostSales(getSellerWithSales(Sales.getSales()), Sales.getSales());
        getMostSoldProduct(getProductsSold(Sales.getSales()), Sales.getSales());
        showSummaryBySeller(getSellerWithSales(Sales.getSales()), Sales.getSales());
    }

    public static void printTableHeader() {
        System.out.printf("%-15s %-15s %-15s\n",
                "SELLER",
                "TOTAL SALES",
                "COMMISSION");
    }

    public void showSellerSummary(Users seller, double totalSales, double commission) {
        System.out.printf("%-15s %-15.2f %-15.2f\n",
                seller.getName() + " " + seller.getLastName(),
                totalSales,
                commission);
    }

    public void showErrorMessage() {
        System.out.println("***THERE IS NO DATA TO SHOW REPORT***\n");
    }
}
