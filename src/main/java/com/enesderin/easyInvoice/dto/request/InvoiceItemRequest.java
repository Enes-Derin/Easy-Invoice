package com.enesderin.easyInvoice.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemRequest {

    private String description;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;
    private Long invoiceId;
}
