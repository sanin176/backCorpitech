package com.alex.Model;

import lombok.Data;
import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customer_id")
    @Column(name = "id")
    private int id;
    private String licenseKey;
    private String email;
    private String fio;
    private String organization;
    private Boolean isActive;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private int maxSeatCount;
    private int maxWorkstationCount;
    private String tmapiEmail;
    private String tmapiPassword;
    private String tmapiLimitKey;
    private String note;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @OneToMany(mappedBy="customer", fetch = FetchType.EAGER)
    private List<CustomerSeat> customerSeats;
}
