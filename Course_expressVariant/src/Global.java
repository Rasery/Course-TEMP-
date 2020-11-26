import java.util.List;

public class Global {

    public static BuilderGroup table;
    public static List<Builder> builders;
    static BuilderTableModel tableModel;

    public static void updateJTable(List<Builder> build) {
        builders.clear();
        builders.addAll(build);
        tableModel.fireTableDataChanged();
    }
}
