package com.enesderin.easyInvoice.service.impl;

import com.enesderin.easyInvoice.dto.request.CompanyRequest;
import com.enesderin.easyInvoice.dto.response.CompanyResponse;
import com.enesderin.easyInvoice.model.Company;
import com.enesderin.easyInvoice.model.User;
import com.enesderin.easyInvoice.repository.CompanyRepository;
import com.enesderin.easyInvoice.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;

    @Override
    public String createCompany(CompanyRequest companyRequestDto) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequestDto, company);
        this.companyRepository.save(company);
        return "Company created";
    }

    @Override
    public String updateCompany(Long id, CompanyRequest companyRequestDto) {
        Company company = this.companyRepository.findById(id).orElse(null);
        BeanUtils.copyProperties(companyRequestDto, company);
        this.companyRepository.save(company);
        return "Company updated";
    }

    @Override
    public CompanyResponse getCompany(Long id) {
        Optional<Company> optional = this.companyRepository.findById(id);
        if (optional.isPresent()) {
            Company company = optional.get();
            CompanyResponse companyResponse = new CompanyResponse();
            BeanUtils.copyProperties(company, companyResponse);
            return companyResponse;
        }
        return null;
    }

    @Override
    public String deleteCompany(Long id) {
        this.companyRepository.deleteById(id);
        return "Company deleted";
    }


}
