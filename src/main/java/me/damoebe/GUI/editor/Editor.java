package me.damoebe.GUI.editor;

import com.google.gson.Gson;
import me.damoebe.GUI.editor.Canvas.Layer;
import me.damoebe.GUI.editor.Canvas.PixelCanvas;
import me.damoebe.GUI.CreateMenu;
import me.damoebe.GUI.EditMenu;
import me.damoebe.GUI.Window;
import me.damoebe.GUI.error.ErrorScreen;
import me.damoebe.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Editor implements Window {

    private final JFrame frame;
    private final String projectName;
    private final String direction;
    private PixelCanvas canvas;
    private ToolConfiguration toolConfiguration;

    // editing a file which already exists
    public Editor(String folderPath, String projectName){
        Main.editors.add(this);
        frame = new JFrame();
        this.projectName = projectName;
        this.direction = folderPath;
        this.toolConfiguration = new ToolConfiguration(direction);
        Dimension size = getSize();
        setupFrame(projectName, size.height, size.width);
        loadLayers();

    }

    // creating a new folder
    public Editor(String folderPath, String projectName, int height, int width){
        this.toolConfiguration = new ToolConfiguration();
        this.direction = folderPath + "/" + projectName;
        this.projectName = projectName;
        new File(folderPath + "/" + projectName).mkdir();
        new File(direction + "/settings.json");
        saveSettings();
        frame = new JFrame();
        setupFrame(projectName, height, width);
        new File(direction + "/layers").mkdir();
        saveLayers();
    }

    private void setupFrame(String name, int height, int width){

        frame.setTitle(name + " - Pixelmoebe");
        frame.setIconImage(new ImageIcon(Main.assetPath + "/flower.png").getImage());
        frame.setLocationRelativeTo(null);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveLayers();
                close();
                super.windowClosing(e);
            }
        });

        frame.setSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(), (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH );

        canvas = new PixelCanvas(height, width);

        JPanel toolsPanel = getToolsLayout();
        toolsPanel.setPreferredSize(new Dimension((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - Toolkit.getDefaultToolkit().getScreenSize().getHeight()) / 2), frame.getHeight()));
        JScrollPane scrollTools = new JScrollPane(toolsPanel);

        JPanel layerPanel = new JPanel();
        layerPanel.add(new JLabel("Layer-Management under development!"));
        layerPanel.setPreferredSize(new Dimension((int) ((Toolkit.getDefaultToolkit().getScreenSize().getWidth() - Toolkit.getDefaultToolkit().getScreenSize().getHeight()) / 2), frame.getHeight()));
        JScrollPane scrollLayer = new JScrollPane(layerPanel);

        frame.add(scrollTools, BorderLayout.WEST);
        frame.add(canvas, BorderLayout.CENTER);
        frame.add(scrollLayer, BorderLayout.EAST);

        JMenuBar menuBar = getMenuBar();
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);

        canvas.start();
    }

    private int addedColors = 0;

    // all tools
    private JPanel getToolsLayout(){
        JPanel tools = new JPanel();

        JPanel colorChooser = new JPanel();
        colorChooser.setName("color_chooser");
        colorChooser.setLayout(new FlowLayout(FlowLayout.LEFT));
        colorChooser.setBorder(new LineBorder(Color.GRAY));
        colorChooser.setPreferredSize(new Dimension(450, 200));

        JButton addColor = new JButton(new ImageIcon(Main.assetPath + "/plus.png"));
        addColor.setContentAreaFilled(false);
        addColor.setPreferredSize(new Dimension(40, 40));
        addColor.addActionListener(_ ->{
            Color newColor = JColorChooser.showDialog(this.frame, "Choose a color.", Color.RED);
            this.canvas.setSelectedColor(newColor);

            JButton colorButton = new JButton();
            colorButton.setBackground(newColor);
            colorButton.setPreferredSize(new Dimension(40, 40));
            colorButton.addActionListener(_ -> {
                this.canvas.setSelectedColor(newColor);
            });
            colorChooser.add(colorButton);
            frame.revalidate();
            frame.repaint();
        });
        colorChooser.add(addColor);

        for (Color color : this.toolConfiguration.getColors()){
            JButton colorButton = new JButton();
            colorButton.setBackground(color);
            colorButton.setPreferredSize(new Dimension(40, 40));
            colorButton.addActionListener(_ -> {
                this.canvas.setSelectedColor(color);
            });
            colorChooser.add(colorButton);
        }


        JPanel toolChooser = new JPanel();
        toolChooser.setLayout(new GridBagLayout());
        toolChooser.setBorder(new LineBorder(Color.GRAY));
        toolChooser.setPreferredSize(new Dimension(450, 200));

        JButton pen = new JButton(new ImageIcon(Main.assetPath + "/pen.png"));
        pen.setContentAreaFilled(false);
        pen.setPreferredSize(new Dimension(40, 40));
        pen.addActionListener(_ -> {
            this.toolConfiguration.selectTool(Tool.PEN);
            this.canvas.setSelectedTool(Tool.PEN);
        });

        JButton rubber = new JButton(new ImageIcon(Main.assetPath + "/rubber.png"));
        rubber.setContentAreaFilled(false);
        rubber.setPreferredSize(new Dimension(40, 40));
        rubber.addActionListener(_ -> {
            this.toolConfiguration.selectTool(Tool.RUBBER);
            this.canvas.setSelectedTool(Tool.RUBBER);
        });

        JButton colorPicker = new JButton(new ImageIcon(Main.assetPath + "/color-picker.png"));
        colorPicker.setContentAreaFilled(false);
        colorPicker.setPreferredSize(new Dimension(40, 40));
        colorPicker.addActionListener(_ -> {
            this.toolConfiguration.selectTool(Tool.COLOR_PICKER);
            this.canvas.setSelectedTool(Tool.COLOR_PICKER);
        });

        toolChooser.add(pen, JLabel.CENTER);
        toolChooser.add(rubber, JLabel.CENTER);
        toolChooser.add(colorPicker, JLabel.CENTER);

        tools.add(colorChooser);
        tools.add(toolChooser);
        return tools;
    }

    private void saveSettings(){
        Gson gson = new Gson();
    }

    // structure of JMenuBar
    private JMenuBar getMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenuItem save = new JMenuItem("Save Project");
        save.addActionListener(_ -> {
            saveLayers();
            JOptionPane.showMessageDialog(this.frame, "Saved all layers!");
        });

        JMenuItem newProject = new JMenuItem("New Project");
        newProject.addActionListener(_ -> {
            new CreateMenu();
        });

        JMenuItem open = new JMenuItem("Open Project");
        open.addActionListener(_ -> {
            new EditMenu();
        });

        JMenuItem close = new JMenuItem("Close Project");
        close.addActionListener(_ -> {
            close();
        });

        JMenuItem exit = new JMenuItem("Exit");
        exit.setBorder(new LineBorder(Color.GRAY));
        exit.addActionListener(_ -> {
            close();
            System.exit(0);
        });

        file.add(newProject);
        file.add(open);
        file.add(save);
        file.add(close);
        file.add(exit);

        JMenu edit = new JMenu("Edit");
        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(_ -> {
            canvas.undo();
        });
        JMenuItem redo = new JMenuItem("Redo");
        redo.addActionListener(_ -> {
            canvas.redo();
        });

        edit.add(undo);
        edit.add(redo);

        JMenu export = new JMenu("Export");

        JMenuItem exportProject = new JMenuItem("Export this project to Downloads");
        exportProject.addActionListener(_ ->{
            try {
                ImageIO.write(canvas.getFinalImage(), "png", new File(System.getProperty("user.home") + "/Downloads/" + projectName + ".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            JOptionPane.showMessageDialog(this.frame, "Successfully exported to Downloads-Folder!");
        });

        JMenuItem exportLayer = new JMenuItem("Export a layer to Downloads...");

        export.add(exportLayer);
        export.add(exportProject);

        JMenu importFile = new JMenu("Import");

        JMenu help = new JMenu("Help");
        help.addActionListener(_ ->{
            JOptionPane.showMessageDialog(frame, "This Section is currently under development. Please read the READ_ME.txt file for an introduction.");
        });

        menuBar.add(file);
        menuBar.add(edit);
        menuBar.add(export);
        menuBar.add(importFile);
        menuBar.add(help);

        return menuBar;
    }

    private void saveLayers(){
        int number = 0;
        for (Layer layer : canvas.getLayers()){
            try {
                ImageIO.write(layer, "png", new File(direction + "/layers/layer" + number + ".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            number++;
        }
    }

    private void loadLayers(){
        if (!new File(direction).isDirectory()){
            return ;
        }
        List<Layer> layers = new ArrayList<>();
        for (File file : Objects.requireNonNull(new File(direction + "/layers").listFiles())){
            try {
                BufferedImage image =  ImageIO.read(new File(direction + "/layers/" + file.getName()));
                layers.add(new Layer(image.getWidth(), image.getHeight(), file.getName(), image));
            } catch (IOException e) {
                new ErrorScreen(e.getMessage());
            }
        }
        canvas.setLayers(layers);
        canvas.setSelectedLayer(1);
    }

    private Dimension getSize(){
        try {
            BufferedImage hello_image = ImageIO.read(new File(direction + "/layers/layer0.png"));
            return new Dimension(hello_image.getWidth(), hello_image.getHeight());
        } catch (IOException e) {
            new ErrorScreen(e.getMessage());
            return null;
        }
    }

    @Override
    public void close() {
        this.frame.setVisible(false);
        Main.closeEditor(this);
    }

    @Override
    public void open() {
        this.frame.setVisible(true);
    }
}
