package com.enesderin.easyInvoice.dto.request;

import com.enesderin.easyInvoice.model.Company;
import com.enesderin.easyInvoice.model.Customer;
import com.enesderin.easyInvoice.model.InvoiceItem;
import com.enesderin.easyInvoice.model.InvoiceStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class InvoiceRequest {
    private String invoiceNumber;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private InvoiceStatus status;

    private Long customerId;

    private Long companyId;

    private List<InvoiceItemRequest> invoiceItems;
}
