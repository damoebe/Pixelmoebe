package me.damoebe.GUI.editor.Canvas;

import me.damoebe.GUI.editor.Tool;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;


// outdated!

public class PixelCanvas extends Canvas implements MouseListener, MouseMotionListener, Runnable {

    private final int width;
    private final int height;
    private int pixelSize;
    private Color selectedColor = Color.GREEN;
    private Tool selectedTool = Tool.PEN;

    private List<Version> versions = new ArrayList<>();
    private Version oldVersion;
    private int selectedLayerIndex;
    private int selectedVersionIndex;
    private boolean running = false;

    public PixelCanvas(int height, int width) {

        this.height = height;
        this.width = width;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        Layer background = new Layer(width, height, "Background");
        Color color = Color.GRAY;
        for (int x = 0; x != width; x++){
            color = getOtherColor(color);
            for (int y = 0; y != height; y++){
                color = getOtherColor(color);
                background.setRGB(x, y, color.getRGB());
            }
        }

        this.versions.add(new Version(List.of(background, getEmptyLayer(1))));
        this.selectedLayerIndex = 1;
    }

    public Layer getEmptyLayer(int number){
        Layer layer = new Layer(width, height, "layer" + number);

        // empty
        for (int x = 0; x != width; x++){
            for (int y = 0; y != height; y++){
                layer.setRGB(x, y, Color.decode("#00FFFFFF").getRGB());
            }
        }

        return layer;
    }

    public void start() {
        this.createBufferStrategy(2);
        running = true;
        new Thread(this).start();
    }

    @Override
    public void run() {
        BufferStrategy bs = getBufferStrategy();

        while (running) {
            Graphics g = bs.getDrawGraphics();

            draw(g);

            g.dispose();
            bs.show();

            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw(Graphics g) {
        if (pixelSize == 0) {
            pixelSize = getHeight() / height;
        }

        g.drawImage(getCombinedImage(new ArrayList<>()), 0, 0, width * pixelSize, height * pixelSize, null);
    }

    private BufferedImage getCombinedImage(List<Layer> hiddenLayers){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x != width; x++){

            for (int y = 0; y != height; y++){

                int pixelRGB = Color.decode("#00FFFFFF").getRGB();

                for (Layer layer : versions.get(selectedVersionIndex).getLayers()){
                    if (layer.isVisible() && !hiddenLayers.contains(layer)) {
                        if (layer.getRGB(x, y) != Color.decode("#00FFFFFF").getRGB()) {
                            pixelRGB = layer.getRGB(x, y);
                        }else{
                            if (!hiddenLayers.isEmpty()) {
                                pixelRGB = 0x00000000;
                            }
                        }
                    }
                }

                bufferedImage.setRGB(x, y, pixelRGB);

            }
        }
        return bufferedImage;
    }

    public BufferedImage getFinalImage(){
        return getCombinedImage(List.of(versions.get(selectedVersionIndex).getLayers().getFirst()));
    }

    // for background pattern
    private Color getOtherColor(Color color) {
        return color.equals(Color.GRAY) ? Color.WHITE : Color.GRAY;
    }

    private void setPixel(int mouseX, int mouseY) {
        int x = mouseX / pixelSize;
        int y = mouseY / pixelSize;

        if (x >= width || y >= height || x < 0 || y < 0) return;

        if (selectedTool.equals(Tool.PEN)) {
            versions.get(selectedVersionIndex).getLayers().get(selectedLayerIndex).setRGB(x, y, selectedColor.getRGB());
        }else if (selectedTool.equals(Tool.RUBBER)){
            versions.get(selectedVersionIndex).getLayers().get(selectedLayerIndex).setRGB(x, y, Color.decode("#00FFFFFF").getRGB());
        }
    }

    private void addVersion(){
        if (oldVersion == null){return;}
        List<Version> newVersions = new ArrayList<>();
        // adds old Version after selected version
        if (versions.size() > 1) {
            for (Version version : versions) {
                newVersions.add(version);
                if (versions.indexOf(version) == selectedVersionIndex) {
                    newVersions.add(oldVersion);
                }
            }
        }else{
            newVersions.add(versions.getFirst());
            newVersions.add(oldVersion);
        }
        this.versions = newVersions;
    }

    private void createVersion(){
        this.oldVersion = new Version(this.versions.get(selectedVersionIndex).getLayers());
    }

    public void undo(){
        if (selectedVersionIndex < versions.size()-1) {
            selectedVersionIndex += 1;
        }
    }

    public void redo(){
        if (selectedVersionIndex > 0) {
            selectedVersionIndex -= 1;
        }
    }

    private void pickColor(int mouseX, int mouseY){
        int x = mouseX / pixelSize;
        int y = mouseY / pixelSize;

        if (x >= width || y >= height || x < 0 || y < 0) return;

        this.selectedColor = new Color(versions.get(selectedVersionIndex).getLayers().get(selectedLayerIndex).getRGB(x, y));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            switch (selectedTool){
                case PEN, RUBBER -> setPixel(e.getX(), e.getY());
                case COLOR_PICKER -> pickColor(e.getX(), e.getY());
            }

        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        setPixel(e.getX(), e.getY());
    }

    @Override public void mousePressed(MouseEvent e) {
        createVersion();
    }
    @Override public void mouseReleased(MouseEvent e) {
        addVersion();
    }
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {

    }
    @Override public void mouseMoved(MouseEvent e) {}

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedTool(Tool tool){
        this.selectedTool = tool;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public BufferedImage getSelectedLayer() {
        return versions.get(selectedVersionIndex).getLayers().get(selectedLayerIndex);
    }

    public void setSelectedLayer(int selectedLayer) {
        selectedLayerIndex = selectedLayer;
    }

    public List<Layer> getLayers() {
        return versions.get(selectedVersionIndex).getLayers();
    }

    public void setLayers(List<Layer> layers) {
        versions.set(0, new Version(layers));
    }

}
