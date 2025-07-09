package me.damoebe.GUI;

import me.damoebe.Main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MainMenu implements Window{
    private final JFrame frame;
    private int width = 600;
    private int height = 400;

    public MainMenu(){
        frame = new JFrame();

        frame.setUndecorated(true);
        frame.setTitle("Pixelmoebe");
        frame.setIconImage(new ImageIcon(System.getProperty("user.dir") + "/src/main/java/me/damoebe/assets/flower.png").getImage());

        addDecoration();
        frame.pack();

        frame.setSize(width, height);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private void addDecoration(){

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BorderLayout());
        optionsPanel.setBackground(Color.WHITE);
        optionsPanel.setBorder(new LineBorder(Color.GRAY));
        optionsPanel.setPreferredSize(new Dimension(350, height));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setPreferredSize(new Dimension(350, 150));

        JPanel imagePanel = new JPanel();
        JLabel image = new JLabel(new ImageIcon(new ImageIcon(System.getProperty("user.dir") + "/src/main/java/me" +
                "/damoebe/assets/flower.png").getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH)));

        JButton create = new JButton("New File");
        create.addActionListener(_ -> {
            close();
            new CreateMenu();
        });
        create.setVerticalAlignment(JLabel.CENTER);

        JButton edit = new JButton("Edit File");
        edit.addActionListener(_ ->{
            close();
            new EditMenu();
        });
        edit.setVerticalAlignment(JLabel.CENTER);

        JButton exit = new JButton("Exit");
        exit.setBackground(Color.RED);
        exit.setForeground(Color.WHITE);
        exit.addActionListener(_ ->{
            close();
            System.exit(0);
        });
        exit.setVerticalAlignment(JLabel.CENTER);

        JLabel title = new JLabel("Pixelmoebe");
        title.setFont(new Font("Bauhaus 93", Font.PLAIN, 50));
        title.setHorizontalAlignment(JLabel.CENTER);


        JLabel description = new JLabel("<html><br>Pixelmoebe is a simple pixel-art-editor build <br>from scratch" +
                " in Java with the goal to understand <br>the whole Java-Application development.<br><br>This Programm " +
                "was coded by Damoebe for a <br>School-Project.<br><br>To get started press 'New File' (To create a <br>new" +
                " file) or 'Edit File' (To edit an already existing <br>File).<br></html>");
        description.setOpaque(true);
        description.setVerticalAlignment(JLabel.TOP);
        description.setBackground(Color.WHITE);
        description.setHorizontalAlignment(JLabel.CENTER);

        buttonPanel.add(create);
        buttonPanel.add(edit);
        buttonPanel.add(exit);

        optionsPanel.add(title, BorderLayout.NORTH);
        optionsPanel.add(description, BorderLayout.CENTER);
        optionsPanel.add(buttonPanel, BorderLayout.SOUTH);

        imagePanel.add(image);

        frame.add(optionsPanel, BorderLayout.WEST);
        frame.add(imagePanel, BorderLayout.EAST);
    }
    @Override
    public void close(){
        frame.setVisible(false);
    }
    @Override
    public void open(){frame.setVisible(true);}
}
