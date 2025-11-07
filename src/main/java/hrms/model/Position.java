package hrms.model;

public class Position {
    private Integer positionId;
    private String name;
    private Integer departmentId; // Liên kết Department

    public Position() {}

    public Position(Integer positionId, String name, Integer departmentId) {
        this.positionId = positionId;
        this.name = name;
        this.departmentId = departmentId;
    }

    public Integer getPositionId() {
        return positionId;
    }

    public void setPositionId(Integer positionId) {
        this.positionId = positionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
