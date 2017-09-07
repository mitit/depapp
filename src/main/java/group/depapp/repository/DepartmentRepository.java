package group.depapp.repository;

import group.depapp.domain.MainDomain;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.List;
import java.util.Set;

public interface DepartmentRepository<X extends MainDomain> {

    void persist(X domain);

    List<X> getAll();
}

