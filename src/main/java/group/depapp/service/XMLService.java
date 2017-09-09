package group.depapp.service;

import group.depapp.domain.Department;

import java.util.List;

public interface XMLService {
    void create();

    List<Department> parse();
}
