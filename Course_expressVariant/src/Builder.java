import java.util.Objects;

public class Builder implements Comparable<Builder> {

    private int id, readiness;
    private String objectName, date;

    public Builder(int id, String objectName, String date, int readiness) {
        this.id = id;
        this.objectName = objectName;
        this.date = date;
        this.readiness = readiness;
    }

    public Builder () { }

    public void setBuilderId(int id) { this.id = id; }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setReadiness(int readiness) {
        this.readiness = readiness;
    }

    public int getBuilderId() {
        return id;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getDate() {
        return date;
    }

    public int getReadiness() {
        return readiness;
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

    public int compareTo(Builder build) {
        //сравниваютс¤ только пол¤, составл¤ющие ключ!
        //по возрастанию названи¤ команды,
        //в рамках одной команды -
        //по возрастанию названи¤ чемпионата
        // сначала сравниваем названи¤ команд
        if (id < build.id) return -1;
        if (id > build.id) return 1;
        //названи¤ команд равны (одна и та же команда)
        //сравниваем названи¤ чемпионатов
        if (readiness < build.readiness) return -1;
        if (readiness > build.readiness) return 1;
        return 0; //названи¤ чемпионатов равны   (этого не будет, т.к. ключи
        // разных строк Ѕƒ не могут быть равны друг другу)
    }
}
