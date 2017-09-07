package com.base.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Hashtable;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

/**
 * Class for loading texture files from resources
 * 
 * @author JordanG
 */
public class TextureLoader
{
    public static TextureLoader textureLoader;
    
    private HashMap<String, Texture> table = new HashMap<String, Texture>();
    private ColorModel glAlphaColorModel, glColorModel;
    private IntBuffer textureIDBuffer = BufferUtils.createIntBuffer(1);
    
    /**
     * Initialise the texture loader with alpha bits to look out for and a colour model
     */
    public TextureLoader()
    {
        int[] bits = {8, 8, 8, 8};
        boolean hassAlpha = true;
        boolean alphaPermitted = false;
        
        glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), bits, hassAlpha, alphaPermitted, ComponentColorModel.TRANSLUCENT, DataBuffer.TYPE_BYTE);
        glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[] {8, 8, 8, 0}, hassAlpha, alphaPermitted, ComponentColorModel.OPAQUE, DataBuffer.TYPE_BYTE);
    }
    /**
    * Create an ID for the loaded texture in the buffer
    */
    private int createTextureID()
    {
        glGenTextures(textureIDBuffer);
        return textureIDBuffer.get(0);
    }
    
    /**
     * Load the texture from file in resources
     * 
     * @param resourceName Name of the file
     * @return Texture
     * @throws IOException Issue finding texture
     */
    public Texture getTexture(String resourceName) throws IOException
    {
        Texture texture = table.get(resourceName);
        if(texture != null)
        {
            return texture;
        }
        
        texture = getTexture(resourceName, GL_TEXTURE_2D, GL_RGBA, GL_NEAREST, GL_NEAREST);
        table.put(resourceName, texture);
        return texture;
    }
    
    /**
     * Load the texture from file in resources with a magnitude filter
     * 
     * @param resourceName Name of file
     * @param target ID of target to bind the texture to
     * @param dstPixelFormat Pixel format of file
     * @param minFilter Minimum magnitude filter
     * @param magFilter Maximum magnitude filter
     * @return Texture
     * @throws IOException Issue finding texture 
     */
    public Texture getTexture(String resourceName, int target, int dstPixelFormat, int minFilter, int magFilter) throws IOException
    {
        int srcPixelFormat;
        int textureID = createTextureID();
        Texture texture = new Texture(target, textureID);
        
        glBindTexture(target, textureID);
        
        BufferedImage bufferedImage = FileLoader.loadImageFromResources(resourceName);
        texture.setWidth(bufferedImage.getWidth());
        texture.setHeight(bufferedImage.getHeight());
        texture.setNormalizedWidth(powerOfTwo(bufferedImage.getWidth()));
        texture.setNormalizedHeight(powerOfTwo(bufferedImage.getHeight()));
        
        if(bufferedImage.getColorModel().hasAlpha())
        {
            srcPixelFormat = GL_RGBA;
        }
        else
        {
            srcPixelFormat = GL_RGB;
        }
        
        ByteBuffer textureBuffer = convertImageData(bufferedImage, texture);
        
        if(target == GL_TEXTURE_2D)
        {
            glTexParameteri(target, GL_TEXTURE_MIN_FILTER, minFilter);
            glTexParameteri(target, GL_TEXTURE_MAG_FILTER, magFilter);
        }
        
        glTexImage2D(target, 0, dstPixelFormat, get2Fold(bufferedImage.getWidth()), get2Fold(bufferedImage.getHeight()), 0, srcPixelFormat, GL_UNSIGNED_BYTE, textureBuffer);
        return texture;
    }
    
    /**
     * Bit operation to check of the specified value is the power of 2
     * 
     * @param value Value to perform the operations on
     * @return New value
     */
    private int powerOfTwo(int value)
    {
        if(value != 0)
        {
            value--;
            value |= (value >> 1);
            value |= (value >> 2);
            value |= (value >> 4);
            value |= (value >> 8);
            value |= (value >> 16);
            value++;
        }
        return value;
    }
    
    /**
     * Get 2 fold of specified value
     * 
     * @param fold Value to 2 fold
     * @return 2 folded value
     */
    private static int get2Fold(int fold)
    {
        int ret = 2;
        while(ret < fold)
        {
            ret *= 2;
        }
        return ret;
    }
    
    /**
     * Convert buffered image to byte buffer
     * 
     * @param bufferedImage Image to convert
     * @param texture Texture data of image
     * @return Byte buffer of image data
     */
    private ByteBuffer convertImageData(BufferedImage bufferedImage, Texture texture)
    {
        ByteBuffer imageBuffer;
        WritableRaster raster;
        BufferedImage textureImage;
        
        int textureWidth = 2;
        int textureHeight = 2;
        
        while(textureWidth < bufferedImage.getWidth())
        {
            textureWidth *= 2;
        }
        while(textureHeight < bufferedImage.getHeight())
        {
            textureHeight *= 2;
        }
        
        texture.setTextureHeight(textureHeight);
        texture.setTextureWidth(textureWidth);
        
        if(bufferedImage.getColorModel().hasAlpha())
        {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, textureWidth, textureHeight, 4, null);
            textureImage = new BufferedImage(glAlphaColorModel, raster, false, new Hashtable());
        }
        else
        {
            raster = Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, textureWidth, textureHeight, 3, null);
            textureImage = new BufferedImage(glColorModel, raster, false, new Hashtable());
        }
        
        Graphics g = textureImage.getGraphics();
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, textureWidth, textureHeight);
        g.drawImage(bufferedImage, 0, 0, null);
        
        byte[] data = ((DataBufferByte) textureImage.getRaster().getDataBuffer()).getData();
        
        imageBuffer = ByteBuffer.allocateDirect(data.length);
        imageBuffer.order(ByteOrder.nativeOrder());
        imageBuffer.put(data, 0, data.length);
        imageBuffer.flip();
        
        return imageBuffer;
    }
}
