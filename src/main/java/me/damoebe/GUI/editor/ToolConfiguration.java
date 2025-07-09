package me.damoebe.GUI.editor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ToolConfiguration {
    private List<Color> colors = new ArrayList<>();
    private int drawSize;
    private Tool selectedTool;

    public ToolConfiguration(){
        colors = List.of(Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW, Color.BLACK, Color.ORANGE, Color.magenta);
        drawSize = 1;
        selectedTool = Tool.PEN;
    }

    public ToolConfiguration(String direction){
        // load settings
        colors = List.of(Color.GREEN, Color.RED, Color.BLUE, Color.YELLOW, Color.BLACK, Color.ORANGE, Color.magenta);
        selectedTool = Tool.PEN;
    }

    public void selectTool(Tool tool){
        selectedTool = tool;
    }

    public Tool getSelectedTool(){
        return selectedTool;
    }

    public List<Color> getColors(){
        return this.colors;
    }
}
