package hrms.model;

public class Department {
    private int departmentId;
    private String name;
    private int managerId; 

    public Department() {}

    public Department(int departmentId, String name, int managerId) {
        this.departmentId = departmentId;
        this.name = name;
        this.managerId = managerId;
    }

  
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", name='" + name + '\'' +
                ", managerId=" + managerId +
                '}';
    }
}
