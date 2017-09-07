package group.depapp.repository;

import group.depapp.domain.Department;
import group.depapp.domain.MainDomain;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DepartmentRepositoryImpl extends JdbcDaoSupport implements DepartmentRepository<Department> {

    private static final String SQL_INSERT_DEPARTMENT = "insert into department (dep_code, dep_job, description) " +
            "values (?, ?, ?)";

    private static final String SQL_SELECT_ALL_FROM_DEPARTMENT = "select * from department";

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

        return jdbcTemplate.query("select * from department", ROW_MAPPER);
    }
}



