package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Data
public class Flavour {
    public int id;
    public Integer productId;
    public String name;
    public Integer dealItemId;
    public double price;
}
