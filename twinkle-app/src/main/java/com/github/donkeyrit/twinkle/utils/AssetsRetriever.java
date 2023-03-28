package com.github.donkeyrit.twinkle.utils;

import javax.swing.ImageIcon;

import java.awt.Image;
import java.net.URL;

public class AssetsRetriever 
{
    public static Image retrieveAssetFromResources(String relativePath)
    {
        URL iconUrl = AssetsRetriever.class
            .getClassLoader()
            .getResource(relativePath);
            
        return new ImageIcon(iconUrl).getImage();
    }
}
