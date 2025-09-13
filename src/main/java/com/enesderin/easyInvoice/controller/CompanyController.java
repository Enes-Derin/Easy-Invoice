package com.enesderin.easyInvoice.controller;

import com.enesderin.easyInvoice.dto.request.CompanyRequest;
import com.enesderin.easyInvoice.dto.response.CompanyResponse;
import jakarta.persistence.criteria.Root;

import java.util.List;

public interface CompanyController {
    RootEntity<CompanyResponse> createCompany(CompanyRequest companyRequestDto);
    RootEntity<CompanyResponse> updateCompany(Long id, CompanyRequest companyRequestDto);
    RootEntity<CompanyResponse> getCompany(Long id);
    RootEntity<List<CompanyResponse>> getAllCompanies();
    RootEntity<String> deleteCompany(Long id);
}
