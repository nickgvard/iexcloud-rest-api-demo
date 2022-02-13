package my.education.iexcloudrestapidemo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.education.iexcloudrestapidemo.dto.CompanyDto;
import my.education.iexcloudrestapidemo.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/companies")
@RequiredArgsConstructor
@Slf4j
public class CompaniesRestControllerV1 {

    private final CompanyService companyService;

    @GetMapping("/stocks")
    public ResponseEntity<List<CompanyDto>> findAll() {
        long start = System.currentTimeMillis();
        List<CompanyDto> companyDtos = companyService.findAll().join();
        long finish = System.currentTimeMillis();
        log.info("result ----> {}", (finish - start));
        return ResponseEntity.ok(companyDtos);
    }

    @GetMapping("/{symbol}/stocks")
    public ResponseEntity<CompanyDto> findBySymbol(@PathVariable String symbol) {
        return ResponseEntity.ok(companyService.findCompanyBySymbol(symbol));
    }

    @GetMapping("/stocks/top")
    public ResponseEntity<List<CompanyDto>> findTop5AndOther() {
        return ResponseEntity.ok(companyService.findTop5AndOther().join());
    }

    @GetMapping("/stocks/delta")
    public ResponseEntity<List<CompanyDto>> findTop5ByDeltaLatestPrice() {
        return ResponseEntity.ok(companyService.findTop5ByDeltaLatestPrice());
    }
}
