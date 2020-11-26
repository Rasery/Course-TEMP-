import javax.swing.*;
import java.awt.*;


public class EditPanel extends JPanel {

    JTextField tfId;
    JTextField tfObjName;
    JTextField tfDate;
    JTextField tfReadiness;
    JTextField tfInpReadiness;

    public EditPanel() {

        setLayout(new GridLayout(3, 3, 2, 2));
        JButton butAdd = new JButton("Add");
        JButton butUpd = new JButton("Update");
        JButton butDel = new JButton("Delete");
        JButton butDelGr = new JButton("Delete group:");
        tfId = new JTextField("");
        tfObjName = new JTextField("");
        tfDate = new JTextField("");
        tfReadiness = new JTextField("");
        tfInpReadiness = new JTextField("", 10);
        JLabel labelEmpty = new JLabel("");
        JLabel labelLess = new JLabel("  readiness less than ");
        JPanel panel = new JPanel();

        add(tfId);
        add(tfObjName);
        add(tfDate);
        add(tfReadiness);
        add(butAdd);
        add(butUpd);
        add(labelEmpty);
        add(butDel);
        add(butDelGr);
        add(panel);
        panel.add(labelLess);
        panel.add(tfInpReadiness);

        butAdd.addActionListener(e -> insert());
        butUpd.addActionListener(e -> update());
        butDel.addActionListener(e -> delete());
        butDelGr.addActionListener(e -> deleteGroup());
    }

    private void insert() {

        int id, readiness;
        var idStr = tfId.getText();
        var objName = tfObjName.getText();
        var date = tfDate.getText();
        var readinessStr = tfReadiness.getText();

        if (idStr.equals("") || objName.equals("") || date.equals("") || readinessStr.equals("")) {
            MainFrame.MSG.setText("Set field values");
            return;
        }

        try {
            id = Integer.parseInt(idStr);
            readiness = Integer.parseInt(readinessStr);
        }
        catch (NumberFormatException e) {
            MainFrame.MSG.setText("   Set the readiness percentage correctly");
            return;
        }

        MainFrame.MSG.setText("   Request to add an entry to the table");
        if (!Global.table.AddBuilder(new Builder(id, objName, date, readiness)))
            MainFrame.MSG.setText("   Entry not added, possibly key uniqueness violated");
        Global.updateJTable(Global.table.getBuilders());
        tfId.setText("");
        tfObjName.setText("");
        tfDate.setText("");
        tfReadiness.setText("");
    }

    private void update() {
        int id, readiness;
        String idStr, objName, date, readinessStr;
        idStr = tfId.getText();
        objName = tfObjName.getText();
        date = tfDate.getText();
        readinessStr = tfReadiness.getText();
        if (idStr.equals("") || objName.equals("") || date.equals("") || readinessStr.equals("")) {
            MainFrame.MSG.setText("Задайте значения полей");
            return;
        }
        try {
            id = Integer.parseInt(idStr);
            readiness = Integer.parseInt(readinessStr);
        } catch (NumberFormatException e) {
            MainFrame.MSG.setText("   Задайте правильно число голов");
            return;
        }
        MainFrame.MSG.setText("   Запрос на обновление записи в таблице");
        if (!Global.table.UpdBuilderReadiness(new Builder(id, objName, "", 0), date, readiness))
            MainFrame.MSG.setText("   Запись не обновлена, возможно записи с таким ключом нет");
        Global.updateJTable(Global.table.getBuilders());
        tfId.setText("");
        tfObjName.setText("");
        tfDate.setText("");
        tfReadiness.setText("");
    }

    private void delete() {
        int id;
        var idStr = tfId.getText();
        var objName = tfObjName.getText();
        if (idStr.equals("") || objName.equals("")) {
            MainFrame.MSG.setText("Задайте значения полей ключа");
            return;
        }
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            MainFrame.MSG.setText("   Задайте правильно число голов");
            return;
        }
        MainFrame.MSG.setText("   Запрос на удаление записи по ключу");

        if (!Global.table.DelBuilder(new Builder(id, objName, "", 0)))
            MainFrame.MSG.setText("   Запись не удалена, возможно записи с таким ключом нет");

        Global.updateJTable(Global.table.getBuilders());
        tfId.setText("");
        tfObjName.setText("");
        tfDate.setText("");
        tfReadiness.setText("");
    }

    private void deleteGroup() {
        if (!Global.table.DelInpReadiness(tfInpReadiness.getText()))
            MainFrame.MSG.setText(
                    "   Записи не удалены, возможно таких записей нет");
        Global.updateJTable(Global.table.getBuilders());
        tfId.setText("");
        tfObjName.setText("");
        tfDate.setText("");
        tfReadiness.setText("");
        tfInpReadiness.setText("");
    }
} 