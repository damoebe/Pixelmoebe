package me.damoebe;


import java.io.*;
import java.net.URL;
import java.util.List;

// container class for all GitHub download stuff
public class AssetRetriever {

    // important : all asset names have to be in this list, or they won't get downloaded!
    private static List<String> assetNames = List.of("flower", "color-picker", "pen", "plus", "rubber");

    public static void setupAssets(){
        File rootDir = new File(System.getProperty("user.home") + "/Pixelmoebe/assets");
        rootDir.mkdirs();
        for (String name : assetNames){
            downloadAsset(name);
        }
    }
    private static void downloadAsset(String name){
        try {
            URL url = new URL("https://raw.githubusercontent.com/damoebe/Pixelmoebe/refs/heads/master" +
                    "/src/main/java/me/damoebe/assets/" + name + ".png");
            InputStream inputStream = url.openStream();
            File source = new File(System.getProperty("user.home") + "/Pixelmoebe/assets/" + name + ".png");
            FileOutputStream outputStream = new FileOutputStream(source);
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            source.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
