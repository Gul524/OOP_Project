package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deal {
    public int id;
    public int productId;
    public String productName;
    public boolean minimunSelection;
    public boolean maximumSelection;
    public boolean isActive;
    public List<DealItem> dealItems;
}
