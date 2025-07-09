package me.damoebe.GUI.editor.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Version {

    private final List<Layer> layers = new ArrayList<>();

    public Version(List<Layer> layers){
        for (Layer layer : layers){
            this.layers.add(new Layer(layer.getWidth(), layer.getWidth(), layer.getName(), layer));
        }
    }

    public List<Layer> getLayers() {
        return layers;
    }


}
