package my.education.iexcloudrestapidemo.postgresql.repository;

import my.education.iexcloudrestapidemo.postgresql.model.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Repository
@Transactional("postgresqlTransactionManager")
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findCompanyBySymbol(String symbol);

    List<Company> findTop5By(Sort sort);

    List<Company> findCompaniesBySymbolNotInOrderBySymbolAsc(Collection<String> symbols);

    @Query("SELECT c FROM Logbook l LEFT JOIN l.company c " +
            "WHERE ABS(l.currentPrice - l.oldPrice) > 0 " +
            "ORDER BY ABS(l.currentPrice - l.oldPrice) DESC")
    List<Company> findTop5ByDeltaLatestPrice(Pageable pageable);
}
