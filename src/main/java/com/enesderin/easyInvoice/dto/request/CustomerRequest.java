package com.enesderin.easyInvoice.dto.request;

import com.enesderin.easyInvoice.model.Company;
import com.enesderin.easyInvoice.model.Invoice;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class CustomerRequest {

    private String taxNumber; // vergi numarası
    private String name;

    private String address;
    private String phone;
    private String email;
    private Company company;
}
