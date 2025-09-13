package com.enesderin.easyInvoice.controller.impl;

import com.enesderin.easyInvoice.controller.CompanyController;
import com.enesderin.easyInvoice.controller.RootEntity;
import com.enesderin.easyInvoice.dto.request.CompanyRequest;
import com.enesderin.easyInvoice.dto.response.CompanyResponse;
import com.enesderin.easyInvoice.service.CompanyService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/company")
@AllArgsConstructor
public class CompanyControllerImpl extends RootEntity implements CompanyController {

    private CompanyService companyService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    @Override
    public RootEntity<CompanyResponse> createCompany(@Valid @RequestBody CompanyRequest companyRequestDto) {
        return ok(companyService.createCompany(companyRequestDto));
    }

    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    @PutMapping("/update/{id}")
    @Override
    public RootEntity<CompanyResponse> updateCompany(@PathVariable Long id,@Valid @RequestBody CompanyRequest companyRequestDto) {
        return ok(companyService.updateCompany(id, companyRequestDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/{id}")
    @Override
    public RootEntity<CompanyResponse> getCompany(@PathVariable Long id) {
        return ok(companyService.getCompany(id));
    }

    @GetMapping("/all")
    @Override
    public RootEntity<List<CompanyResponse>> getAllCompanies() {
        return ok(companyService.getAllCompany());
    }

    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{id}")
    @Override
    public RootEntity<String> deleteCompany(@PathVariable Long id) {
        return ok(companyService.deleteCompany(id));
    }
}
