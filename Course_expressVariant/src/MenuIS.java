import javax.swing.*;

public class MenuIS {

    JMenuItem newFile;
    JMenuItem openFile;
    JMenuItem saveFile;
    JMenuItem saveAsFile;
    JMenuItem closeFile;
    JMenuItem startEdit;
    JMenuItem stopEdit;
    JMenuItem help1;
    JMenuItem help2;

    JMenu menuFile, menuEdit, menuReference;
    JMenuBar menuBar;


    public MenuIS() {

        menuFile = new JMenu("File");

        newFile = new JMenuItem("New");
        menuFile.add(newFile);

        openFile = new JMenuItem("Open");
        menuFile.add(openFile);

        saveFile = new JMenuItem("Save");
        menuFile.add(saveFile);

        saveAsFile = new JMenuItem("Save as");
        menuFile.add(saveAsFile);

        closeFile = new JMenuItem("Close");
        menuFile.add(closeFile);

        menuEdit = new JMenu("Edit");

        startEdit = new JMenuItem("Start edit");
        menuEdit.add(startEdit);

        stopEdit = new JMenuItem("Stop edit");
        menuEdit.add(stopEdit);

        menuReference = new JMenu("Reference");

        help1 = new JMenuItem("About");
        menuReference.add(help1);

        help2 = new JMenuItem("IS description");
        menuReference.add(help2);

        menuBar = new JMenuBar();

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuReference);
    }
}
