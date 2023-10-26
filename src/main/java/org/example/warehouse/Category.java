package org.example.warehouse;

import java.util.HashMap;
import java.util.Map;

// A class representing categories in a warehouse.
public class Category {
    // A map to store category objects, using their names as keys.
    // The names are stored in a case-insensitive manner (capitalized).
    private static final Map<String, Category> categories = new HashMap<>();

    // The name of the category.
    private final String name;

    // Private constructor to create Category objects.
    private Category(String name) {
        this.name = name;
    }

    // Static factory method to create or retrieve Category objects.
    // It ensures that category names are not null and case-insensitive.
    public static Category of(String name) {
        // Check if the provided name is null; if so, throw an exception.
        if (name == null) {
            throw new IllegalArgumentException("Category name can't be null");
        }

        // Capitalize the first letter and make the rest of the name lowercase.
        String capitalized = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

        // Use the computed, capitalized name as the key to retrieve or create the Category object.
        return categories.computeIfAbsent(capitalized, Category::new);
    }

    // Getter method to retrieve the name of the category.
    public String getName() {
        return name;
    }
}
