package group.depapp.domain;


public class DepartmentDTO {

    private String depCode;
    private String depJob;
    private String description;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Department department) {
        this.depCode = department.getDepCode();
        this.depJob = department.getDepJob();
        this.description = department.getDescription();
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

    public Department toDepartmentEntity() {
        Department department = new Department();

        department.setDepCode(this.getDepCode());
        department.setDepJob(this.getDepJob());
        department.setDescription(this.getDescription());

        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DepartmentDTO that = (DepartmentDTO) o;

        if (!depCode.equals(that.depCode)) return false;
        return depJob.equals(that.depJob);
    }

    @Override
    public int hashCode() {
        int result = depCode.hashCode();
        result = 31 * result + depJob.hashCode();
        return result;
    }
}
