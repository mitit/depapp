package group.depapp.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


/**
 * Конфигурационный файл для подключения к базе данных и конфигурации бинов для работы с ней
 */

@Configuration
@ComponentScan
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})

public class AppConfig {

    private static final Logger log = Logger.getLogger(AppConfig.class);

    @Value("${init-db:false}")
    private String initDatabase;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public DataSource dataSource() {
        FileInputStream fis;
        Properties property = new Properties();
        HikariConfig config = new HikariConfig();

        try {
            fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);

            config.setDriverClassName(property.getProperty("spring.dataSource.driver-class-name"));
            config.setJdbcUrl(property.getProperty("spring.dataSource.url"));
            config.setUsername(property.getProperty("spring.dataSource.username"));
            config.setPassword(property.getProperty("spring.dataSource.password"));

            log.info("DATASOURCE CREATED");
        } catch (IOException e) {
            log.error("ERROR DATASOURCE CREATING: " + e.getMessage(), e);
        }
        return new HikariDataSource(config);
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource("data.sql"));
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
        dataSourceInitializer.setEnabled(Boolean.parseBoolean(initDatabase));
        return dataSourceInitializer;
    }
}