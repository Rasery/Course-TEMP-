import java.util.Objects;

public class Builder implements Comparable<Builder> {

    private int id;
    private int readiness;
    private String objectName;
    private String date;

    public Builder(int id, String objectName, String date, int readiness) {
        this.id = id;
        this.objectName = objectName;
        this.date = date;
        this.readiness = readiness;
    }

    public Builder() { }

    public int getBuilderId() {
        return id;
    }

    public void setBuilderId(int id) {
        this.id = id;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getReadiness() {
        return readiness;
    }

    public void setReadiness(int readiness) {
        this.readiness = readiness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Builder builder = (Builder) o;
        return id == builder.id &&
                readiness == builder.readiness &&
                Objects.equals(objectName, builder.objectName) &&
                Objects.equals(date, builder.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, readiness, objectName, date);
    }

    @Override
    public int compareTo(Builder build) {
        if (id < build.id) return -1;
        if (id > build.id) return 1;
        return Integer.compare(readiness, build.readiness);
    }
}
