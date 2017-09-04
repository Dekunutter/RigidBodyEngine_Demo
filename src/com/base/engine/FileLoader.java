package com.base.engine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import javax.swing.ImageIcon;

public class FileLoader
{
    public static BufferedImage loadImage(String reference) throws IOException
    {
        File file = new File(reference);
        
        if(!file.exists())
        {
            throw new IOException("Cannot find: " + reference);
        }
        
        Image image = new ImageIcon(file.getAbsolutePath()).getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        
        return bufferedImage;
    }
    
    public static BufferedImage loadImageFromResources(String reference) throws IOException
    {
        URL url = FileLoader.class.getClassLoader().getResource(reference);

        if(url == null)
        {
            throw new IOException("Cannot find: " + reference);
        }
        
        Image image = new ImageIcon(url).getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = bufferedImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        
        return bufferedImage;
    }
    
    public static File loadJson(String reference) throws IOException
    {
        URL url = FileLoader.class.getClassLoader().getResource(reference);
        
        if(url == null)
        {
            throw new IOException("Cannt find: " + reference);
        }
        
        String filePath = URLDecoder.decode(url.getPath().toString());
        File file = new File(filePath);
        return file;
    }
}
