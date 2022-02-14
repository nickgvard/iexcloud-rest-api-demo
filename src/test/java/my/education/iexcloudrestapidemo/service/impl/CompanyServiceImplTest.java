package my.education.iexcloudrestapidemo.service.impl;

import my.education.iexcloudrestapidemo.dto.CompanyDto;
import my.education.iexcloudrestapidemo.postgresql.model.Company;
import my.education.iexcloudrestapidemo.postgresql.repository.CompanyRepository;
import my.education.iexcloudrestapidemo.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Test
    void findAll() {
        doReturn(Arrays.asList(company("A"), company("AA")))
                .when(companyRepository).findAll();
        List<CompanyDto> all = companyService.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void findCompanyBySymbol() {
        String symbol = "AB";
        Company expected = company(symbol);
        doReturn(expected)
                .when(companyRepository).findCompanyBySymbol(symbol);
        CompanyDto actual = companyService.findCompanyBySymbol(symbol);
        assertEquals(actual.getSymbol(), expected.getSymbol());
    }

    @Test
    void findTop5AndOther() {

    }

    @Test
    void findTop5ByDeltaLatestPrice() {

    }

    private Company company(String symbol) {
        return Company.builder()
                .symbol(symbol)
                .build();
    }
}