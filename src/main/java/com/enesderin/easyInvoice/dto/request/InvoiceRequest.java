package com.enesderin.easyInvoice.dto.request;

import com.enesderin.easyInvoice.model.Company;
import com.enesderin.easyInvoice.model.Customer;
import com.enesderin.easyInvoice.model.InvoiceItem;
import com.enesderin.easyInvoice.model.InvoiceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceRequest {
    @NotNull
    private String invoiceNumber;
    @NotNull
    private LocalDate issueDate;
    @NotNull
    private LocalDate dueDate;
    @NotNull
    private InvoiceStatus status;
    @NotNull
    private Long customerId;
    @NotNull
    private Long companyId;
    @NotNull
    private List<InvoiceItemRequest> invoiceItems;
}
