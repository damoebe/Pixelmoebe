package me.damoebe.GUI;

import me.damoebe.GUI.editor.Editor;
import me.damoebe.GUI.error.ErrorScreen;
import me.damoebe.GUI.error.ErrorType;
import me.damoebe.Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.util.Objects;

public class CreateMenu implements Window{
    private final JFrame frame;

    public CreateMenu(){
        frame = new JFrame();

        frame.setTitle("Create New File");
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon(Main.assetPath + "/flower.png").getImage());
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        addDecoration();

        frame.pack();

        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void addDecoration(){
        JPanel namePanel = new JPanel();
        JPanel sizePanel = new JPanel();
        JPanel savePanel = new JPanel();
        JPanel navigatePanel = new JPanel();

        JLabel nameLabel = new JLabel("Project Name:");

        JTextField name = new JTextField("ProjectName", 15);
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        name.setBorder(border);

        JLabel heightLabel = new JLabel("Height:");

        JTextField height = new JTextField("100", 4);
        height.setBorder(border);

        JLabel widthLabel = new JLabel("Width:");

        JTextField width = new JTextField("100", 4);
        width.setBorder(border);

        JLabel directionLabel = new JLabel("Path:");

        JTextField direction = new JTextField(System.getProperty("user.dir") + "/Projects", 15);
        width.setBorder(border);

        JButton back = new JButton("Back");
        back.addActionListener(_ -> {
            new MainMenu();
            close();
        });

        JLabel errorMessage = new JLabel("");
        errorMessage.setForeground(Color.RED);

        JButton create = new JButton("Create");
        create.addActionListener(_ -> {
            if (getErrorType(name.getText(), height.getText(), width.getText(), direction.getText()).equals(ErrorType.NONE)) {

                new Editor(direction.getText(), name.getText(), Integer.parseInt(height.getText()), Integer.parseInt(width.getText()));

                close();
            }else{
                errorMessage.setText(getErrorType(name.getText(), height.getText(), width.getText(), direction.getText()).getMessage());
            }
        });

        navigatePanel.add(back);
        navigatePanel.add(create);
        savePanel.add(directionLabel);
        savePanel.add(direction);
        namePanel.add(nameLabel);
        namePanel.add(name);
        sizePanel.add(heightLabel);
        sizePanel.add(height);
        sizePanel.add(widthLabel);
        sizePanel.add(width);

        frame.add(namePanel);
        frame.add(sizePanel);
        frame.add(savePanel);
        frame.add(errorMessage);
        frame.add(navigatePanel);
    }
    @Override
    public void close(){
        frame.setVisible(false);
    }
    @Override
    public void open(){frame.setVisible(true);}

    private ErrorType getErrorType(String name, String height, String width, String path){
        try{
            Integer.parseInt(width);
            Integer.parseInt(height);
            if (Objects.equals(name, "")){
                return ErrorType.MISSING_PARAMETERS;
            }
        }catch (Exception e){
            return ErrorType.MISSING_PARAMETERS;
        }
        if (Files.exists(new File(path).toPath())){
            if (!Files.exists(new File(path + "/" + name).toPath())){
                if (((Integer.parseInt(width) >= 10) && (Integer.parseInt(width) <= 200)) && ((Integer.parseInt(height) >= 10) && (Integer.parseInt(height) <= 200))) {
                    return ErrorType.NONE;
                } else {
                    return ErrorType.INVALID_SIZE;
                }
            }else {
                return ErrorType.NAME_ALREADY_USED;
            }
        }else{
            return ErrorType.PATH_DOES_NOT_EXIST;
        }
    }
}
