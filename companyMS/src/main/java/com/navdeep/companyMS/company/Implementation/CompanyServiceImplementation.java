package com.navdeep.companyMS.company.Implementation;

import com.navdeep.companyMS.company.Company;
import com.navdeep.companyMS.company.CompanyRepository;
import com.navdeep.companyMS.company.CompanyService;
import com.navdeep.companyMS.company.clients.ReviewClient;
import com.navdeep.companyMS.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImplementation implements CompanyService {

    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImplementation(CompanyRepository companyRepository, ReviewClient reviewClient){
        this.companyRepository=companyRepository;
        this.reviewClient=reviewClient;
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

    public void updateCompanyRating(ReviewMessage reviewMessage){
//        System.out.println(reviewMessage.getCompanyId());
        Company company=companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(()-> new NotFoundException("Company not found" + reviewMessage.getCompanyId()));
        Double averageRating= reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }
}
