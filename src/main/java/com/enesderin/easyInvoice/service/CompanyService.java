package com.enesderin.easyInvoice.service;

import com.enesderin.easyInvoice.dto.request.CompanyRequest;
import com.enesderin.easyInvoice.dto.response.CompanyResponse;
import org.springframework.stereotype.Service;

@Service
public interface CompanyService {
    public String createCompany(CompanyRequest companyRequestDto);
    public String updateCompany(Long id, CompanyRequest companyRequestDto);
    public CompanyResponse getCompany(Long id);
    public String deleteCompany(Long id);
}
