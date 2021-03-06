package group.depapp.domain;


import java.io.Serializable;


/**
 * Ссущность, описывающая объект из таблицы Department
 */
public class Department implements Serializable {

    private String depCode;
    private String depJob;
    private String description;

    public Department() {
    }

    public Department(String depCode, String depJob, String description) {
        this.depCode = depCode;
        this.depJob = depJob;
        this.description = description;
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

        Department that = (Department) o;

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
