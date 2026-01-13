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

        frame.setTitle("Pixelmoebe");
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon(Main.assetPath + "/flower.png").getImage());

        addDecoration();
        frame.pack();

        frame.setSize(width, height);

        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        if (Main.missing_files) {
            JOptionPane.showMessageDialog(this.frame, "There are files missing! Errorcode: 67");
        }
    }

    private void addDecoration(){
        frame.setUndecorated(true);
        Color background = new Color(59, 61, 59);
        Color background2 = new Color(57, 59, 57);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BorderLayout());
        optionsPanel.setBorder(new LineBorder(Color.DARK_GRAY));
        optionsPanel.setPreferredSize(new Dimension(350, height));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setBackground(background);
        buttonPanel.setPreferredSize(new Dimension(350, 150));

        JPanel imagePanel = new JPanel();
        JLabel image = new JLabel(new ImageIcon(new ImageIcon(Main.assetPath + "/flower.png")
                .getImage().getScaledInstance(250, 400, Image.SCALE_SMOOTH)));

        JButton create = new JButton("New File");
        create.setBackground(background2);
        create.setForeground(Color.WHITE);
        create.addActionListener(_ -> {
            close();
            new CreateMenu();
        });
        create.setVerticalAlignment(JLabel.CENTER);

        JButton edit = new JButton("Edit File");
        edit.setBackground(background2);
        edit.setForeground(Color.WHITE);
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
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Bauhaus 93", Font.PLAIN, 50));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setBorder(new LineBorder(Color.WHITE));


        JLabel description = new JLabel("<html>Pixelmoebe is a simple pixel-art-editor build <br>from scratch" +
                " in Java with the goal to understand the whole Java-Application development.<br><br>This Programm " +
                "was coded by Damoebe for a School-Project. To get started press 'New File' (To create a new" +
                " file) or 'Edit File' (To edit an already existing File).</html>");
        description.setOpaque(true);
        description.setBackground(background2);
        description.setForeground(Color.WHITE);
        description.setBorder(new LineBorder(Color.WHITE));
        description.setVerticalAlignment(JLabel.TOP);
        description.setHorizontalAlignment(JLabel.CENTER);

        buttonPanel.add(create);
        buttonPanel.add(edit);
        buttonPanel.add(exit);

        optionsPanel.add(title, BorderLayout.NORTH);
        optionsPanel.add(description, BorderLayout.CENTER);
        optionsPanel.add(buttonPanel, BorderLayout.SOUTH);
        optionsPanel.setBackground(background);

        imagePanel.add(image);
        imagePanel.setBackground(background);

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
