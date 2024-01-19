import Entity.ICategory;
import Entity.IProduct;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Product implements IProduct, Serializable {
    private static final float MIN_INTEREST_RATE = 0.2f;
    private static final String PRODUCT_FILE = "C:\\Users\\Owner\\IdeaProjects\\projectmodule2\\src\\DataBase\\Product.txt";


    private String id;
    private String name;
    private double importPrice;
    private double exportPrice;
    private double profit;
    private String description;
    private boolean status;
    private int categoryId;

    public Product() {
    }

    public Product(String id, String name, double importPrice, double exportPrice, String description, boolean status, int categoryId) {
        this.id = id;
        this.name = name;
        this.importPrice = importPrice;
        this.exportPrice = exportPrice;
        this.description = description;
        this.status = status;
        this.categoryId = categoryId;
        this.calProfit();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws Exception {
        if (id.length() == 4 && id.startsWith("P")) {
            this.id = id;
        } else {
            throw new Exception("ID must have 4 characters and start with 'P'");
        }
    }


        public String getName() {
            return name != null ? name : "";
        }

    public void setName(String name) throws Exception {
        if (name.length() < 6 || name.length() > 30) {
            throw new Exception("Name must be between 6 and 30 characters");
        }
        this.name = name;
    }

    public double getImportPrice() {
        return importPrice;
    }

    public void setImportPrice(double importPrice) throws Exception {
        if (importPrice > 0) {
            this.importPrice = importPrice;
        } else {
            throw new Exception("Import price must be greater than 0");
        }
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(double exportPrice) throws Exception {
        if (exportPrice / importPrice <  (1 + MIN_INTEREST_RATE)) {
            throw new Exception("Export price must be greater than or equal to " + (importPrice * (1 + MIN_INTEREST_RATE)));
        }
        this.exportPrice = exportPrice;
    }


    public double getProfit() {
        return profit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws Exception {
        if (description.trim().isEmpty()) {
            throw new Exception("Description must not be empty");
        }
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId, List<Category> existingCategories) throws Exception {
        boolean validCategoryId = existingCategories.stream()
                .anyMatch(category -> category.getId() == categoryId);

        if (validCategoryId) {
            this.categoryId = categoryId;
        } else {
            throw new Exception("Invalid category ID");
        }
    }

    @Override
    public void inputData(Scanner sc) {
        System.out.println("Enter Product Information");
        sc.nextLine();

        try {
            System.out.println("Enter ID");
            setId(sc.nextLine());

            System.out.println("Enter the name");
            setName(sc.nextLine());

            System.out.println("Enter the import price");
            setImportPrice(sc.nextDouble());
            sc.nextLine();

            System.out.println("Enter the export price");
            setExportPrice(sc.nextDouble());
            sc.nextLine();

            System.out.println("Enter the description");
            setDescription(sc.nextLine());

            System.out.println("Enter status (true/false)");
            setStatus(sc.nextBoolean());
            sc.nextLine();

            System.out.println("Enter category id");
            this.categoryId=sc.nextInt();
            sc.nextLine();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }




    @Override
    public void displayData() {
        System.out.println("-------------------------");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Import Price: " + importPrice);
        System.out.println("Export Price: " + exportPrice);
        System.out.println("Profit: " + profit);
        System.out.println("Description: " + description);
        System.out.println("Status: " + (status ? "In Stock" : "Out Of Stock"));
        System.out.println("Category ID: " + categoryId);
    }

    public void calProfit() {
        this.profit = this.exportPrice - this.importPrice;
    }

    public static void writeProductsToFile(List<Product> products) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PRODUCT_FILE))) {
            oos.writeObject(products);
            System.out.println("Products written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> readProductsFromFile() {
        List<Product> products = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PRODUCT_FILE))) {
            products = (List<Product>) ois.readObject();
            System.out.println("Products read from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return products;
    }

}
