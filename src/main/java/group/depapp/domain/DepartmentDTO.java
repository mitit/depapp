package group.depapp.domain;

public class DepartmentDTO {

    private Integer id;
    private String depCode;
    private String depJob;
    private String description;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.depCode = department.getDepCode();
        this.depJob = department.getDepJob();
        this.description = department.getDescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getDepJob() {
        return depJob;
    }

    public void setDepJob(String depJob) {
        this.depJob = depJob;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department toDepartmentEntity(DepartmentDTO departmentDTO) {
        Department department = new Department();

        department.setDepCode(departmentDTO.getDepCode());
        department.setDepJob(departmentDTO.getDepJob());
        department.setDescription(departmentDTO.getDescription());

        return department;
    }
}
