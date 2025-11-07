package hrms.dto;

public class PositionDTO {
    private int positionId;
    private String name;
    private int departmentId;
    private String departmentName;

    public PositionDTO() {}

    public PositionDTO(int positionId, String name, int departmentId, String departmentName) {
        this.positionId = positionId;
        this.name = name;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
    }

    // Getters and Setters
    public int getPositionId() { return positionId; }
    public void setPositionId(int positionId) { this.positionId = positionId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
}
