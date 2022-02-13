package my.education.iexcloudrestapidemo.service;

import my.education.iexcloudrestapidemo.dto.CompanyDto;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CompanyService {

    CompletableFuture<List<CompanyDto>> findAll();

    CompanyDto findCompanyBySymbol(String symbol);

    CompletableFuture<List<CompanyDto>> findTop5AndOther();

    List<CompanyDto> findTop5ByDeltaLatestPrice();
}
