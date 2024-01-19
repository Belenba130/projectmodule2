import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Store {
    private List<Category> categories;
    private List<Product> products;

    public Store() {
        categories = new ArrayList<>();
        products = new ArrayList<>();
        categories.add(new Category(1, "Category 1", "Description 1", true));
        categories.add(new Category(2, "Category 2", "Description 1", true));
        categories.add(new Category(3, "Category 3", "Description 1", true));
        categories.add(new Category(4, "Category 4", "Description 1", true));
        products.add(new Product("P001", "Product 1", 100.0, 200.0, "Description 1", true, 1));
        products.add(new Product("P002", "Product 2", 100.0, 200.0, "Description 1", true, 2));
        products.add(new Product("P003", "Product 3", 100.0, 200.0, "Description 1", true, 3));
//        categories = Category.readCategoriesFromFile();
//        products = Product.readProductsFromFile();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Store store = new Store();
        while (true) {
            System.out.println("1. Manage category");
            System.out.println("2. Manage product");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> store.ManageCategory(scanner);
                case 2 -> store.ManageProducts(scanner);
                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void ManageCategory(Scanner scanner) {
        while (true) {
            System.out.println("===== Manage Category =====");
            System.out.println("1. Add Category");
            System.out.println("2. Update Category");
            System.out.println("3. Delete Category");
            System.out.println("4. Search Category by Name");
            System.out.println("5. Display Product Count in Category");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addCategory(scanner);
                case 2 -> updateCategory(scanner);
                case 3 -> deleteCategory(scanner);
                case 4 -> searchCategoryByName(scanner);
                case 5 -> displayProductCountInCategory(scanner);
                case 6 -> {
                    saveChanges();
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void addCategory(Scanner scanner) {
        Category category = new Category();
        category.inputDataCategory(scanner);
        categories.add(category);
        saveChanges();
    }

    private void updateCategory(Scanner scanner) {
        System.out.print("Enter the ID of the category you want to update: ");
        int categoryId = scanner.nextInt();

        categories.stream()
                .filter(category -> category.getId() == categoryId)
                .findFirst()
                .ifPresentOrElse(
                        foundCategory -> {
                            System.out.println("Found category:");
                            foundCategory.displayData();
                            System.out.println("Enter the new information:");
                            foundCategory.inputDataCategory(scanner);
                            System.out.println("Category updated successfully.");
                            saveChanges();
                        },
                        () -> System.out.println("Category not found.")
                );
    }

    private void deleteCategory(Scanner scanner) {
        System.out.print("Enter the ID of the category you want to delete: ");
        int categoryId = scanner.nextInt();
        categories.removeIf(category -> category.getId() == categoryId);
        System.out.println("Category deleted successfully.");
        saveChanges();
    }

    private void searchCategoryByName(Scanner scanner) {
        System.out.print("Enter the name of the category you want to search: ");
        String name = scanner.next();
        categories.stream()
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .ifPresentOrElse(
                        foundCategory -> {
                            System.out.println("Found category:");
                            foundCategory.displayData();
                        },
                        () -> System.out.println("Category not found.")
                );
    }

    private void displayProductCountInCategory(Scanner scanner) {
        System.out.print("Enter the name of the category you want to search: ");
        String name = scanner.next();
        categories.stream()
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .ifPresentOrElse(
                        foundCategory -> {
                            System.out.println("Found category:");
                            foundCategory.displayData();
                        },
                        () -> System.out.println("Category not found.")
                );
    }

    private void ManageProducts(Scanner scanner) {
        while (true) {
            System.out.println("===== Manage Product =====");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Sort Product by Name A-Z");
            System.out.println("5. Sort Product by Profit Descending");
            System.out.println("6. Search Product");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> addProduct(scanner);
                case 2 -> updateProduct(scanner);
                case 3 -> deleteProduct(scanner);
                case 4 -> displayProductsByNameAZ();
                case 5 -> displayProductsByProfitDescending();
                case 6 -> searchProduct(scanner);
                case 7 -> {
                    saveChanges();
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void addProduct(Scanner scanner) {
        Product product = new Product();
        product.inputData(scanner);
        if (isProductNameDuplicated(product.getName())) {
            System.out.println("Error: Product with the same name already exists.");
        } else {
            products.add(product);
            System.out.println("Product added successfully.");
        }
        saveChanges();
    }

    private void updateProduct(Scanner scanner) {
        System.out.print("Enter the ID of the product you want to update: ");
        String productId = scanner.next();
        products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .ifPresentOrElse(
                        foundProduct -> {
                            System.out.println("Found product:");
                            foundProduct.displayData();
                            System.out.println("Enter the new information:");
                            foundProduct.inputData(scanner);
                            System.out.println("Product updated successfully.");
                            saveChanges();
                        },
                        () -> System.out.println("Product not found.")
                );
    }

    private void deleteProduct(Scanner scanner) {
        System.out.print("Enter the ID of the product you want to delete: ");
        String productId = scanner.next();
        products.removeIf(product -> product.getId().equals(productId));
        System.out.println("Product deleted successfully.");
        saveChanges();
    }

    private void displayProductsByNameAZ() {
        products.sort(Comparator.comparing(Product::getName));
        for (Product product : products) {
            product.displayData();
        }
    }

    private void displayProductsByProfitDescending() {
        products.sort(Comparator.comparing(Product::getProfit).reversed());
        for (Product product : products) {
            product.displayData();
        }
    }

    private void searchProduct(Scanner scanner) {
        System.out.print("Enter the search keyword: ");
        String searchKeyword = scanner.next();

        products.stream()
                .filter(product ->
                        product.getName().toLowerCase().contains(searchKeyword.toLowerCase()) ||
                                String.valueOf(product.getImportPrice()).contains(searchKeyword) ||
                                String.valueOf(product.getExportPrice()).contains(searchKeyword)
                )
                .forEach(Product::displayData);
    }

    private void saveChanges() {
        Category.writeCategoriesToFile(categories);
        Product.writeProductsToFile(products);
    }

    private boolean isProductNameDuplicated(String productName) {
        return products.stream().anyMatch(product -> product.getName().equalsIgnoreCase(productName));
    }


}
