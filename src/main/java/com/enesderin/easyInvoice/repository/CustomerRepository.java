package com.enesderin.easyInvoice.repository;

import com.enesderin.easyInvoice.dto.response.CustomerResponse;
import com.enesderin.easyInvoice.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findCustomersByCompanyId(Long companyId);
}
