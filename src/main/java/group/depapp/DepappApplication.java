package group.depapp;

import group.depapp.domain.Department;
import group.depapp.repository.DepartmentRepositoryImpl;
import group.depapp.util.CreateXMLFile;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.Set;

import static java.lang.System.exit;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
public class DepappApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DepappApplication.class);

        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

        CreateXMLFile createXMLFile = new CreateXMLFile();
        createXMLFile.create();

        if (args.length > 0) {
            System.out.println("LOOoooool");
        } else {

            SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
            dataSource.setDriverClass(org.postgresql.Driver.class);
            dataSource.setUsername("postgres");
            dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
            dataSource.setPassword("postgres");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

            DepartmentRepositoryImpl departmentRepository = new DepartmentRepositoryImpl();

//            System.out.println("Fucking tables");
//            jdbcTemplate.execute("drop table customers if exists");
//            jdbcTemplate.execute("create table customers(" +
//                    "id serial, first_name varchar(255), last_name varchar(255))");
//
//            String[] names = "John Woo;Jeff Dean;Josh Bloch;Josh Long".split(";");
//            for (String fullname : names) {
//                String[] name = fullname.split(" ");
//                System.out.printf("Inserting customer record for %s %s\n", name[0], name[1]);
//                jdbcTemplate.update(
//                        "INSERT INTO customers(first_name,last_name) values(?,?)",
//                        name[0], name[1]);
//            }
//
//            System.out.println("Querying for customer records where first_name = 'Josh':");
//            List<Customer> results = jdbcTemplate.query(
//                    "select * from customers where first_name = ?", new Object[]{"Josh"},
//                    (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"),
//                            rs.getString("last_name")));
//
//            for (Customer customer : results) {
//                System.out.println(customer);
//            }

//
//            Set<Department> rs =  departmentRepository.getAll();
//
//            for (Department d : rs) {
//                System.out.println("id: " + d.getId() + " code: " + d.getDepCode() +
//                        " job: " + d.getDepJob() + " desc: " + d.getDescription());
//            }
        }


        exit(0);
    }
}