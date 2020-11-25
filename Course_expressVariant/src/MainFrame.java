import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class MainFrame implements ActionListener {
    //Главная форма программы
    String directoryName = "C:/";//имя директории, в которой расположен
    // последний загруженный файл БД
    String fileName = "";// имя последнего загруженного файла базы данных
    File curFile; //текущий файловый объект
    static JFrame frame; //главный фрейм
    JPanel pMain; //главная панель фрейма
    JTable VIS_TABLE;
    EditPanel editPanel; //панель инструментов редактирования
    ViewPanel viewPanel; //панель инструментов просмотра
    JLabel jFileName; //метка для вывода имени файла таблицы
    static JLabel MSG;//сообщение в нижней части окна
    java.util.List<String> LINES; //список для ввода-вывода строк
    //Текстовые массивы, которые содержат сообщения, выдаваемые в окнах меню "Справка"
    static String helpArr1 = "\n     The system was developed by a student of the group IVT/b-19-2-o\n " +
            "    Klinikov Rostislav Alexandrovich:\n" +
            "     SevSU - 2020.\n";

    static String helpArr2 = "\n     The information system stores and\n" +
            "     dynamics of construction data processing\n" +
            "     in the city.\n";

    public MainFrame() {
        //Создаем основные структуры --------------------------------------------
        //1. Внутреннее представление основной таблицы
        Global.table = new BuilderGroup("Список записей о результатах");
        //2. Список для вывода в JTable
        Global.builders = new ArrayList<Builder>();
        //2. Визуальный компонент для таблицы
        Global.tableModel = new BuilderTableModel(Global.builders);
        VIS_TABLE = new JTable(Global.tableModel);
        //Создаем панель прокрутки и включаем в ее состав  таблицу
        JScrollPane scrtable = new JScrollPane(VIS_TABLE);
        //Устаналиваем размеры прокручиваемой области
        VIS_TABLE.setPreferredScrollableViewportSize(
                new Dimension(250, 100));
        //3. Панель просмотра записей
        viewPanel = new ViewPanel();
        //4. Панель редактирования записей
        editPanel = new EditPanel();
        //---------------------------------------------------------------------
        int WinSizeG = 1366;//начальный размер окна по горизонтали
        int WinSizeV = 768;//начальный размер окна по вертикали
        frame = new JFrame("Data Storage and Processing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container myC = frame.getContentPane();
        myC.setLayout(new BorderLayout(5, 5));//должна быть такая компоновка, чтобы отображались
        //полосы прокрутки
        MenuIS s = new MenuIS();
        frame.setJMenuBar(s.mb1);//добавление меню в окно
        //Организация прослушивания пунктов меню
        s.newFile.addActionListener(this);
        s.openFile.addActionListener(this);
        s.saveFile.addActionListener(this);
        s.saveAsFile.addActionListener(this);
        s.closeFile.addActionListener(this);
        s.startEdit.addActionListener(this);
        s.stopEdit.addActionListener(this);
        s.help1.addActionListener(this);
        s.help2.addActionListener(this);
        //начальная сборка главной панели
        pMain = new JPanel(); //главная панель проекта
        pMain.setLayout(new BorderLayout());
        pMain.add(scrtable, BorderLayout.CENTER);
        pMain.add(editPanel, BorderLayout.SOUTH);
        jFileName = new JLabel("", JLabel.CENTER);
        pMain.add(jFileName, BorderLayout.NORTH);
        // Скроллинг для главной панели
        // Полосы прокрутки должны появляться в случае необходимости,
        // т.е. если мы в процессе выполнения программы
        // изменим размер фрейма так, что он станет меньше предпочтительного
        // размера главной панели (по умолчанию предполагает, что все элементы помещаются целиком)
        int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
        int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
        JScrollPane spMain = new JScrollPane(pMain, v, h);//панель прокрутки для pMain
        myC.add(spMain, BorderLayout.CENTER);
        // Метка с названием таблицы
        myC.add(new JLabel("Data on accounting of construction dynamics in the city", JLabel.CENTER),
                BorderLayout.NORTH);
        // Метка для сообщений системы
        MSG = new JLabel(
                "   Course project on discipline \"Programming\". SevSU - 2020");
        MSG.setFont(new Font("Serif", Font.BOLD, 14));
        myC.add(MSG, BorderLayout.SOUTH);
        // устанавливаем размеры и локализацию фрейма
        frame.setSize(WinSizeG, WinSizeV);
        frame.setLocation(10, 10);
        frame.setVisible(true);
    }
    //**********методы для пункта меню "Файл"********************************************

    public void NewFile() {
        Global.table.getBuilders().clear();
        Global.builders.clear();
        Global.tableModel.fireTableDataChanged();
        pMain.remove(viewPanel);
        pMain.add(editPanel, BorderLayout.SOUTH);
        MSG.setText(
                "   Creating a Database");
    }

    public void setFileFilter(JFileChooser fch) {//установка фильтра для диалога выбора файла
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
        if (rez == fch.APPROVE_OPTION) {
            curFile = fch.getSelectedFile();
            fileName = curFile.getAbsolutePath();
            n = fileName.lastIndexOf('\\');
            directoryName = fileName.substring(0, n + 1);
            try {
                LINES = IO.inpLines(fileName);
                if (LINES != null) MSG.setText("   Successful data entry");
                else MSG.setText("   Data Entry Error");
            } catch (Exception e) {
                MSG.setText("   Data Entry Error");
            } finally {
            }
            java.util.List<Builder> builder = Transfer.StringsToBuilders(LINES);
            Global.table.getBuilders().clear();
            for (Builder build : builder) Global.table.AddBuilder(build);//добавятся только уникальные
            Global.updateJTable(Global.table.getBuilders());
            pMain.remove(editPanel);
            pMain.add(viewPanel, BorderLayout.SOUTH);
            jFileName.setText(fileName);
        }
    }

    private void SaveDialog() {//открывает окно диалога для сохранения файла
        int rez;
        int n;
        JFileChooser fch = new JFileChooser(directoryName);
        fch.setDialogTitle("Save File");
        setFileFilter(fch);
        rez = fch.showDialog((Component) frame, "Save");
        if (rez == fch.APPROVE_OPTION) {
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
        else if (fileName == "") SaveDialog();
        //если файл при первом запуске JFileChooser не был выбран
        //т.е. была нажата кнопка Close:
        if (curFile == null) {
            MSG.setText(
                    "   No file selected to save");
            return;
        }
        if ((!curFile.exists()) || fileName.equals(old_file_name)) {
            LINES = Transfer.BuilderToString(Global.table.getBuilders());
            try {
                boolean f = IO.outpLines(fileName, LINES);
                if (f) {
                    MSG.setText("    Data saved successfully");
                    jFileName.setText(fileName);
                } else MSG.setText("    Error saving data");
            } catch (Exception e) {
                MSG.setText("    Error saving data");
            } finally {
            }
        } else {
            JOptionPane.showMessageDialog(
                    frame, "Error: file with given name " + fileName + " doesn't exist");
            fileName = old_file_name;
        }
        MSG.setText(
                "   Course project on discipline \"Programming\". SevSU - 2020");
    }

    public void CloseWindow() {
        frame.dispose();
    }

//********************методы для пункта меню "Редактирование"*************************************************

    public void StartEdit() {
        pMain.remove(viewPanel);
        pMain.add(editPanel, BorderLayout.SOUTH);
        MSG.setText(
                "   Editing a Database");
    }

    public void StopEdit() {
        pMain.remove(editPanel);
        pMain.add(viewPanel, BorderLayout.SOUTH);
        MSG.setText(
                "   View Database");
    }

    public void actionPerformed(ActionEvent e) {
        String s;
        int l;
        char ch;
        boolean f;
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
            HelpDialog helpMSG1 = new HelpDialog(MainFrame.frame, "IS description",
                    helpArr2, "лошадь.gif");
            helpMSG1.setVisible(true);
        }

    }

}