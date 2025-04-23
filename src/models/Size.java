package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Size {
    public int id;
    public String name;
    public Integer productId;
    public double price;
    public Integer dealItemId;
}
