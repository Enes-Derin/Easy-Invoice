package com.enesderin.easyInvoice.controller.impl;

import com.enesderin.easyInvoice.controller.CustomerController;
import com.enesderin.easyInvoice.controller.RestBaseController;
import com.enesderin.easyInvoice.controller.RootEntity;
import com.enesderin.easyInvoice.dto.request.CustomerRequest;
import com.enesderin.easyInvoice.dto.response.CustomerResponse;
import com.enesderin.easyInvoice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomerControllerImpl extends RestBaseController implements CustomerController {

    private CustomerService customerService;

    @GetMapping("/{companyId}/all")
    @Override
    public RootEntity<List<CustomerResponse>> getCustomers(@PathVariable Long companyId) {
        return ok(this.customerService.getCustomers(companyId));
    }

    @PostMapping("/create")
    @Override
    public RootEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return ok(this.customerService.createCustomer(customerRequest));
    }

    @GetMapping("/{id}")
    @Override
    public RootEntity<CustomerResponse> getCustomer(@PathVariable Long id) {
        return ok(this.customerService.getCustomer(id));
    }

    @PutMapping("/update/{id}")
    @Override
    public RootEntity<CustomerResponse> updateCustomer(@PathVariable Long id,@Valid @RequestBody CustomerRequest customerRequest) {
        return ok(this.customerService.updateCustomer(id, customerRequest));
    }

    @DeleteMapping("/{id}")
    @Override
    public RootEntity<String> deleteCustomer(@PathVariable Long id) {
        return ok(this.customerService.deleteCustomer(id));
    }
}
