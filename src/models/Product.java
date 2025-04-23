package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import models.*;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
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
