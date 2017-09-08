package group.depapp.repository;

import group.depapp.domain.Department;
import group.depapp.domain.MainDomain;
import group.depapp.util.JpaConfig;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.util.List;

public interface DepartmentRepository<X extends MainDomain> {

    RowMapper<Department> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new Department(resultSet.getString("dep_code"),
                resultSet.getString("dep_job"),
                resultSet.getString("description"));
    };

//    JdbcOperations jdbcTemplate = JpaConfig.jdbcTemplate();

    void save(X domain);

    void save(List<X> domainList);

    List<X> getAll();

    void update(X domain);

    void delete(X domain);

    void delete(List<X> domainList);
}

