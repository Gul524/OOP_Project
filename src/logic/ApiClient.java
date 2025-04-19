/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logic;

import com.google.gson.JsonObject;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.TokenResponse;

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

    public static void main(String[] args) {
        login("api", "123");
    }
}
