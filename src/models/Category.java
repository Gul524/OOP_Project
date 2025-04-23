package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {
    public int id;
    public String categoryName;
    public boolean isActive;
}
