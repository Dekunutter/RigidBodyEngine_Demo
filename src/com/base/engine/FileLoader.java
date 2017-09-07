package com.base.engine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import javax.swing.ImageIcon;

/**
 * Class of static methods used for loading files into the engine
 * 
 * @author JordanG
 */
public class FileLoader
{
    /**
     * Load a buffered image from a specified path
     * 
     * @param reference The path of the image to load
     * @return The image we loaded
     * @throws IOException Thrown if the image could not be found
     */
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
    
    /**
     * Load a buffered image from a specified path in the resources directory of the project
     * 
     * @param reference The path of the image from the resources directory
     * @return The image we loaded
     * @throws IOException Thrown if the image could not be found
     */
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
    
    /**
     * Loads a JSON file into the engine from the specified path
     * 
     * @param reference Path of the file to load in the resources directory of the project
     * @return The file we found
     * @throws IOException Thrown if the image could not be found
     */
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
