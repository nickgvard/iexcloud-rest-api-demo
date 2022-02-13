package my.education.iexcloudrestapidemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgresqlEntityManagerFactory",
        transactionManagerRef = "postgresqlTransactionManager",
        basePackages = {"my.education.iexcloudrestapidemo.postgresql.repository"}
)
@RequiredArgsConstructor
public class PostgreSqlDbConfig {

    private final Environment environment;

    @Bean
    @ConfigurationProperties(prefix = "postgresql.datasource")
    public DataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("postgresql.datasource.driver-class-name"));
        dataSource.setUrl(environment.getProperty("postgresql.datasource.url"));
        dataSource.setUsername(environment.getProperty("postgresql.datasource.username"));
        dataSource.setPassword(environment.getProperty("postgresql.datasource.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresqlEntityManagerFactory(
            EntityManagerFactoryBuilder builder,
            @Qualifier("postgresqlDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("my.education.iexcloudrestapidemo.postgresql.model")
                .build();
    }

    @Bean
    public PlatformTransactionManager postgresqlTransactionManager(
            @Qualifier("postgresqlEntityManagerFactory") EntityManagerFactory postgresqlEntityManagerFactory) {
        return new JpaTransactionManager(postgresqlEntityManagerFactory);
    }
}
