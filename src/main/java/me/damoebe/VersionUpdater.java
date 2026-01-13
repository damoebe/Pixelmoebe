package me.damoebe;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class VersionUpdater {

    public static void checkVersion(Properties properties){
        try {
            URL url = new URL("https://raw.githubusercontent.com/damoebe/Pixelmoebe/refs/heads/master/version.txt");
            String version = new BufferedReader(new InputStreamReader(url.openStream())).readLine();
            if (!version.equals(Main.version)){
                updateVersion(version);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateVersion(String version){
        try {
            URL url = new URL("https://raw.githubusercontent.com/damoebe/Pixelmoebe/refs/heads/master/pixelmoebe_" + version + ".exe");
            File newExe = new File(System.getProperty("user.home") + "/Pixelmoebe/pixelmoebe_" + version + ".exe");
            InputStream inputStream = url.openStream();
            FileOutputStream outputStream = new FileOutputStream(newExe);
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            newExe.createNewFile();
            Runtime.getRuntime().exec(newExe.getAbsolutePath(), null, new File(System.getProperty("user.home") + "/Pixelmoebe/"));
            System.exit(0);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
