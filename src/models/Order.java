package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class Order {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("orderNumber")
    private String orderNumber;

    @JsonProperty("customerId")
    private Long customerId;

    @JsonProperty("orderTime")
    private LocalTime orderTime;

    @JsonProperty("orderDate")
    private LocalDate orderDate;

    @JsonProperty("totalAmount")
    private Double totalAmount;

    @JsonProperty("taxAmount")
    private Double taxAmount;

    @JsonProperty("amountWithoutTax")
    private Double amountWithoutTax;

    @JsonProperty("orderDetails")
    private List<OrderDetail> orderDetails;

    @JsonProperty("orderDealDetails")
    private List<OrderDealDetail> orderDealDetails;


}