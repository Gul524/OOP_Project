/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import com.fasterxml.jackson.databind.ObjectMapper;
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


/**
 * @author salman
 */
public class ApiClient {
    static ObjectMapper _mapper = new ObjectMapper();
    static CloseableHttpClient _httpClient = HttpClients.createDefault();
    static String _baseURL = "http://localhost:8080";
    static String _token;

    public static void checkApi(){
        HttpGet request = new HttpGet(_baseURL+"/connection/check");
        try {
            CloseableHttpResponse response = _httpClient.execute(request);
            if(response.getStatusLine().getStatusCode() == 200){
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
            else {
                System.out.println(EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    static public void login()  {
        try{
            String jsonBody = "{\"username\":\"api\" , \"password\":\"123\"}";
            HttpPost request = new HttpPost(_baseURL + "/login");
            request.addHeader("Content-Type", "application/json");
            request.setEntity(new StringEntity(jsonBody));

            CloseableHttpResponse response = _httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();

            if(statusCode == 200){
                _token = EntityUtils.toString(response.getEntity());
            }
            else if(statusCode == 401){
                String responseData = EntityUtils.toString(response.getEntity());
                System.out.println(responseData);
            }
            else {
                System.out.println("Failed to Login");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


     static List<Product> loadProducts() {
        try {
            var request = new HttpGet(_baseURL + "/resApi/products/products");
//           request.addHeader("Authorization", "Bearer " + bearerToken);
            CloseableHttpResponse response = _httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode >= 200 && statusCode <= 300) {
                String jsonResponse = EntityUtils.toString(response.getEntity());
                ApiResponseModel<List<Product>> responseModel = _mapper.readValue(jsonResponse, ApiResponseModel.class);
                if (responseModel.isSuccess()) {
                    return responseModel.getData();
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

    static String storeProduct(List<Product> products) {
        try {
            var request = new HttpPost(_baseURL + "/resApi/products/addProduct");
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


    public static void main(String[] args) {
        checkApi();
        login();
        System.out.println(_token);
        System.out.println(loadProducts());
        ArrayList<Size> sizes =  new ArrayList<>();
        sizes.add(new Size(0,"1 ltr",0 , 120 , null));
        sizes.add( new Size(0 , "1.5 ltr" , 0 , 200 , null));
        ArrayList<Flavour> flavours =  new ArrayList<>();
        flavours.add(new Flavour(0,0,"coke" , 0 , 0));
        flavours.add( new Flavour(0 , 0 , "lemon" , 0 , 0));
        ArrayList<Product> products = new ArrayList<>();

        products.add( new Product(
                0,new Category(1 ,"Drinks", true),
                "ColdDrinks",
                120,
                140,
                8,false,
                false,
                true ,
                sizes,
                flavours,
                new ArrayList<Deal>()
        ));
//        storeProduct(
//                products
//        );
        System.out.println(loadProducts());

    }
}