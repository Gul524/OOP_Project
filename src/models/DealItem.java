package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DealItem {
    public int id;
    public int dealId;
    public String itemName;
    public double price;
    public List<Flavour> flavours;
    public List<Size> size;
}
