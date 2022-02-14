package my.education.iexcloudrestapidemo.service.impl;

import my.education.iexcloudrestapidemo.dto.CompanyDto;
import my.education.iexcloudrestapidemo.postgresql.model.Company;
import my.education.iexcloudrestapidemo.postgresql.model.Logbook;
import my.education.iexcloudrestapidemo.postgresql.repository.CompanyRepository;
import my.education.iexcloudrestapidemo.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceImplTest {

    @InjectMocks
    private CompanyServiceImpl companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Test
    void findAll() {
        doReturn(Arrays.asList(
                company("A"),
                company("AA"),
                company("AAA")))
                .when(companyRepository).findAll();

        List<CompanyDto> all = companyService.findAll();

        assertEquals(3, all.size());
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
        Company a = company("A");
        Company aa = company("AA");
        Company aaa = company("AAA");

        a.setVolume(15L);
        aa.setVolume(20L);
        aaa.setVolume(25L);

        List<Company> sortedByVolume = Stream.of(a, aa, aaa)
                .sorted(Comparator.comparingLong(Company::getVolume).reversed())
                .collect(Collectors.toList());

        Company abcd = company("ABCD");
        Company yybd = company("YYBD");
        Company gelm = company("GELM");
        Company tlcp = company("TLCP");

        List<Company> other = Stream.of(abcd, yybd, gelm, tlcp)
                .sorted(Comparator.comparing(Company::getSymbol))
                .collect(Collectors.toList());

        List<Company> expected = Stream.of(sortedByVolume, other)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        given(companyRepository.findTop5By(Sort.by(Sort.Direction.DESC, "previousVolume", "volume")))
                .willReturn(expected);

        companyService.findTop5AndOther();

        assertEquals(expected.get(0).getVolume(), aaa.getVolume());
        assertEquals(expected.get(2).getVolume(), a.getVolume());
        assertEquals(expected.get(3).getSymbol(), abcd.getSymbol());
        assertEquals(expected.get(5).getSymbol(), tlcp.getSymbol());
        assertEquals(expected.get(6).getSymbol(), yybd.getSymbol());
    }

    @Test
    void findTop5ByDeltaLatestPrice() {
        Logbook a = logbook("A");
        Logbook aa = logbook("AA");
        Logbook aaa = logbook("AAA");
        Logbook ab = logbook("AB");
        Logbook abb = logbook("ABB");
        Logbook ac = logbook("AC");
        Logbook acc = logbook("ACC");

        a.setCurrentPrice(0.1F); a.setOldPrice(0.4F); // 0.3
        aa.setCurrentPrice(0.1F); aa.setOldPrice(0.9F); // 0.8
        aaa.setCurrentPrice(0.7F); aaa.setOldPrice(1.3F); // 0.6
        ab.setCurrentPrice(10.8F); ab.setOldPrice(12.8F); // 2
        abb.setCurrentPrice(5.1F); abb.setOldPrice(5.8F); // 0.7
        ac.setCurrentPrice(19.1F); ac.setOldPrice(20.0F); // 0.9
        acc.setCurrentPrice(14.5F); acc.setOldPrice(15.1F); // 0.6

        List<Company> expected = Stream.of(a, aa, aaa, ab, abb, ac, acc)
                .sorted(Comparator.comparing(o -> {
                    Logbook logbook = (Logbook) o;
                    return Math.abs(logbook.getCurrentPrice() - logbook.getOldPrice());
                }).reversed())
                .limit(5)
                .map(Logbook::getCompany)
                .collect(Collectors.toList());

        given(companyRepository.findTop5ByDeltaLatestPrice(Pageable.ofSize(5))).willReturn(expected);

        companyService.findTop5ByDeltaLatestPrice();

        assertEquals(expected.get(0).getSymbol(), ab.getCompany().getSymbol());
        assertEquals(expected.get(1).getSymbol(), ac.getCompany().getSymbol());
        assertEquals(expected.get(4).getSymbol(), acc.getCompany().getSymbol());

        assertNotEquals(expected.get(2).getSymbol(), a.getCompany().getSymbol());
        assertNotEquals(expected.get(3).getSymbol(), aaa.getCompany().getSymbol());
    }

    private Company company(String symbol) {
        return Company.builder()
                .symbol(symbol)
                .build();
    }

    private Logbook logbook(String symbol) {
        return Logbook.builder()
                .company(company(symbol))
                .build();
    }
}