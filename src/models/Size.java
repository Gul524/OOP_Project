package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class Size {
    public int id;
    public String name;
    public Integer productId;
    public double price;
    public Integer dealItemId;
}
