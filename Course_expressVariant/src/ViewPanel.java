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

        Box vBox = Box.createVerticalBox();
        for(var el : Global.table.MaxReadinessForEveryBuilder()){
            vBox.add(new JLabel(el));
        }

        JDialog dialog = new JDialog(MainFrame.frame, "Result one", true);
        dialog.getContentPane().setLayout(new BorderLayout());
        dialog.getContentPane().add(vBox, BorderLayout.CENTER);
        dialog.setBounds(300, 300, 200, 150);

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> {
            dialog.setVisible(false);
        });

        JPanel panel = new JPanel();

        vBox.add(panel, BorderLayout.SOUTH);
        panel.add(ok);
        dialog.setVisible(true);
    }

    private void showTotal2() {

        MainFrame.MSG.setText("   Final Selection Request");
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
                "   Fetch request: check out records with object beginning with \"%s\"", filter));
        Global.updateJTable(Global.table.filterObjects(filter).getBuilders());
    }

    private void ShowSortByIdAndReadiness() {
        MainFrame.MSG.setText(
                "   Fetch query: check out all table entries sorted by id and readiness");
        Global.updateJTable(Global.table.Sort(new CompIdAscReadinessDesc()).getBuilders());
    }

    private void showAll() {
        MainFrame.MSG.setText("   Fetch query: check out all table entries without sorting");
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