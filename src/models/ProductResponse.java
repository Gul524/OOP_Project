package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import models.Product;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {
    public boolean success;
    public String message;
    public String errorCause;
    public List<Product> data;

    // Getters and setters
    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getErrorCause() { return errorCause; }
    public List<Product> getData() { return data; }
}
