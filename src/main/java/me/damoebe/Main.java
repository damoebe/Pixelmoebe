package me.damoebe;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import me.damoebe.GUI.MainMenu;
import me.damoebe.GUI.editor.Editor;
import me.damoebe.GUI.error.ErrorScreen;

import javax.swing.*;
import java.awt.*;
import java.io.*;
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
    public static boolean missing_files = false;
    public static String assetPath = System.getProperty("user.home") + "/Pixelmoebe/assets";
    public static boolean darkMode = false;
    public static String version = "V0.1";

    public static void closeEditor(Editor editor){
        editors.remove(editor);
        if (editors.isEmpty()){
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        File assetPath = new File(Main.assetPath);
        if (!assetPath.isDirectory()){
            AssetRetriever.setupAssets();
        }
        File properties = new File(System.getProperty("user.home") + "/Pixelmoebe/properties.json");

        if (properties.exists()){
            // open last file
            Gson gson = new Gson();
            try {
                JsonReader jsonReader = new JsonReader(new FileReader(properties));
                Properties data = gson.fromJson(jsonReader, Properties.class);

                VersionUpdater.checkVersion(data);

                if (!data.getLastOpenProject().isEmpty()){
                    String lastOpenName = data.getLastOpenProject().split("/")[data.getLastOpenProject().split("/").length - 1];
                    String lastOpenFolder = data.getLastOpenProject().replace(lastOpenName, "");
                    new Editor(lastOpenFolder, lastOpenName);
                }else{
                    new MainMenu();
                }

                darkMode = data.isDarkMode();

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

        }else {
            try {
                Properties newProperties = new Properties("", false);
                FileWriter writer = new FileWriter(properties);
                Gson gson = new Gson();
                writer.write(gson.toJson(newProperties));
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            new MainMenu();
        }
    }

}