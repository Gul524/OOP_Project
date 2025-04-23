/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import com.google.gson.JsonObject;
import java.io.*;
import java.net.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.*;

/**
 *
 * @author AnasJ
 */
public class ApiClient {

    static String baseURL = "http://localhost:8080";
    static String _token;

    //Send Data Function
    public static void sendData(HttpURLConnection conn, String jsonInputString) {
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.toString().getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
            os.close();
        } catch (IOException ex) {
            Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void login(String username, String password) {
        try {
            JsonObject json = new JsonObject();
            json.addProperty("username", username);
            json.addProperty("password", password);
            String jsonInputString = json.toString();

            URL url = new URL(baseURL + "/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
//            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");

            //Send Data
            sendData(conn, jsonInputString);

            //read-response
            int status = conn.getResponseCode();
            InputStream is = (status < 400) ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = in.readLine()) != null) {
                response.append(responseLine.trim());
            }
            in.close();

            TokenResponse token = TokenResponse.fromJson(response.toString());

            System.out.println(token.getToken());
            _token = token.getToken();

//            System.out.println(TokenResponse.fromJson(response.toString()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Product> products() {
    List<Product> products = new ArrayList<>();
    try {
        String apiUrl = baseURL + "/resApi/products/products"; // Verify this endpoint
        URL url = new URL(apiUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        // Add Authorization header if token is required
        if (_token != null && !_token.isEmpty()) {
            conn.setRequestProperty("Authorization", "Bearer " + _token);
        }

        // Check HTTP status code
        int status = conn.getResponseCode();
        InputStream inputStream = (status < 400) ? conn.getInputStream() : conn.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));

        StringBuilder responseFetched = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseFetched.append(line);
        }
        reader.close();
        conn.disconnect();

        // Log the response for debugging
        System.out.println("Response from API: " + responseFetched.toString());

        // Deserialize JSON to ProductResponse
        ObjectMapper mapper = new ObjectMapper();
        ProductResponse response = mapper.readValue(responseFetched.toString(), ProductResponse.class);

        // Check if the response is successful and data is present
        if (response.isSuccess() && response.getData() != null) {
            products.addAll(response.getData());
            System.out.println("Products retrieved: " + products.size());
        } else {
            System.out.println("No products found or API failed: " + response.getMessage());
        }

        
    } catch (Exception e) {
        Logger.getLogger(ApiClient.class.getName()).log(Level.SEVERE, "Failed to fetch products", e);
    }
    return products;
}

    public static void main(String[] args) {
        System.out.println(products());
    }
}