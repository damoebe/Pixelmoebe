package me.damoebe;

import me.damoebe.GUI.MainMenu;
import me.damoebe.GUI.editor.Editor;

import java.util.ArrayList;
import java.util.List;

/*

    author: damoebe
    version: V0.1
    project-description:
        Pixelmoebe is a simple pixel-art-editor with multiple functions to design/draw a painting and
        the opportunity to save it as a png-file.

*/
public class Main{

    public static List<Editor> editors = new ArrayList<>();

    public static void closeEditor(Editor editor){
        editors.remove(editor);
        if (editors.isEmpty()){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new MainMenu();
    }

}