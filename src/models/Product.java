package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @JsonProperty(access = WRITE_ONLY)
    public int id;
    @JsonProperty(access = WRITE_ONLY)
    public Category category;
    @JsonProperty(access = READ_ONLY)
    public int categoryId;
    public String productName;
    public double price;
    public List<Size> sizes;
    public List<Flavor> flavors;

    public Product(int categoryId, String name, int price, List<Size> sizes, List<Flavor> flavors){
        this.categoryId = categoryId;
        this.productName = name;
        this.price = price;
        this.sizes = sizes;
        this.flavors = flavors;

    }

    public int getPriceForSize(String size){
        int price = 0;
        for(Size s : sizes){
            if(size.equals(s.name)){
                price = s.price;
            }
        }
        return price;
    }

    public String getFalovorsString(){
        StringBuilder result = new StringBuilder();
        for(Flavor f : flavors){
            result.append(f.getName()).append(" , ");
        }
        return (result.isEmpty())? "-" : result.toString();
    }

    public String getSizesString(){
        StringBuilder result = new StringBuilder();
        for(Size s : sizes){
            result.append("(").append(s.name).append(" - ").append(s.price).append(") , ");
        }
        return (result.isEmpty())? "-" : result.toString();
    }

}