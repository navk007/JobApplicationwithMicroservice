package com.navdeep.companyMS.company;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();
    Company getCompanyById(Long id);
    void createCompany(Company company);
    Boolean deleteCompany(Long id);
    Boolean updateCompany(Long id, Company company);
}
