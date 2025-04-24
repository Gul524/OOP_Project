package models;

import lombok.Data;

@Data
public class ApiResponseModel<T> {
    boolean success;
    String message;
    String errorCause;
    T data;
}
