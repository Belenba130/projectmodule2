import Entity.ICategory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Category implements ICategory,Serializable {
    private static List<Category> categories;
    private static final String CATEGORY_FILE = "C:\\Users\\Owner\\IdeaProjects\\projectmodule2\\src\\DataBase\\Category.txt";

    private int id;
    private String name;
    private String description;
    private boolean status;

    public Category() {
    }

    public Category(int id, String name, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id, List<Category> existingCategories) throws Exception {
        if (existingCategories == null) {
            existingCategories = new ArrayList<>();
        }

        if (id > 0) {
            boolean isUniqueId = existingCategories.stream()
                    .noneMatch(category -> category.getId() == id);

            if (isUniqueId) {
                this.id = id;
            } else {
                throw new Exception("ID must be unique");
            }
        } else {
            throw new Exception("ID must be greater than 0");
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) throws Exception {
        if (name != null && name.length() >= 6 && name.length() <= 30) {
            this.name = name;
        } else {
            throw new Exception("Name must be between 6 and 30 characters");
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws Exception {
        if (description != null && !description.trim().isEmpty()) {
            this.description = description;
        } else {
            throw new Exception("Description must not be empty");
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public void inputDataCategory(Scanner sc) {
        System.out.println("Enter Category Information");

        try {
            System.out.println("Enter ID");
            setId(sc.nextInt(), categories);
            sc.nextLine();

            System.out.println("Enter the name");
            setName(sc.nextLine());

            System.out.println("Enter the description");
            setDescription(sc.nextLine());

            System.out.println("Enter status");
            setStatus(sc.nextBoolean());
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
        System.out.println("Description: " + description);
        System.out.println("Status: " + (status ? "Active" : "Inactive"));
    }

    public static void writeCategoriesToFile(List<Category> categories) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CATEGORY_FILE))) {
            oos.writeObject(categories);
            System.out.println("Categories written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Category> readCategoriesFromFile() {
        List<Category> categories = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(CATEGORY_FILE))) {
            categories = (List<Category>) ois.readObject();
            System.out.println("Categories read from file successfully.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public String toString() {
        return String.format("%d,%s,%s,%b", id, name, description, status);
    }
}