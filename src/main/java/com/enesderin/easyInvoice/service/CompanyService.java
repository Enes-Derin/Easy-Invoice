package com.enesderin.easyInvoice.service;

import com.enesderin.easyInvoice.dto.request.CompanyRequest;
import com.enesderin.easyInvoice.dto.response.CompanyResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyService {
    CompanyResponse createCompany(CompanyRequest companyRequestDto);
    CompanyResponse updateCompany(Long id, CompanyRequest companyRequestDto);
    CompanyResponse getCompany(Long id);
    List<CompanyResponse> getAllCompany();
    String deleteCompany(Long id);
}
