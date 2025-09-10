package com.enesderin.easyInvoice.dto.response;

import com.enesderin.easyInvoice.model.Invoice;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemResponse {

    private Long id;
    private String description;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Long invoiceId;
}
