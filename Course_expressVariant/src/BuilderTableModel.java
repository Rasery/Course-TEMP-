import javax.swing.table.AbstractTableModel;
import java.util.*;

public class BuilderTableModel extends AbstractTableModel {

    List<Builder> builders;

    public BuilderTableModel(List<Builder> builders) {
        super();
        this.builders = builders;
    }

    @Override
    public int getRowCount() {
        return builders.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int r, int c) {
        return switch (c) {
            case 0 -> builders.get(r).getBuilderId();
            case 1 -> builders.get(r).getObjectName();
            case 2 -> builders.get(r).getDate();
            case 3 -> builders.get(r).getReadiness();
            default -> "";
        };
    }

    @Override
    public String getColumnName(int c) {
        return switch (c) {
            case 0 -> "ID";
            case 1 -> "Object name";
            case 2 -> "Date";
            case 3 -> "Readiness";
            default -> "";
        };
    }
}