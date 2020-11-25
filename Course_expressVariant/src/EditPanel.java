import java.awt.*;
import javax.swing.*;
import java.awt.event.*;//важно


public class EditPanel extends JPanel {

    JTextField tf1;
    JTextField tf2;
    JTextField tf3;
    JTextField tf4;
    JTextField tf5;

    public EditPanel() {
        setLayout(new GridLayout(3, 3, 2, 2));
        JButton but1 = new JButton("Add");
        JButton but2 = new JButton("Update");
        JButton but3 = new JButton("Delete");
        JButton but4 = new JButton("Delete group:");
        tf1 = new JTextField("");
        tf2 = new JTextField("");
        tf3 = new JTextField("");
        tf4 = new JTextField("");
        tf5 = new JTextField("", 10);
        JLabel l1 = new JLabel("");
        JLabel l2 = new JLabel("  readiness less than ");
        JPanel p1 = new JPanel();
        add(tf1);
        add(tf2);
        add(tf3);
        add(tf4);
        add(but1);
        add(but2);
        add(l1);
        add(but3);
        add(but4);
        add(p1);
        p1.add(l2);
        p1.add(tf5);
        but1.addActionListener(new ActionListener() { //анонимный слушатель
                                   public void actionPerformed(ActionEvent e) {
                                       insert();
                                   }
                               }
        );
        but2.addActionListener(new ActionListener() { //анонимный слушатель
                                   public void actionPerformed(ActionEvent e) {
                                       update();
                                   }
                               }
        );
        but3.addActionListener(new ActionListener() { //анонимный слушатель
                                   public void actionPerformed(ActionEvent e) {
                                       delete();
                                   }
                               }
        );
        but4.addActionListener(new ActionListener() { //анонимный слушатель
                                   public void actionPerformed(ActionEvent e) {
                                       deleteGroup();
                                   }
                               }
        );
    }

    private void insert() {
        int n1, n4;
        String str1, str2, str3, str4;
        str1 = tf1.getText();
        str2 = tf2.getText();
        str3 = tf3.getText();
        str4 = tf4.getText();
        if (str1.equals("") || str2.equals("") || str3.equals("") || str4.equals("")) {
            MainFrame.MSG.setText("Задайте значения полей");
            return;
        }
        try {
            n1 = Integer.parseInt(str1);
            n4 = Integer.parseInt(str4);
        }//try
        catch (NumberFormatException e) {
            MainFrame.MSG.setText("   Задайте правильно число голов");
            return;
        }
        MainFrame.MSG.setText(
                "   Запрос на добавление записи в таблицу");
        if (!Global.table.AddBuilder(new Builder(n1, str2, str3, n4)))
            MainFrame.MSG.setText(
                    "   Запись не добавлена, возможно нарушена уникальность ключа");
        Global.updateJTable(Global.table.getBuilders());
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
    }

    private void update() {
        int n1, n4;
        String str1, str2, str3, str4;
        str1 = tf1.getText();
        str2 = tf2.getText();
        str3 = tf3.getText();
        str4 = tf4.getText();
        if (str1.equals("") || str2.equals("") || str3.equals("") || str4.equals("")) {
            MainFrame.MSG.setText("Задайте значения полей");
            return;
        }
        try {
            n1 = Integer.parseInt(str1);
            n4 = Integer.parseInt(str4);
        }
        catch (NumberFormatException e) {// обработчик исключения для try
            MainFrame.MSG.setText("   Задайте правильно число голов");
            return;
        }
        MainFrame.MSG.setText(
                "   Запрос на обновление записи в таблице");
        if (!Global.table.UpdBuilderReadiness(new Builder(n1, str2, str3, n4)))
            MainFrame.MSG.setText(
                    "   Запись не обновлена, возможно записи с таким ключом нет");
        Global.updateJTable(Global.table.getBuilders());
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
    }

    private void delete() {
        int n1, n4;
        String str1, str2;
        str1 = tf1.getText();
        str2 = tf2.getText();
        if (str1.equals("") || str2.equals("")) {
            MainFrame.MSG.setText("Задайте значения полей ключа");
            return;
        }
        try {
            n1 = Integer.parseInt(str1);
        }
        catch (NumberFormatException e) {
            MainFrame.MSG.setText("   Задайте правильно число голов");
            return;
        }
        MainFrame.MSG.setText(
                "   Запрос на удаление записи по ключу");
        if (!Global.table.DelBuilder(new Builder(n1, str2, "", 0)))
            MainFrame.MSG.setText(
                    "   Запись не удалена, возможно записи с таким ключом нет");
        Global.updateJTable(Global.table.getBuilders());
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
    }

    private void deleteGroup() {
        if (!Global.table.DelInpReadiness(tf5.getText()))
            MainFrame.MSG.setText(
                    "   Записи не удалены, возможно таких записей нет");
        Global.updateJTable(Global.table.getBuilders());
        tf1.setText("");
        tf2.setText("");
        tf3.setText("");
        tf4.setText("");
        tf5.setText("");
    }
} 