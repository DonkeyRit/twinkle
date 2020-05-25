package com.github.donkeyrit.javaapp.resources;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

public class ResourceManager {

    private static final String IMAGES_BASE_PATH = "assets";

    public static Image getImageFromResources(Assets assets, String name) {
        return getImageIconFromResources(assets, name).getImage();
    }

    public static ImageIcon getImageIconFromResources(Assets assets, String name){
        try{
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            Path assetsPath = Path.of(IMAGES_BASE_PATH, assets.getPath(), name);
            InputStream image = classloader.getResourceAsStream(assetsPath.toString());
            return new ImageIcon(image.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
