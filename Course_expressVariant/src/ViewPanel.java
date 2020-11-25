import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class ViewPanel extends JPanel implements ActionListener {

    JTextField tf;

    public ViewPanel() {
        setLayout(new GridLayout(4, 2, 2, 2));
        JButton but4 = new JButton("Max readiness for every builder company");
        JButton but5 = new JButton("Count of objects");
        JButton but6 = new JButton("Apply filter");
        JButton but8 = new JButton("Sort by id and readiness");
        JButton but9 = new JButton("Show all");
        tf = new JTextField("");
        JLabel l1 = new JLabel("Input filter for builders", JLabel.CENTER);
        JLabel l2 = new JLabel("");
        but4.setActionCommand("Total1");
        but5.setActionCommand("Total2");
        but6.setActionCommand("Filter");
        but8.setActionCommand("Sort2");
        but9.setActionCommand("All");
        add(l1);
        add(l2);
        add(tf);
        add(but6);
        add(but9);
        add(but8);
        add(but4);
        add(but5);

        but4.addActionListener(this);
        but5.addActionListener(this);
        but6.addActionListener(this);
        but8.addActionListener(this);
        but9.addActionListener(this);
    }

    private void showTotal1() {
        JScrollPane scrollPane = new JScrollPane();
        Box vBox = Box.createVerticalBox();
        for(var el : Global.table.result1()){
            vBox.add(new JLabel(el));
            System.out.println(el);
        }
//        BorderLayout borderLayout = new BorderLayout();
//        borderLayout.addLayoutComponent(scrollPane, null);
//        scrollPane.setLayout(new ScrollPaneLayout());

//        TotalDialog td = new TotalDialog(MainFrame.frame, "Суммарное число голов для каждой команды:", Global.table.TotalSumCommandGoals_1());
//        TotalDialog td = new TotalDialog(MainFrame.frame, "Суммарное число голов для каждой команды:", Global.table.TotalSumCommandGoals_1());
        JDialog dialog = new JDialog(MainFrame.frame, "Result one", true);
        dialog.getContentPane().setLayout(new GridLayout());
        dialog.getContentPane().add(scrollPane);

        dialog.setSize(200, 150);

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> {
            dialog.setVisible(false);
        });
        dialog.getContentPane().add(ok);
        dialog.setVisible(true);
    }

    private void showTotal2() {
        MainFrame.MSG.setText("   Итоговый запрос на выборку");
        JOptionPane.showMessageDialog(MainFrame.frame,
                String.format("Count of objects: %5d", Global.table.ObjectsNumber()));
    }

    private void showFilter() {
        String filter = tf.getText();
        if (filter.equals("")) {
            MainFrame.MSG.setText("     Enter text filter");
            return;
        }
        MainFrame.MSG.setText(String.format(
                "   Запрос на выборку: выдать записи с командой, начинающейся с \"%s\"", filter));
        Global.updateJTable(Global.table.filterObjects(filter).getBuilders());
    }

    private void ShowSortByIdAndReadiness() {
        MainFrame.MSG.setText(
                "   Запрос на выборку: выдать все записи таблицы с сортировкой по команде и баллу");
        Global.updateJTable(Global.table.Sort(new CompIdAscReadinessDesc()).getBuilders());
    }

    private void showAll() {
        MainFrame.MSG.setText("   Запрос на выборку: выдать все записи таблицы без сортировки");
        Global.updateJTable(Global.table.getBuilders());
    }

    public void actionPerformed(ActionEvent e) {
        if ("Total1".equals(e.getActionCommand())) showTotal1();
        else if ("Total2".equals(e.getActionCommand())) showTotal2();
        else if ("Filter".equals(e.getActionCommand())) showFilter();
        else if ("Sort2".equals(e.getActionCommand())) ShowSortByIdAndReadiness();
        else showAll();
    }
} 