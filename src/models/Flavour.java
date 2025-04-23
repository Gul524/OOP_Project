package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Flavour {
    public int id;
    public Integer productId;
    public String name;
    public Integer dealItemId;
    public double price;
}
