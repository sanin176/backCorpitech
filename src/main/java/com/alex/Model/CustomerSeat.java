package com.alex.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class CustomerSeat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customer_seat_id")
    private int id;
    private String customerId;
    private String email;
    private String fio;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id", insertable = false, updatable = false)
    private Customer customer;
}
