package com.enesderin.easyInvoice.service.impl;

import com.enesderin.easyInvoice.dto.request.InvoiceItemRequest;
import com.enesderin.easyInvoice.dto.request.InvoiceRequest;
import com.enesderin.easyInvoice.dto.response.InvoiceItemResponse;
import com.enesderin.easyInvoice.dto.response.InvoiceResponse;
import com.enesderin.easyInvoice.exception.BaseException;
import com.enesderin.easyInvoice.exception.ErrorMessage;
import com.enesderin.easyInvoice.exception.MessageType;
import com.enesderin.easyInvoice.model.Company;
import com.enesderin.easyInvoice.model.Customer;
import com.enesderin.easyInvoice.model.Invoice;
import com.enesderin.easyInvoice.model.InvoiceItem;
import com.enesderin.easyInvoice.repository.CompanyRepository;
import com.enesderin.easyInvoice.repository.CustomerRepository;
import com.enesderin.easyInvoice.repository.InvoiceItemRepository;
import com.enesderin.easyInvoice.repository.InvoiceRepository;
import com.enesderin.easyInvoice.service.InvoiceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private InvoiceRepository invoiceRepository;
    private InvoiceItemRepository invoiceItemRepository;
    private CompanyRepository companyRepository;
    private CustomerRepository customerRepository;

    @Override
    public List<InvoiceResponse> getAllInvoices(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,"Company not found")));

        List<Invoice> invoices = invoiceRepository.findAllByCompanyId(companyId);
        List<InvoiceResponse> invoiceResponseList = new ArrayList<>();

        for (Invoice invoice : invoices) {
            List<InvoiceItemResponse> invoiceItemResponseList = new ArrayList<>();
            for (InvoiceItem invoiceItem : invoice.getInvoiceItems()) {
                InvoiceItemResponse invoiceItemResponse = new InvoiceItemResponse();
                invoiceItemResponse.setId(invoiceItem.getId());
                invoiceItemResponse.setDescription(invoiceItem.getDescription());
                invoiceItemResponse.setQuantity(invoiceItem.getQuantity());
                invoiceItemResponse.setUnitPrice(invoiceItem.getUnitPrice());
                invoiceItemResponse.setTotalPrice(invoiceItem.getTotalPrice());
                invoiceItemResponseList.add(invoiceItemResponse);
            }

            InvoiceResponse invoiceResponse = new InvoiceResponse();
            invoiceResponse.setId(invoice.getId());
            invoiceResponse.setStatus(invoice.getStatus());
            invoiceResponse.setInvoiceNumber(invoice.getInvoiceNumber());
            invoiceResponse.setDueDate(invoice.getDueDate());
            invoiceResponse.setIssueDate(invoice.getIssueDate());
            invoiceResponse.setTotalAmount(invoice.getTotalAmount());
            invoiceResponse.setCustomerId(invoice.getCustomer().getId());
            invoiceResponse.setCompanyId(invoice.getCompany().getId());
            invoiceResponse.setInvoiceItems(invoiceItemResponseList);

            invoiceResponseList.add(invoiceResponse);
        }
        return invoiceResponseList;
    }

    @Override
    public InvoiceResponse getInvoiceById(Long id) {
        Optional<Invoice> foundInvoice = this.invoiceRepository.findById(id);
        if (foundInvoice.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NO_RECORD_EXIST,"Fatura kaydı bulunamadı"));
        }
        List<InvoiceItemResponse> invoiceItemResponseList = new ArrayList<>();
        for (InvoiceItem invoiceItem : foundInvoice.get().getInvoiceItems()) {
            InvoiceItemResponse invoiceItemResponse = new InvoiceItemResponse();
            BeanUtils.copyProperties(invoiceItem, invoiceItemResponse);
            invoiceItemResponseList.add(invoiceItemResponse);
        }
        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setId(foundInvoice.get().getId());
        invoiceResponse.setInvoiceItems(invoiceItemResponseList);
        invoiceResponse.setInvoiceNumber(foundInvoice.get().getInvoiceNumber());
        invoiceResponse.setIssueDate(foundInvoice.get().getIssueDate());
        invoiceResponse.setDueDate(foundInvoice.get().getDueDate());
        invoiceResponse.setTotalAmount(foundInvoice.get().getTotalAmount());
        invoiceResponse.setCompanyId(foundInvoice.get().getCompany().getId());
        invoiceResponse.setCustomerId(foundInvoice.get().getCustomer().getId());
        invoiceResponse.setStatus(foundInvoice.get().getStatus());

        return invoiceResponse;
    }

    @Override
    public InvoiceResponse createInvoice(InvoiceRequest invoiceRequest) {
        Company company = companyRepository.findById(invoiceRequest.getCompanyId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Şirket bulunamadı")));

        Customer customer = customerRepository.findById(invoiceRequest.getCustomerId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Müşteri bulunamadı")));

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceRequest.getInvoiceNumber());
        invoice.setCustomer(customer);
        invoice.setCompany(company);
        invoice.setDueDate(invoiceRequest.getDueDate());
        invoice.setStatus(invoiceRequest.getStatus());
        invoice.setIssueDate(invoiceRequest.getIssueDate());

        BigDecimal totalAmount = addInvoiceItems(invoice, invoiceRequest.getInvoiceItems());
        invoice.setTotalAmount(totalAmount);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        InvoiceResponse invoiceResponse = new InvoiceResponse();
        invoiceResponse.setId(savedInvoice.getId());
        invoiceResponse.setInvoiceNumber(savedInvoice.getInvoiceNumber());
        invoiceResponse.setIssueDate(savedInvoice.getIssueDate());
        invoiceResponse.setDueDate(savedInvoice.getDueDate());
        invoiceResponse.setTotalAmount(savedInvoice.getTotalAmount());
        invoiceResponse.setStatus(savedInvoice.getStatus());
        invoiceResponse.setCustomerId(savedInvoice.getCustomer().getId());
        invoiceResponse.setCompanyId(savedInvoice.getCompany().getId());

        List<InvoiceItemResponse> itemResponses = savedInvoice.getInvoiceItems()
                .stream()
                .map(item -> {
                    InvoiceItemResponse r = new InvoiceItemResponse();
                    r.setId(item.getId());
                    r.setDescription(item.getDescription());
                    r.setQuantity(item.getQuantity());
                    r.setUnitPrice(item.getUnitPrice());
                    r.setTotalPrice(item.getTotalPrice());
                    return r;
                })
                .toList();

        invoiceResponse.setInvoiceItems(itemResponses);
        invoiceResponse.setCompanyId(company.getId());
        invoiceResponse.setCustomerId(customer.getId());

        return invoiceResponse;
    }

    @Override
    public InvoiceResponse updateInvoice(Long id, InvoiceRequest invoiceRequest) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NO_RECORD_EXIST, "Fatura bulunamadı")));

        invoice.setDueDate(invoiceRequest.getDueDate());
        invoice.setStatus(invoiceRequest.getStatus());

        invoice.getInvoiceItems().clear();
        BigDecimal totalAmount = addInvoiceItems(invoice, invoiceRequest.getInvoiceItems());
        invoice.setTotalAmount(totalAmount);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        InvoiceResponse invoiceResponse = new InvoiceResponse();
        BeanUtils.copyProperties(savedInvoice, invoiceResponse);
        invoiceResponse.setCompanyId(savedInvoice.getCompany().getId());
        invoiceResponse.setCustomerId(savedInvoice.getCustomer().getId());

        return invoiceResponse;
    }

    /**
     * Yardımcı metod: invoiceItem’ları invoice’a ekler ve totalAmount döner
     */
    private BigDecimal addInvoiceItems(Invoice invoice, List<InvoiceItemRequest> itemRequests) {
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (InvoiceItemRequest itemRequest : itemRequests) {
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setDescription(itemRequest.getDescription());
            invoiceItem.setQuantity(itemRequest.getQuantity());
            invoiceItem.setUnitPrice(itemRequest.getUnitPrice());
            invoiceItem.setTotalPrice(itemRequest.getUnitPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
            invoiceItem.setInvoice(invoice);
            invoice.getInvoiceItems().add(invoiceItem);

            totalAmount = totalAmount.add(invoiceItem.getTotalPrice());
        }

        return totalAmount;
    }

    @Override
    public String deleteInvoice(Long id) {
        this.invoiceRepository.deleteById(id);
        return "Fatura Silindi";
    }
}
