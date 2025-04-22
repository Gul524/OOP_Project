package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// Root class for the JSON
public class ProductModel {
    private boolean success;
    private String message;
    private String errorCause;
    private List<Product> data;

    // Getters and Setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getErrorCause() { return errorCause; }
    public void setErrorCause(String errorCause) { this.errorCause = errorCause; }
    public List<Product> getData() { return data; }
    public void setData(List<Product> data) { this.data = data; }

    // Convert to Map
    public Map<String, Object> toMap() {
        return Map.of(
            "success", success,
            "message", message,
            "errorCause", errorCause,
            "data", data.stream().map(Product::toMap).toList()
        );
    }
}

// Product class
class Product {
    private int id;
    private Category category;
    private String productName;
    private int primaryPrice;
    private int secondaryPrice;
    private int discountPercent;
    private boolean isDealProduct;
    private boolean isFixedDeal;
    private boolean isActive;
    private List<Size> sizes;
    private List<Flavour> flavours;
    private List<Deal> deals;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public int getPrimaryPrice() { return primaryPrice; }
    public void setPrimaryPrice(int primaryPrice) { this.primaryPrice = primaryPrice; }
    public int getSecondaryPrice() { return secondaryPrice; }
    public void setSecondaryPrice(int secondaryPrice) { this.secondaryPrice = secondaryPrice; }
    public int getDiscountPercent() { return discountPercent; }
    public void setDiscountPercent(int discountPercent) { this.discountPercent = discountPercent; }
    public boolean isDealProduct() { return isDealProduct; }
    public void setDealProduct(boolean dealProduct) { isDealProduct = dealProduct; }
    public boolean isFixedDeal() { return isFixedDeal; }
    public void setFixedDeal(boolean fixedDeal) { isFixedDeal = fixedDeal; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
    public List<Size> getSizes() { return sizes; }
    public void setSizes(List<Size> sizes) { this.sizes = sizes; }
    public List<Flavour> getFlavours() { return flavours; }
    public void setFlavours(List<Flavour> flavours) { this.flavours = flavours; }
    public List<Deal> getDeals() { return deals; }
    public void setDeals(List<Deal> deals) { this.deals = deals; }

    // Convert to Map
    public Map<String, Object> toMap() {
    Map<String, Object> map = new LinkedHashMap<>(); // preserves insertion order
    map.put("id", id);
    map.put("category", category != null ? category.toMap() : null);
    map.put("productName", productName);
    map.put("primaryPrice", primaryPrice);
    map.put("secondaryPrice", secondaryPrice);
    map.put("discountPercent", discountPercent);
    map.put("isDealProduct", isDealProduct);
    map.put("isFixedDeal", isFixedDeal);
    map.put("isActive", isActive);
    map.put("sizes", sizes.stream().map(Size::toMap).toList());
    map.put("flavours", flavours.stream().map(Flavour::toMap).toList());
    map.put("deals", deals.stream().map(Deal::toMap).toList());
    return map;
}
}

// Category class
class Category {
    private int id;
    private String categoryName;
    private boolean isActive;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }

    // Convert to Map
    public Map<String, Object> toMap() {
        return Map.of(
            "id", id,
            "categoryName", categoryName,
            "isActive", isActive
        );
    }
}

// Size class
class Size {
    private int id;
    private String name;
    private int productId;
    private int price;
    private Integer dealItemId; // Nullable

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public Integer getDealItemId() { return dealItemId; }
    public void setDealItemId(Integer dealItemId) { this.dealItemId = dealItemId; }

    // Convert to Map
    public Map<String, Object> toMap() {
        return Map.of(
            "id", id,
            "name", name,
            "productId", productId,
            "price", price,
            "dealItemId", dealItemId != null ? dealItemId : null
        );
    }
}

// Flavour class
class Flavour {
    private int id;
    private Integer productId; // Nullable
    private String name;
    private Integer dealItemId; // Nullable
    private int price;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public Integer getProductId() { return productId; }
    public void setProductId(Integer productId) { this.productId = productId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getDealItemId() { return dealItemId; }
    public void setDealItemId(Integer dealItemId) { this.dealItemId = dealItemId; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    // Convert to Map
    public Map<String, Object> toMap() {
        return Map.of(
            "id", id,
            "productId", productId != null ? productId : null,
            "name", name,
            "dealItemId", dealItemId != null ? dealItemId : null,
            "price", price
        );
    }
}

// Deal class
class Deal {
    private int id;
    private int productId;
    private String productName;
    @JsonProperty("minimunSelection") // JSON field has a typo
    private boolean minimumSelection;
    private boolean maximumSelection;
    private boolean isActive;
    private List<DealItem> dealItems;

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public boolean isMinimumSelection() { return minimumSelection; }
    public void setMinimumSelection(boolean minimumSelection) { this.minimumSelection = minimumSelection; }
    public boolean isMaximumSelection() { return maximumSelection; }
    public void setMaximumSelection(boolean maximumSelection) { this.maximumSelection = maximumSelection; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
    public List<DealItem> getDealItems() { return dealItems; }
    public void setDealItems(List<DealItem> dealItems) { this.dealItems = dealItems; }

    // Convert to Map
    public Map<String, Object> toMap() {
        return Map.of(
            "id", id,
            "productId", productId,
            "productName", productName,
            "minimunSelection", minimumSelection,
            "maximumSelection", maximumSelection,
            "isActive", isActive,
            "dealItems", dealItems.stream().map(DealItem::toMap).toList()
        );
    }
}

// DealItem class
class DealItem {
    private int id;
    private int dealId;
    private String itemName;
    private int price;
    private List<Flavour> flavours;
    private List<Size> size; // JSON field is singular "size" but contains an array

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getDealId() { return dealId; }
    public void setDealId(int dealId) { this.dealId = dealId; }
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public List<Flavour> getFlavours() { return flavours; }
    public void setFlavours(List<Flavour> flavours) { this.flavours = flavours; }
    public List<Size> getSize() { return size; }
    public void setSize(List<Size> size) { this.size = size; }

    // Convert to Map
    public Map<String, Object> toMap() {
        return Map.of(
            "id", id,
            "dealId", dealId,
            "itemName", itemName,
            "price", price,
            "flavours", flavours.stream().map(Flavour::toMap).toList(),
            "size", size.stream().map(Size::toMap).toList()
        );
    }
}