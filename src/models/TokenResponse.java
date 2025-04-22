/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author AnasJ
 */
public class TokenResponse {
    private String token;

    // Default constructor
    public TokenResponse() {}

    // Constructor with parameter
    public TokenResponse(String token) {
        this.token = token;
    }

    // Getter and Setter
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Convert object to JSON string
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Convert JSON string to object
    public static TokenResponse fromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, TokenResponse.class);
            
            
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // toString method
    @Override
    public String toString() {
        return "TokenResponse{token='" + token + "'}";
    }
}