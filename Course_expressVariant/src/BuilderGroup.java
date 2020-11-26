import java.util.*;

public class BuilderGroup {

    private final static String GROUP_FORMAT_STRING =
            "Dynamics of construction: %-s, %-5d lines";
    private String name;
    private List<Builder> builders;

    public BuilderGroup() {
        this.name = "";
        this.builders = new ArrayList<Builder>();
    }

    public BuilderGroup(String name) {
        this.name = name;
        this.builders = new ArrayList<Builder>();
    }

    public BuilderGroup(String name, List<Builder> list) {
        this.name = name;
        this.builders = new ArrayList<>(list);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Builder> getBuilders() {
        return builders;
    }

    public void setBuilders(List<Builder> builders) {
        this.builders = builders;
    }

    public String toString() {
        return String.format(GROUP_FORMAT_STRING, name, getBuilderNum());
    }

    public boolean AddBuilder(Builder build) {
        if (getBuilder(build) != null)
            return false;
        return this.builders.add(build);
    }

    public boolean DelBuilder(Builder build) {
        return builders.removeIf(el -> (el.getBuilderId() == build.getBuilderId() && el.getObjectName().toLowerCase().equals(build.getObjectName().toLowerCase())));
    }

    public boolean DelInpReadiness(String inpReadiness) {
        return builders.removeAll(LessInpReadiness(inpReadiness).builders);
    }

    public boolean UpdBuilderReadiness(Builder build, String date, int readiness) {
            for (var el : builders) {
                if (el.getBuilderId() == build.getBuilderId() && el.getObjectName().equals(build.getObjectName())) {
                    el.setDate(date);
                    el.setReadiness(readiness);
                }
            }
            return true;
    }

    public Builder getBuilder(Builder build) {
        for (Builder b : builders)
            if (b.equals(build)) return b;
        return null;
    }

    public int getBuilderNum() {
        return builders.size();
    }

    public BuilderGroup LessInpReadiness(String inpReadiness) {
        int readiness = Integer.parseInt(inpReadiness);
        BuilderGroup group = new BuilderGroup(name + ": Builders who have a percentage of readiness below the entered - " + readiness);
        for (Builder build : builders)
            if (build.getReadiness() < readiness) group.AddBuilder(build);
        return group;
    }

    public BuilderGroup filterObjects(String filter) {
        BuilderGroup group = new BuilderGroup(name + ": objects starting with \"" + filter + "\"");
        for (Builder build : builders)
            if (build.getObjectName().startsWith(filter)) group.AddBuilder(build);
        return group;
    }

    public int ObjectsNumber() {
        int size = builders.size();
        if (size == 0) return 0;
        Set<String> objectsS = new TreeSet<String>();
        for (Builder build : builders)
            objectsS.add(build.getObjectName());
        return objectsS.size();
    }

    public List<String> MaxReadinessForEveryBuilder() {

        var result = new ArrayList<String>();
        var builders = new ArrayList<Integer>();

        for (var builder : getBuilders()) {
            boolean flag = false;
            for (var el : builders) {
                if (builder.getBuilderId() == el) {
                    flag = true;
                    break;
                }
            }
            if (!flag) builders.add(builder.getBuilderId());
        }

        for (var el : builders) {
            var max = Integer.MIN_VALUE;
            for (var builder : getBuilders()) {
                if (el == builder.getBuilderId()) {
                    if (builder.getReadiness() > max) max = builder.getReadiness();
                }
            }
            result.add(String.format("Builder: %s     Max: %d", el, max));
        }

        return result;
    }

    public BuilderGroup Sort(Comparator comp) {
        BuilderGroup group = new BuilderGroup(name, builders);
        group.builders.sort(comp);
        return group;
    }
}
