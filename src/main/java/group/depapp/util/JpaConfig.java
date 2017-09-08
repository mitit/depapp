package group.depapp.util;

import group.depapp.DepappApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
//@EnableJpaRepositories(basePackageClasses = DepappApplication.class)

public class JpaConfig {

    @Bean("jdbcTemplate")
    public JdbcOperations jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean("dataSource")
    public DataSource dataSource() {
        FileInputStream fis;
        Properties property = new Properties();
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        try {
            fis = new FileInputStream("src/main/resources/db/db-config.properties");
            property.load(fis);

            dataSource.setDriverClass(org.postgresql.Driver.class);
            dataSource.setUsername(property.getProperty("db.username"));
            dataSource.setUrl(property.getProperty("db.url"));
            dataSource.setPassword(property.getProperty("db.password"));

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }

        return dataSource;
    }
}
