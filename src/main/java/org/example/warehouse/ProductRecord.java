package org.example.warehouse;

import java.math.BigDecimal;
import java.util.UUID;

// A record representing product details in a warehouse.
public record ProductRecord(UUID uuid, String name, Category category, BigDecimal price) {

}
