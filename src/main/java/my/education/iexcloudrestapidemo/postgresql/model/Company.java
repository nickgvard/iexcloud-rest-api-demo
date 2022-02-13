package my.education.iexcloudrestapidemo.postgresql.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "companies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    private Long id;
    private String symbol;
    @Column(name = "company_name")
    private String companyName;
    @Column(name = "previous_volume")
    private Long previousVolume;
    private Long volume;
    @Column(name = "latest_price")
    private Float latestPrice;
}
