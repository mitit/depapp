package group.depapp;

import group.depapp.domain.Department;
import group.depapp.domain.DepartmentDTO;
import group.depapp.repository.DepartmentRepositoryImpl;
import group.depapp.service.DepartmentServiceImpl;
import group.depapp.util.JpaConfig;
import group.depapp.util.Synchronizer;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan
@EntityScan("group.depapp")
public class DepappApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(new Class<?>[] {DepappApplication.class, JpaConfig.class});

        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Override
    public void run(String... args) throws Exception {

//        DepartmentServiceImpl departmentService = new DepartmentServiceImpl();
//        DepartmentDTO departmentDTO = new DepartmentDTO(new Department("2", "dev", "newDesc"));
//        DepartmentDTO departmentDTO1 = new DepartmentDTO(new Department("4", "mark", "newDesc"));
//        List<DepartmentDTO> departmentDTOS = new ArrayList<>();
//        departmentDTOS.add(departmentDTO);
//        departmentDTOS.add(departmentDTO1);
//        departmentService.save(departmentDTOS);

        Synchronizer synchronizer = new Synchronizer();
        synchronizer.synchronize();


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