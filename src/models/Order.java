package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import lombok.Data;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Order {

    @JsonProperty(access = WRITE_ONLY)
    private int id;

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("customerId")
    private String customerId;

    @JsonProperty("orderTime")
    private String orderTime;

    @JsonProperty("orderDate")
    private String orderDate;

    @JsonProperty("totalAmount")
    private double totalAmount;

    @JsonProperty("taxAmount")
    private double taxAmount;

    @JsonProperty("amountWithoutTax")
    private double amountWithoutTax;

    @JsonProperty("orderDetails")
    private List<OrderDetail> orderDetails;

    @JsonProperty("orderDealDetails")
    private List<OrderDealDetail> orderDealDetails;

    // All-args constructor
    public Order(String orderNumber, String customerId, String orderTime,
            String orderDate, double totalAmount, double taxAmount, double amountWithoutTax,
            List<OrderDetail> orderDetails, List<OrderDealDetail> orderDealDetails) {
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.orderTime = orderTime;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.taxAmount = taxAmount;
        this.amountWithoutTax = amountWithoutTax;
        this.orderDetails = orderDetails;
        this.orderDealDetails = orderDealDetails;
    }
}
