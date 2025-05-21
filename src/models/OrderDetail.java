package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetail {

    @JsonProperty(access = WRITE_ONLY)
    private int id;

    @JsonProperty("productName")
    private String itemName;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("price")
    private double price;

    public OrderDetail(String name, int qty, int price) {
        this.itemName = name;
        this.quantity = qty;
        this.price = price;
}
}
