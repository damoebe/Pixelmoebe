package me.damoebe.GUI;

import me.damoebe.GUI.editor.Editor;
import me.damoebe.Main;

import javax.swing.*;
import java.io.File;

public class EditMenu implements Window {
    private JFrame frame;

    public EditMenu() {
        frame = new JFrame();
        frame.setTitle("Edit File (Choose Project-Folder)");
        frame.setIconImage(new ImageIcon(Main.assetPath + "/flower.png").getImage());
        frame.setResizable(false);

        addDecoration();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addDecoration(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "/Projects"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.addActionListener(event -> {
            String command = event.getActionCommand();
            if (command.equalsIgnoreCase("ApproveSelection")){
                close();
                new Editor(fileChooser.getSelectedFile().getPath(), fileChooser.getSelectedFile().getName());
            }else if (command.equalsIgnoreCase("CancelSelection")){
                close();
                new MainMenu();
            }
        });
        frame.add(fileChooser);
        frame.setSize(fileChooser.getWidth(), fileChooser.getHeight());
        frame.pack();
    }

    public void close(){
        frame.setVisible(false);

    }

    @Override
    public void open() {
        frame.setVisible(true);
    }

}
