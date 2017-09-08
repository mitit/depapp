package group.depapp.repository;

import group.depapp.domain.Department;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.util.ArrayList;
import java.util.List;

public class DepartmentRepositoryImpl extends JdbcDaoSupport implements DepartmentRepository<Department> {

    private static final String SQL_INSERT_DEPARTMENT = "insert into department (dep_code, dep_job, description) " +
            "values (?, ?, ?)";

    private static final String SQL_SELECT_ALL_FROM_DEPARTMENT = "select * from department";

    private static final String SQL_UPDATE_DESCRIPTION_DEPARTMENT = "update department set description = ? where dep_code = ? and dep_job = ?";

    private static final String SQL_DELETE_FROM_DEPARTMENT = "delete from department where (dep_code = ? and dep_job = ?)";

    private static final String TEST = "delete from department where (dep_code = ? and dep_job = ?) or (dep_code = ? and dep_job = ?)";

    @Override
    public void persist(Department dep) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername("postgres");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setPassword("postgres");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(SQL_INSERT_DEPARTMENT,
                dep.getDepCode(), dep.getDepJob(), dep.getDescription());

    }

    @Override
    public List<Department> getAll() {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername("postgres");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setPassword("postgres");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        return jdbcTemplate.query(SQL_SELECT_ALL_FROM_DEPARTMENT, ROW_MAPPER);
    }

    @Override
    public void update(Department dep) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername("postgres");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setPassword("postgres");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(SQL_UPDATE_DESCRIPTION_DEPARTMENT, dep.getDescription(), dep.getDepCode(), dep.getDepJob());
    }

    @Override
    public void delete(Department dep) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername("postgres");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setPassword("postgres");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.update(SQL_DELETE_FROM_DEPARTMENT, dep.getDepCode(), dep.getDepJob());
    }

    @Override
    public void delete(List<Department> departmentList) {
        SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriverClass(org.postgresql.Driver.class);
        dataSource.setUsername("postgres");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setPassword("postgres");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String newDeleteString = SQL_DELETE_FROM_DEPARTMENT;
        List<String> parameters = new ArrayList<>();

        for (int i = 0; i < departmentList.size(); i++) {
            if (i != 0) newDeleteString+= " or (dep_code = '" +  departmentList.get(i).getDepCode() + "' and dep_job = '" + departmentList.get(i).getDepJob() +"')";
//            parameters.add(departmentList.get(i).getDepCode());
//            parameters.add(departmentList.get(i).getDepJob());
        }

        System.out.println(newDeleteString);
        jdbcTemplate.update(newDeleteString, departmentList.get(0).getDepCode(), departmentList.get(0).getDepJob());

    }
}



