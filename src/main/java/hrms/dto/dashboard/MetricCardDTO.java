package hrms.dto.dashboard;

public class MetricCardDTO {
    private String title;
    private String value;
    private String subtitle;
    private String icon;
    private String link;

    public MetricCardDTO() {
    }

    public MetricCardDTO(String title, String value, String subtitle, String icon, String link) {
        this.title = title;
        this.value = value;
        this.subtitle = subtitle;
        this.icon = icon;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
