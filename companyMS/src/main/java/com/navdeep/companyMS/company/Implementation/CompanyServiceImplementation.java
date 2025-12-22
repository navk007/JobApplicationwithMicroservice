package com.navdeep.companyMS.company.Implementation;

import com.navdeep.companyMS.company.Company;
import com.navdeep.companyMS.company.CompanyRepository;
import com.navdeep.companyMS.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImplementation implements CompanyService {

    CompanyRepository companyRepository;

    public CompanyServiceImplementation(CompanyRepository companyRepository){
        this.companyRepository=companyRepository;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Boolean deleteCompany(Long id) {
        Optional<Company> companyOptional=companyRepository.findById(id);
        if(companyOptional.isPresent()){
            companyRepository.deleteById(id);
            return true;
        } else{
            return false;
        }
    }

    @Override
    public Boolean updateCompany(Long id, Company updatedCompany) {
        Optional<Company> companyOptional=companyRepository.findById(id);
        if(companyOptional.isPresent()){
            Company company=companyOptional.get();
            company.setDescription(updatedCompany.getDescription());
            company.setName(updatedCompany.getName());
//            company.setReviewList(updatedCompany.getReviewList());
            companyRepository.save(company);
            return true;
        }
        return false;
    }
}
