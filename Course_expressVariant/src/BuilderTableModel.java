import javax.swing.table.AbstractTableModel;
import java.util.*;

public class BuilderTableModel extends AbstractTableModel {
    //Модель для основной таблицы
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
        switch (c) {
            case 0:
                return builders.get(r).getBuilderId();
            case 1:
                return builders.get(r).getObjectName();
            case 2:
                return builders.get(r).getDate();
            case 3:
                return builders.get(r).getReadiness();
            default:
                return "";
        }
    }

    @Override
    public String getColumnName(int c) {
        String name = "";
        switch (c) {
            case 0:
                name = "ID";
                break;
            case 1:
                name = "Object name";
                break;
            case 2:
                name = "Date";
                break;
            case 3:
                name = "Readiness";
                break;
        }
        return name;
    }
}