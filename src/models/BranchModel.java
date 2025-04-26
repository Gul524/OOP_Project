/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import lombok.Data;

/**
 *
 * @author AnasJ
 */
@Data
public class BranchModel {
    private Integer id;
    private Integer companyId;
    private String name;
    private String startTiming;
    private String endTime;
    private String phone;
    private Boolean uploaded;

    // Default constructor
    public BranchModel() {
    }

    // All arguments constructor
    public BranchModel(Integer id, Integer companyId, String name, String startTiming, 
                      String endTime, String phone, Boolean uploaded) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.startTiming = startTiming;
        this.endTime = endTime;
        this.phone = phone;
        this.uploaded = uploaded;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTiming() {
        return startTiming;
    }

    public void setStartTiming(String startTiming) {
        this.startTiming = startTiming;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getUploaded() {
        return uploaded;
    }

    public void setUploaded(Boolean uploaded) {
        this.uploaded = uploaded;
    }

}