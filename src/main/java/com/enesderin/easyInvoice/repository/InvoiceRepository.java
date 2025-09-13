package com.enesderin.easyInvoice.repository;

import com.enesderin.easyInvoice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    String deleteInvoiceByCustomerId(Long id);
    List<Invoice> findAllByCompanyId(Long companyId);
}
