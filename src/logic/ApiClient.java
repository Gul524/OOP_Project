/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.ProductData;
import models.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author salman
 */
public class ApiClient {

    static ObjectMapper _mapper = new ObjectMapper();
    static CloseableHttpClient _httpClient = HttpClients.createDefault();
    static String _baseURL = "http://localhost:8080";
    static String _token;

    public static void checkApi() {
        HttpGet request = new HttpGet(_baseURL + "/connection/check");
        try {
            CloseableHttpResponse response = _httpClient.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println(EntityUtils.toString(response.getEntity()));
            } else {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static public void login() {
        try {
            String jsonBody = "{\"username\":\"api\" , \"password\":\"123\"}";
            HttpPost request = new HttpPost(_baseURL + "/login");
            request.addHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(jsonBody));

            CloseableHttpResponse response = _httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                _token = EntityUtils.toString(response.getEntity());
            } else if (statusCode == 401) {
                String responseData = EntityUtils.toString(response.getEntity());
                System.out.println(responseData);
            } else {
                System.out.println("Failed to Login");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void mapProductsToCategories(List<Product> products) {
        // Clear existing data
        ProductData.stringCategories.clear();
        ProductData.categorizedProducts.clear();

        // Group products by category name and populate stringCategories
        for (Product product : products) {
            // Use categoryName from product.category if available, else fallback to categoryId as string
            String categoryName = (product.category != null && product.category.categoryName != null)
                    ? product.category.categoryName
                    : "Category_" + product.categoryId;

            // Add category name to stringCategories if not already present
            if (!ProductData.stringCategories.contains(categoryName)) {
                ProductData.stringCategories.add(categoryName);
            }

            // Add product to categorizedProducts under the category name
            ProductData.categorizedProducts.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(product);
        }
    }

    public static List<Product> loadProducts() {
        try {
            var request = new HttpGet(_baseURL + "/resApi/products/products");
            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                // Use TypeReference to deserialize the response correctly
                ApiResponseModel<List<Product>> responseModel = _mapper.readValue(
                        jsonResponse, new TypeReference<ApiResponseModel<List<Product>>>() {
                }
                );
                if (responseModel.isSuccess()) {
                    // Convert List<Product> to Map<Integer, Product> using productId as the key

                    mapProductsToCategories(responseModel.getData());

                    return responseModel.getData();

                } else if (responseModel.getErrorCause() != null) {
                    System.out.println("Error: " + responseModel.getErrorCause());
                    return null;
                } else if (responseModel.getMessage() != null) {
                    System.out.println("Error: " + responseModel.getMessage());
                    return null;
                } else {
                    System.out.println(responseModel.getErrorCause());
                    return null;
                }
            } else {
                System.out.println("Failed to Load Product");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String addProduct(List<Product> products) {
        try {
            var request = new HttpPost(_baseURL + "/resApi/products/addProducts");
            request.addHeader("Content-Type", "application/json");
//           request.addHeader("Authorization", "Bearer " + bearerToken);
            String jsonBody;
            try {
                jsonBody = _mapper.writeValueAsString(products);
            } catch (Exception e) {
                System.out.println("Error serializing JSON: " + e.getMessage());
                return "Error serializing JSON: " + e.getMessage();
            }
            request.setEntity(new StringEntity(jsonBody));
            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());

                ApiResponseModel<String> responseModel = _mapper.readValue(jsonResponse, ApiResponseModel.class);
                if (responseModel.isSuccess()) {
                    System.out.println(responseModel.getMessage());
                    return responseModel.getMessage();

                } else {
                    System.out.println(responseModel.getErrorCause());
                    return (responseModel.getMessage() + " \nCause :" + responseModel.getErrorCause());
                }
            } else {
                System.out.println("Failed to Save Products");
                return "Failed to Save Products";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String deleteProduct(int id) {
        try {
            var request = new HttpPost(_baseURL + "/resApi/products/deleteProducts/" + id);
            request.addHeader("Content-Type", "application/json");
            // Uncomment if authentication is required
            // request.addHeader("Authorization", "Bearer " + _token);

            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ApiResponseModel<String> responseModel = _mapper.readValue(jsonResponse, ApiResponseModel.class);
                if (responseModel.isSuccess()) {
                    System.out.println(responseModel.getMessage());
                    return responseModel.getMessage();
                } else {
                    System.out.println(responseModel.getErrorCause());
                    return responseModel.getMessage() + " \nCause: " + responseModel.getErrorCause();
                }
            } else {
                String errorMessage = "Failed to delete product. Status code: " + statusCode;
                System.out.println(errorMessage);
                return errorMessage;
            }
        } catch (Exception e) {
            String errorMessage = "Error deleting product: " + e.getMessage();
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }

    public static String updateProduct(int productId, Product product) {
        try {
            var request = new HttpPost(_baseURL + "/resApi/products/updateProducts/" + productId);
            request.addHeader("Content-Type", "application/json");
            // Uncomment if authentication is required
            // request.addHeader("Authorization", "Bearer " + _token);

            String jsonBody;
            try {
                jsonBody = _mapper.writeValueAsString(product);
            } catch (Exception e) {
                String errorMessage = "Error serializing JSON: " + e.getMessage();
                System.out.println(errorMessage);
                return errorMessage;
            }
            request.setEntity(new StringEntity(jsonBody));

            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ApiResponseModel<String> responseModel = _mapper.readValue(jsonResponse, ApiResponseModel.class);
                if (responseModel.isSuccess()) {
                    System.out.println(responseModel.getMessage());
                    return responseModel.getMessage();
                } else {
                    System.out.println(responseModel.getErrorCause());
                    return responseModel.getMessage() + " \nCause: " + responseModel.getErrorCause();
                }
            } else {
                String errorMessage = "Failed to update product. Status code: " + statusCode;
                System.out.println(errorMessage);
                return errorMessage;
            }
        } catch (Exception e) {
            String errorMessage = "Error updating product: " + e.getMessage();
            System.out.println(errorMessage);
            throw new RuntimeException(errorMessage, e);
        }
    }

    public static List<Category> loadCategories() {
        try {
            var request = new HttpGet(_baseURL + "/resApi/products/categories");
            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                // Use TypeReference to deserialize the response correctly
                ApiResponseModel<List<Category>> responseModel = _mapper.readValue(
                        jsonResponse, new TypeReference<ApiResponseModel<List<Category>>>() {
                }
                );
                if (responseModel.isSuccess()) {
                    ProductData.categories.clear();
                    ProductData.categories = responseModel.getData();
                    ProductData.stringCategories.clear();
                    ProductData.stringCategories.add("All");
                    for (Category c : (responseModel.getData())) {
                        ProductData.stringCategories.add(c.categoryName);

                    }
                    return responseModel.getData();

                } else if (responseModel.getErrorCause() != null) {
                    System.out.println("Error: " + responseModel.getErrorCause());
                    return null;
                } else if (responseModel.getMessage() != null) {
                    System.out.println("Error: " + responseModel.getMessage());
                    return null;
                } else {
                    System.out.println(responseModel.getErrorCause());
                    return null;
                }
            } else {
                System.out.println("Failed to Load Product");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static List<Staff> loadStaff() {
        try {
            var request = new HttpGet(_baseURL + "/resApi/employees");
//            request.addHeader();
            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                // Use TypeReference to deserialize the response correctly
                ApiResponseModel<List<Staff>> responseModel = _mapper.readValue(
                        jsonResponse, new TypeReference<ApiResponseModel<List<Staff>>>() {
                }
                );
                if (responseModel.isSuccess()) {
                    ProductData.employees.clear();
                    ProductData.employees = responseModel.getData();

                    return responseModel.getData();

                } else if (responseModel.getErrorCause() != null) {
                    System.out.println("Error: " + responseModel.getErrorCause());
                    return null;
                } else if (responseModel.getMessage() != null) {
                    System.out.println("Error: " + responseModel.getMessage());
                    return null;
                } else {
                    System.out.println(responseModel.getErrorCause());
                    return null;
                }
            } else {
                System.out.println("Failed to Load Staff");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean storeStaff(List<Staff> staff) {
        try {
            var request = new HttpPost(_baseURL + "/resApi/addEmployee");
            request.addHeader("Content-Type", "application/json");
//           request.addHeader("Authorization", "Bearer " + bearerToken);
            String jsonBody;
            try {
                jsonBody = _mapper.writeValueAsString(staff);
            } catch (Exception e) {
                System.out.println("Error serializing JSON: " + e.getMessage());
                System.out.println("Error serializing JSON: " + e.getMessage());
                return false;
            }
            request.setEntity(new StringEntity(jsonBody));
            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());

                ApiResponseModel<String> responseModel = _mapper.readValue(jsonResponse, ApiResponseModel.class);
                if (responseModel.isSuccess()) {
                    System.out.println(responseModel.getMessage());
                    System.out.println(responseModel.getMessage());
                    return true;

                } else {
                    System.out.println(responseModel.getErrorCause());
                    System.out.println(responseModel.getMessage() + " \nCause :" + responseModel.getErrorCause());
                    return false;
                }
            } else {
                System.out.println("Failed to Save staff");
                return false;

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String storeCategory(List<Category> categories) {
        try {
            var request = new HttpPost(_baseURL + "/resApi/products/addCategory");
            request.addHeader("Content-Type", "application/json");
//           request.addHeader("Authorization", "Bearer " + bearerToken);
            String jsonBody;
            try {
                jsonBody = _mapper.writeValueAsString(categories);
            } catch (Exception e) {
                System.out.println("Error serializing JSON: " + e.getMessage());
                return "Error serializing JSON: " + e.getMessage();
            }
            request.setEntity(new StringEntity(jsonBody));
            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());

                ApiResponseModel<String> responseModel = _mapper.readValue(jsonResponse, ApiResponseModel.class);
                if (responseModel.isSuccess()) {
                    System.out.println(responseModel.getMessage());
                    return responseModel.getMessage();

                } else {
                    System.out.println(responseModel.getErrorCause());
                    return (responseModel.getMessage() + " \nCause :" + responseModel.getErrorCause());
                }
            } else {
                System.out.println("Failed to Save Categories");
                return "Failed to Save Categories";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String deleteCategory(Integer categoryId) {
        try {
            var request = new HttpPost(_baseURL + "/resApi/products/deleteCategory/" + categoryId);
            request.addHeader("Content-Type", "application/json");
            // request.addHeader("Authorization", "Bearer " + bearerToken); // Uncomment if needed

            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ApiResponseModel<String> responseModel = _mapper.readValue(jsonResponse, ApiResponseModel.class);
                if (responseModel.isSuccess()) {
                    System.out.println(responseModel.getMessage());
                    return responseModel.getMessage();
                } else {
                    System.out.println(responseModel.getErrorCause());
                    return (responseModel.getMessage() + " \nCause :" + responseModel.getErrorCause());
                }
            } else {
                System.out.println("Failed to Delete Category");
                return "Failed to Delete Category";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String updateCategory(Integer categoryId, String newName) {
        try {
            var request = new HttpPost(_baseURL + "/resApi/products/updateCategory/" + categoryId + "/" + newName);
            request.addHeader("Content-Type", "application/json");
            // request.addHeader("Authorization", "Bearer " + bearerToken); // Uncomment if needed

            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ApiResponseModel<String> responseModel = _mapper.readValue(jsonResponse, ApiResponseModel.class);
                if (responseModel.isSuccess()) {
                    System.out.println(responseModel.getMessage());
                    return responseModel.getMessage();
                } else {
                    System.out.println(responseModel.getErrorCause());
                    return (responseModel.getMessage() + " \nCause :" + responseModel.getErrorCause());
                }
            } else {
                System.out.println("Failed to Update Category");
                return "Failed to Update Category";
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Order> loadOrders() {
        try {
            var request = new HttpGet(_baseURL + "/resApi/products/orders");
//            request.addHeader();
            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                // Use TypeReference to deserialize the response correctly
                ApiResponseModel<List<Order>> responseModel = _mapper.readValue(
                        jsonResponse, new TypeReference<ApiResponseModel<List<Order>>>() {
                }
                );
                if (responseModel.isSuccess()) {
                    ProductData.orders.clear();
                    ProductData.orders = responseModel.getData();
                    for (Order o : responseModel.getData()) {
                        System.out.println(o);
                    }
                    return responseModel.getData();

                } else if (responseModel.getErrorCause() != null) {
                    System.out.println("Error: " + responseModel.getErrorCause());
                    return null;
                } else if (responseModel.getMessage() != null) {
                    System.out.println("Error: " + responseModel.getMessage());
                    return null;
                } else {
                    System.out.println(responseModel.getErrorCause());
                    return null;
                }
            } else {
                System.out.println("Failed to Load Order");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static String placeOrder(Order order) {
        try {
            var request = new HttpPost(_baseURL + "/resApi/products/placeOrder");
            request.addHeader("Content-Type", "application/json");
//           request.addHeader("Authorization", "Bearer " + bearerToken);
            String jsonBody;
            try {
                jsonBody = _mapper.writeValueAsString(order);
            } catch (Exception e) {
                System.out.println("Error serializing JSON: " + e.getMessage());
                return "Error serializing JSON: " + e.getMessage();
            }
            request.setEntity(new StringEntity(jsonBody));
            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());

                ApiResponseModel<String> responseModel = _mapper.readValue(jsonResponse, ApiResponseModel.class);
                if (responseModel.isSuccess()) {
                    System.out.println(responseModel.getMessage());
                    return responseModel.getMessage();

                } else {
                    System.out.println(responseModel.getMessage() + " \nCause :" + responseModel.getErrorCause());
                    return (responseModel.getMessage() + " \nCause :" + responseModel.getErrorCause());
                }
            } else {
                System.out.println("Failed to Place Order");
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
//
//        loadProducts();
//        loadOrders();

//
//
    

//////        checkApi();
//////        login();
//////        var productMap = loadProducts();
//////        if (productMap != null) {
//////            System.out.println("Token: " + _token);
//////            Product firstProduct = productMap.get(1); // Access product with ID 1
//////            if (firstProduct != null) {
//////                System.out.println("Product Name: " + firstProduct.getProductName());
//////            } else {
//////                System.out.println("Product with ID 1 not found.");
//////            }
//////        }
//////
//////      List<Product> products = new ArrayList<>();
//////
//////        products.add(new Product(1,"Fajita",1000 , new ArrayList<Size>() ,new ArrayList<Flavor>()));
//////
//////      
////
////        loadProducts();
////
////        System.out.println(ProductData.stringCategories);
////        System.out.println(ProductData.categorizedProducts);
////
    }
}
