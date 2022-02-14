package my.education.iexcloudrestapidemo.service.impl;

import lombok.RequiredArgsConstructor;
import my.education.iexcloudrestapidemo.dto.CompanyDto;
import my.education.iexcloudrestapidemo.postgresql.model.Company;
import my.education.iexcloudrestapidemo.postgresql.repository.CompanyRepository;
import my.education.iexcloudrestapidemo.service.CompanyService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository repository;

    @Override
    public List<CompanyDto> findAll() {
        List<Company> all = repository.findAll();
        System.out.println();
        return all.stream()
                .map(CompanyDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyDto findCompanyBySymbol(String symbol) {
        return CompanyDto.toDto(repository.findCompanyBySymbol(symbol));
    }

    @Override
    public List<CompanyDto> findTop5AndOther() {
        List<Company> sortedBy = repository
                .findTop5By(Sort.by(Sort.Direction.DESC, "previousVolume", "volume"));

        List<Company> other = repository.findCompaniesBySymbolNotInOrderBySymbolAsc(
                sortedBy.stream().map(Company::getSymbol)
                        .collect(Collectors.toList()));

        return Stream.of(sortedBy, other)
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .stream()
                .map(CompanyDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompanyDto> findTop5ByDeltaLatestPrice() {
        List<Company> byDeltaLatestPrice = repository
                .findTop5ByDeltaLatestPrice(PageRequest.of(0, 5));
        return byDeltaLatestPrice.stream()
                .map(CompanyDto::toDto)
                .collect(Collectors.toList());
    }
}
