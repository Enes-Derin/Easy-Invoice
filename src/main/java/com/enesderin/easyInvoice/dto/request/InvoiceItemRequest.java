package com.enesderin.easyInvoice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class InvoiceItemRequest {

    @NotNull
    private String description;
    @NotNull
    private int quantity;
    @NotNull
    private BigDecimal unitPrice;
}
