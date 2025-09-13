package com.enesderin.easyInvoice.service;

import com.enesderin.easyInvoice.dto.request.InvoiceRequest;
import com.enesderin.easyInvoice.dto.response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    List<InvoiceResponse> getAllInvoices(Long companyId);
    InvoiceResponse getInvoiceById(Long id);
    InvoiceResponse createInvoice(InvoiceRequest invoiceRequest);
    InvoiceResponse updateInvoice(Long id, InvoiceRequest invoiceRequest);
    String deleteInvoice(Long id);
}
