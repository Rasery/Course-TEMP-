import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class MainFrame implements ActionListener {

    String directoryName = "C:/";
    String fileName = "";
    File curFile;
    static JFrame frame;
    JPanel pMain;
    JTable VIS_TABLE;
    EditPanel editPanel;
    ViewPanel viewPanel;
    JLabel jFileName;
    static JLabel MSG;
    java.util.List<String> LINES;
    static String helpArr1 = "\n     The system was developed by a student of the group IVT/b-19-2-o\n " +
            "    Klinikov Rostislav Alexandrovich:\n" + "     SevSU - 2020.\n";
    static String helpArr2 = "\n     The information system stores and\n" +
            "     dynamics of construction data processing\n" + "     in the city.\n";

    public MainFrame() {

        Global.table = new BuilderGroup("Список записей о результатах");
        Global.builders = new ArrayList<>();
        Global.tableModel = new BuilderTableModel(Global.builders);
        VIS_TABLE = new JTable(Global.tableModel);
        JScrollPane scrTable = new JScrollPane(VIS_TABLE);
        viewPanel = new ViewPanel();
        editPanel = new EditPanel();

        VIS_TABLE.setPreferredScrollableViewportSize(new Dimension(250, 100));
        int WinSizeG = 1366;
        int WinSizeV = 768;

        frame = new JFrame("Data Storage and Processing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container myC = frame.getContentPane();
        myC.setLayout(new BorderLayout(5, 5));

        MenuIS s = new MenuIS();
        frame.setJMenuBar(s.menuBar);

        s.newFile.addActionListener(this);
        s.openFile.addActionListener(this);
        s.saveFile.addActionListener(this);
        s.saveAsFile.addActionListener(this);
        s.closeFile.addActionListener(this);
        s.startEdit.addActionListener(this);
        s.stopEdit.addActionListener(this);
        s.help1.addActionListener(this);
        s.help2.addActionListener(this);

        pMain = new JPanel();
        pMain.setLayout(new BorderLayout());
        pMain.add(scrTable, BorderLayout.CENTER);
        pMain.add(editPanel, BorderLayout.SOUTH);

        jFileName = new JLabel("", JLabel.CENTER);
        pMain.add(jFileName, BorderLayout.NORTH);

        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        JScrollPane spMain = new JScrollPane(pMain, v, h);
        myC.add(spMain, BorderLayout.CENTER);

        myC.add(new JLabel("Data on accounting of construction dynamics in the city", JLabel.CENTER), BorderLayout.NORTH);
        MSG = new JLabel("   Course project on discipline \"Programming\". SevSU - 2020");
        MSG.setFont(new Font("Serif", Font.BOLD, 14));
        myC.add(MSG, BorderLayout.SOUTH);

        frame.setSize(WinSizeG, WinSizeV);
        frame.setLocation(10, 10);
        frame.setVisible(true);
    }

    public void NewFile() {

        Global.table.getBuilders().clear();
        Global.builders.clear();
        Global.tableModel.fireTableDataChanged();

        pMain.remove(viewPanel);
        pMain.add(editPanel, BorderLayout.SOUTH);

        MSG.setText("   Creating a Database");
    }

    public void setFileFilter(JFileChooser fch) {

        TextFilter text_filter = new TextFilter();
        fch.setFileFilter(text_filter);
    }

    public void OpenFile() {

        int rez;
        int n;

        MSG.setText("   Open a file");
        JFileChooser fch = new JFileChooser(directoryName);
        fch.setDialogTitle("Open a file");
        setFileFilter(fch);

        rez = fch.showDialog(frame, "Open");
        if (rez == JFileChooser.APPROVE_OPTION) {
            curFile = fch.getSelectedFile();
            fileName = curFile.getAbsolutePath();
            n = fileName.lastIndexOf('\\');
            directoryName = fileName.substring(0, n + 1);

            try {
                LINES = IO.InpLines(fileName);
                if (LINES != null) MSG.setText("   Successful data entry");
                else MSG.setText("   Data Entry Error");
            } catch (Exception e) {
                MSG.setText("   Data Entry Error");
            }

            java.util.List<Builder> builder = Transfer.StringsToBuilders(LINES);
            Global.table.getBuilders().clear();

            for (Builder build : builder) Global.table.AddBuilder(build);
            Global.updateJTable(Global.table.getBuilders());

            pMain.remove(editPanel);
            pMain.add(viewPanel, BorderLayout.SOUTH);
            jFileName.setText(fileName);
        }
    }

    private void SaveDialog() {

        int rez;
        int n;

        JFileChooser fch = new JFileChooser(directoryName);
        fch.setDialogTitle("Save File");
        setFileFilter(fch);

        rez = fch.showDialog(frame, "Save");
        if (rez == JFileChooser.APPROVE_OPTION) {
            curFile = fch.getSelectedFile();
            fileName = curFile.getAbsolutePath();
            n = fileName.lastIndexOf('\\');
            directoryName = fileName.substring(0, n + 1);
        }
    }

    public void SaveFile(boolean fs) {

        String old_file_name = fileName;
        MSG.setText("   Save File");

        if (fs) SaveDialog();
        else if (fileName.equals("")) SaveDialog();
        if (curFile == null) {
            MSG.setText(
                    "   No file selected to save");
            return;
        }

        if ((!curFile.exists()) || fileName.equals(old_file_name)) {
            LINES = Transfer.BuilderToString(Global.table.getBuilders());

            try {
                boolean f = IO.OutLines(fileName, LINES);
                if (f) {
                    MSG.setText("    Data saved successfully");
                    jFileName.setText(fileName);
                } else MSG.setText("    Error saving data");
            } catch (Exception e) {
                MSG.setText("    Error saving data");
            }

        } else {
            JOptionPane.showMessageDialog(frame, "Error: file with given name " + fileName + " doesn't exist");
            fileName = old_file_name;
        }
        MSG.setText("   Course project on discipline \"Programming\". SevSU - 2020");
    }

    public void CloseWindow() {
        frame.dispose();
    }

    public void StartEdit() {

        pMain.remove(viewPanel);
        pMain.add(editPanel, BorderLayout.SOUTH);
        MSG.setText("   Editing a Database");
    }

    public void StopEdit() {

        pMain.remove(editPanel);
        pMain.add(viewPanel, BorderLayout.SOUTH);
        MSG.setText("   View Database");
    }

    public void actionPerformed(ActionEvent e) {

        if ("New".equals(e.getActionCommand())) NewFile();
        else if ("Open".equals(e.getActionCommand())) OpenFile();
        else if ("Save".equals(e.getActionCommand())) SaveFile(false);
        else if ("Save as".equals(e.getActionCommand())) SaveFile(true);
        else if ("Close".equals(e.getActionCommand())) CloseWindow();
        else if ("Start edit".equals(e.getActionCommand())) StartEdit();
        else if ("Stop edit".equals(e.getActionCommand())) StopEdit();
        else if ("About".equals(e.getActionCommand())) {
            HelpDialog helpMSG = new HelpDialog(MainFrame.frame, "About", helpArr1, "динозавр.gif");
            helpMSG.setSize(375, 250);
            helpMSG.setVisible(true);
        } else if ("IS description".equals(e.getActionCommand())) {
            HelpDialog helpMSG1 = new HelpDialog(MainFrame.frame, "IS description", helpArr2, "лошадь.gif");
            helpMSG1.setVisible(true);
        }

    }

}