package me.damoebe.GUI.editor.Canvas;

import java.awt.image.BufferedImage;

public class Layer extends BufferedImage {

    private final String name;
    private boolean visible;

    public Layer(int width, int height, String name) {
        super(width, height, BufferedImage.TYPE_INT_RGB);

        this.name = name;
        this.visible = true;
    }

    public Layer(int width, int height, String name, BufferedImage image) {
        super(width, height, BufferedImage.TYPE_INT_RGB);

        this.setData(image.getData());
        this.name = name;
        this.visible = true;
    }

    public void hide(){
        visible = false;
    }

    public void show(){
        visible = true;
    }

    public boolean isVisible(){
        return visible;
    }

    public String getName() {
        return name;
    }

}
