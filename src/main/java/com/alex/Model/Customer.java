package com.alex.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String licenseKey;
    private String email;
    private String FIO;
    private String organization;
    private Boolean isActive;
    private String dateFrom;
    private String dateTo;
    private String maxSeatCount;
    private String maxWorkstationCount;
    private String TMAPIEmail;
    private String TMAPIPassword;
    private String TMAPILimitKey;
    private String note;
}
