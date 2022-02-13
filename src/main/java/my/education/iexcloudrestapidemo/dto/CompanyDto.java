package my.education.iexcloudrestapidemo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import my.education.iexcloudrestapidemo.postgresql.model.Company;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyDto {

    private Long id;
    private String symbol;
    private String companyName;
    private Long previousVolume;
    private Long volume;
    private Float latestPrice;

    public static CompanyDto toDto(Company company) {
        return CompanyDto
                .builder()
                .id(company.getId())
                .symbol(company.getSymbol())
                .companyName(company.getCompanyName())
                .previousVolume(company.getPreviousVolume())
                .volume(company.getVolume())
                .latestPrice(company.getLatestPrice())
                .build();
    }

    public static Company toEntity(CompanyDto companyDto) {
        return Company
                .builder()
                .id(companyDto.getId())
                .symbol(companyDto.getSymbol())
                .companyName(companyDto.getCompanyName())
                .previousVolume(companyDto.getPreviousVolume())
                .volume(companyDto.getVolume())
                .latestPrice(companyDto.getLatestPrice())
                .build();
    }
}
