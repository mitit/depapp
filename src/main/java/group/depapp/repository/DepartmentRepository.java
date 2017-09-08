package group.depapp.repository;

import group.depapp.domain.Department;
import group.depapp.domain.MainDomain;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

public interface DepartmentRepository<X extends MainDomain> {

    RowMapper<Department> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new Department(resultSet.getString("dep_code"),
                resultSet.getString("dep_job"),
                resultSet.getString("description"));
    };

    void save(X domain);

    void save(List<X> domainList);

    List<X> getAll();

    void update(X domain);

    void delete(X domain);

    void delete(List<X> domainList);
}

