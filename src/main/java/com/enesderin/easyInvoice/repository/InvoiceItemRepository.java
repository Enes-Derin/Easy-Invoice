package com.enesderin.easyInvoice.repository;

import com.enesderin.easyInvoice.model.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}
