package com.enesderin.easyInvoice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String taxNumber;//vergi numarası

    @Lob
    private String address;
    private String phone;
    private String email;

    @OneToMany
    private List<User> user;

    @OneToMany
    private List<Customer> customers;

    @OneToMany
    private List<Invoice> invoices;
}
