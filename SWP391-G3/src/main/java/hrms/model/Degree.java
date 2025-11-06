package hrms.model;

public class Degree {
    private int degreeId;
    private String name;

    public Degree() {}

    public Degree(int degreeId, String name) {
        this.degreeId = degreeId;
        this.name = name;
    }

    public int getDegreeId() {
        return degreeId;
    }

    public void setDegreeId(int degreeId) {
        this.degreeId = degreeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Degree{" +
                "degreeId=" + degreeId +
                ", name='" + name + '\'' +
                '}';
    }
}
