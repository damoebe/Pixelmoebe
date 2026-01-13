package me.damoebe.GUI.error;

import me.damoebe.GUI.Window;
import me.damoebe.Main;

import javax.swing.*;

public class ErrorScreen implements Window {
    private final JFrame frame;

    public ErrorScreen(String exception){
        frame = new JFrame();
        frame.add(new JLabel("Something went wrong, please try again."));
        frame.add(new JLabel(exception));
        frame.setResizable(false);
        frame.setIconImage(new ImageIcon(Main.assetPath + "/flower.png").getImage());

        frame.pack();

        frame.setSize(300, 100);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void close() {
        frame.setVisible(false);
    }

    @Override
    public void open() {
        frame.setVisible(true);
    }
}
