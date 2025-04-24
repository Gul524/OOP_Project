package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    public int id;
    public Category category;
    public String productName;
    public double primaryPrice;
    public double secondaryPrice;
    public int discountPercent;
    public boolean isDealProduct;
    public boolean isFixedDeal;
    public boolean isActive;
    public List<Size> sizes;
    public List<Flavour> flavours;
    public List<Deal> deals;
}