import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

class Product {
    private String name;
    private String barcode;
    private double price;
    private int stock;

    public Product(String name, double price, int stock) {
        this.name = name;
        this.barcode = generateBarcode();
        this.price = price;
        this.stock = stock;
    }

    private String generateBarcode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getName() {
        return name;
    }

    public String getBarcode() {
        return barcode;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}

class Supplier {
    private String name;
    private List<Product> products;

    public Supplier(String name, List<Product> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }
}

class InventoryManager {
    private List<Product> products;
    private List<Supplier> suppliers;

    public InventoryManager() {
        this.products = new ArrayList<>();
        this.suppliers = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    public void trackInventory() {
        System.out.println("Inventory Levels:");
        for (Product product : products) {
            System.out.println(product.getName() + " - Stock: " + product.getStock());
        }
    }

    public void reorderProducts(int threshold) {
        Random rand = new Random();
        for (Product product : products) {
            if (product.getStock() < threshold) {
                System.out.println("Reordering " + product.getName() + " (" + product.getBarcode() + ")");
                product.setStock(product.getStock() + rand.nextInt(41) + 10);
            }
        }
    }

    public Product scanBarcode(String barcode) {
        for (Product product : products) {
            if (product.getBarcode().equals(barcode)) {
                return product;
            }
        }
        System.out.println("Product not found.");
        return null;
    }
}

public class fitness {
    public static void main(String[] args) {
        Product product1 = new Product("Burger", 4.99, 50);
        Product product2 = new Product("Fries", 2.49, 20);

        List<Product> supplierProducts = new ArrayList<>();
        supplierProducts.add(product1);
        supplierProducts.add(product2);

        Supplier supplier1 = new Supplier("Food Supplier", supplierProducts);

        InventoryManager manager = new InventoryManager();
        manager.addProduct(product1);
        manager.addProduct(product2);
        manager.addSupplier(supplier1);

        manager.trackInventory();

        Product productToSell = manager.scanBarcode(product1.getBarcode());
        if (productToSell != null && productToSell.getStock() > 0) {
            System.out.println("Selling " + productToSell.getName());
            productToSell.setStock(productToSell.getStock() - 1);
        } else {
            System.out.println("Product out of stock.");
        }

        manager.trackInventory();

        manager.reorderProducts(10);

        manager.trackInventory();

        System.out.println("Product 1 barcode: " + product1.getBarcode());
    }
}
