import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class MenuIS {
    //Главное меню
//подпункты меню
    JMenuItem newFile;
    JMenuItem openFile;
    JMenuItem saveFile;
    JMenuItem saveAsFile;
    JMenuItem closeFile;
    JMenuItem startEdit;
    JMenuItem stopEdit;
    JMenuItem help1;
    JMenuItem help2;

    JMenu m1, m2, m3;//пункты меню
    JMenuBar mb1;//панель меню


    public MenuIS() {

        m1 = new JMenu("File");

        newFile = new JMenuItem("New");
        m1.add(newFile);

        openFile = new JMenuItem("Open");
        m1.add(openFile);

        saveFile = new JMenuItem("Save");
        m1.add(saveFile);

        saveAsFile = new JMenuItem("Save as");
        m1.add(saveAsFile);

        closeFile = new JMenuItem("Close");
        m1.add(closeFile);

        m2 = new JMenu("Edit");

        startEdit = new JMenuItem("Start edit");
        m2.add(startEdit);

        stopEdit = new JMenuItem("Stop edit");
        m2.add(stopEdit);

        m3 = new JMenu("Reference");

        help1 = new JMenuItem("About");
        m3.add(help1);

        help2 = new JMenuItem("IS description");
        m3.add(help2);

        mb1 = new JMenuBar();

        mb1.add(m1);
        mb1.add(m2);
        mb1.add(m3);
    }
}
