package group.depapp.repository;

import group.depapp.domain.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository<Department> {

    private static final String SQL_INSERT_DEPARTMENT = "insert into department (dep_code, dep_job, description) " +
            "values (?, ?, ?)";

    private static final String SQL_SELECT_ALL_FROM_DEPARTMENT = "select * from department";

    private static final String SQL_UPDATE_DESCRIPTION_DEPARTMENT = "update department set description = ? where dep_code = ? and dep_job = ?";

    private static final String SQL_DELETE_FROM_DEPARTMENT = "delete from department where (dep_code = ? and dep_job = ?)";

    private JdbcOperations jdbcOperations;

    @Autowired
    public DepartmentRepositoryImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void save(Department dep) {
        jdbcOperations.update(SQL_INSERT_DEPARTMENT,
                dep.getDepCode(), dep.getDepJob(), dep.getDescription());
    }

    @Override
    public void save(List<Department> departmentList) {

        String newInsertString = SQL_INSERT_DEPARTMENT;

        for (int i = 0; i < departmentList.size(); i++) {
            if (i != 0)
                newInsertString += ", ( '"
                        + departmentList.get(i).getDepCode() + "', '"
                        + departmentList.get(i).getDepJob() + "', '"
                        + departmentList.get(i).getDescription() + "')";
        }
        jdbcOperations
                .update(newInsertString, departmentList.get(0).getDepCode(), departmentList.get(0).getDepJob(), departmentList.get(0).getDescription());
    }

    @Override
    public List<Department> getAll() {
        return jdbcOperations.query(SQL_SELECT_ALL_FROM_DEPARTMENT, ROW_MAPPER);
    }

    @Override
    public void update(Department dep) {
        jdbcOperations.update(SQL_UPDATE_DESCRIPTION_DEPARTMENT, dep.getDescription(), dep.getDepCode(), dep.getDepJob());
    }

    @Override
    public void delete(Department dep) {
        jdbcOperations.update(SQL_DELETE_FROM_DEPARTMENT, dep.getDepCode(), dep.getDepJob());
    }

    @Override
    public void delete(List<Department> departmentList) {
        String newDeleteString = SQL_DELETE_FROM_DEPARTMENT;

        for (int i = 0; i < departmentList.size(); i++) {
            if (i != 0)
                newDeleteString += " or (dep_code = '"
                        + departmentList.get(i).getDepCode()
                        + "' and dep_job = '"
                        + departmentList.get(i).getDepJob() + "')";
        }
        jdbcOperations.update(newDeleteString, departmentList.get(0).getDepCode(), departmentList.get(0).getDepJob());
    }
}
