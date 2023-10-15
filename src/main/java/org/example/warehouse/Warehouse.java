package org.example.warehouse;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

// A class representing a warehouse for managing products.
public class Warehouse {
    private final String name;
    private final List<ProductRecord> products;
    private final List<ProductRecord> changedProducts;

    // Private constructor for creating a warehouse with a given name.
    private Warehouse(String name) {
        this.name = name;
        this.products = new ArrayList<>();
        this.changedProducts = new ArrayList<>();
    }

    // Static factory method to create a default warehouse with the name "Warehouse."
    public static Warehouse getInstance() {
        return getInstance("Warehouse");
    }

    // Static factory method to create a warehouse with a specific name.
    public static Warehouse getInstance(String name) {
        return new Warehouse(name);
    }

    // Check if the warehouse is empty (no products).
    public boolean isEmpty() {
        return products.isEmpty();
    }

    // Get an unmodifiable list of products stored in the warehouse.
    public List<ProductRecord> getProducts() {
        return Collections.unmodifiableList(products);
    }

    // Add a new product to the warehouse with the given attributes.
    public ProductRecord addProduct(UUID id, String name, Category category, BigDecimal price) {
        // Input validation and handling defaults.
        if (name == null || name.isEmpty()) throw new IllegalArgumentException("Product name can't be null or empty.");
        if (category == null) throw new IllegalArgumentException("Category can't be null.");
        if (price == null) price = BigDecimal.ZERO;
        if (id == null) id = UUID.randomUUID();
        if (productExists(id)) throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");

        // Create a new product and add it to the list.
        var product = new ProductRecord(id, name, category, price);
        products.add(product);

        return product;
    }

    // Check if a product with the given ID already exists in the warehouse.
    private boolean productExists(UUID id) {
        return products.stream().anyMatch(productRecord -> productRecord.uuid().equals(id));
    }

    // Update the price of a product with a given ID.
    public void updateProductPrice(UUID id, BigDecimal newPrice) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).uuid().equals(id)) {
                var changedProduct = new ProductRecord(
                        products.get(i).uuid(),
                        products.get(i).name(),
                        products.get(i).category(),
                        newPrice);

                // Add the changed product to the list of changed products.
                changedProducts.add(products.set(i, changedProduct));
                return;
            }
        }
        throw new IllegalArgumentException("Product with that id doesn't exist.");
    }

    // Get a product by its UUID.
    public Optional<ProductRecord> getProductById(UUID uuid) {
        return products.stream()
                .filter(p -> p.uuid().equals(uuid))
                .findFirst();
    }

    // Group products by their categories and return the result as a map.
    public Map<Category, List<ProductRecord>> getProductsGroupedByCategories() {
        return products.stream()
                .collect(Collectors.groupingBy(ProductRecord::category));
    }

    // Get a list of products belonging to a specific category.
    public List<ProductRecord> getProductsBy(Category category) {
        return products.stream()
                .filter(product -> product.category().equals(category))
                .collect(Collectors.toList());
    }

    // Get a list of products with changed prices.
    public List<ProductRecord> getChangedProducts() {
        return changedProducts;
    }
}
