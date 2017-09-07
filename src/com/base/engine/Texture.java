package com.base.engine;

import static org.lwjgl.opengl.GL11.*;

/**
 * Object class for data related to each texture redered by the engine
 * 
 * @author JordanG
 */
public class Texture
{
    private int target, textureID, width, height, textureWidth, textureHeight;
    private float widthRatio, heightRatio, normalizedWidth, normalizedHeight;
    
    /**
     * Initialise texture with a target and ID
     * 
     * @param target ID of the target we are binding the texture to
     * @param textureID ID of the texture itself
     */
    public Texture(int target, int textureID)
    {
        this.target = target;
        this.textureID = textureID;
    }
    
    /**
     * Bind the texture to its target
     */
    public void bind()
    {
        glBindTexture(target, textureID);
    }
    
    /**
     * Set the height of the texture image
     * 
     * @param height New height of the texture image
     */
    public void setHeight(int height)
    {
        this.height = height;
        setHeight();
    }
    
    /**
     * Set the width of the texture image
     * 
     * @param width New width of the texture image
     */
    public void setWidth(int width)
    {
        this.width = width;
        setWidth();
    }
    
    /**
     * Get height of the texture image
     * 
     * @return Height of texture image
     */
    public int getImageHeight()
    {
        return height;
    }
    
    /**
     * Get width of the texture image
     * 
     * @return Width of the texture image
     */
    public int getImageWidth()
    {
        return width;
    }
    
    /**
     * Get height of the texture in ratio to how much of the target is textured
     * 
     * @return Height ratio of the texture along its target
     */
    public float getHeight()
    {
        return heightRatio;
    }
    
    /**
     * Get width of the texture in ratio to how much of the target is textured
     * 
     * @return Width ratio of the texture along its target
     */
    public float getWidth()
    {
        return widthRatio;
    }
    
    /**
     * Set texture and texture image heights
     * 
     * @param textureHeight Height of the texture
     */
    public void setTextureHeight(int textureHeight)
    {
        this.textureHeight = textureHeight;
        setHeight();
    }
    
    /**
     * Set texture and texture image widths
     * 
     * @param textureWidth  Width of the texture
     */
    public void setTextureWidth(int textureWidth)
    {
        this.textureWidth = textureWidth;
        setWidth();
    }
    
    /**
     * Set height ratio of the texture
     */
    private void setHeight()
    {
        if(textureHeight != 0)
        {
            heightRatio = ((float)height)/textureHeight;
        }
    }
    
    /**
     * Set width ratio of the texture
     */
    private void setWidth()
    {
        if(textureWidth != 0)
        {
            widthRatio = ((float)width/textureWidth);
        }
    }
    
    /**
     * Set normalised width of the texture
     * 
     * @param potWidth 
     */
    public void setNormalizedWidth(int potWidth)
    {
        normalizedWidth = width/(float)potWidth;
    }
    
    /**
     * Set normalised height of the texture
     * 
     * @param potHeight 
     */
    public void setNormalizedHeight(int potHeight)
    {
        normalizedHeight = height/(float)potHeight;
    }
    
    /**
     * Get normalised width of the texture
     * 
     * @return Normalised width of the texture
     */
    public float getNormalizedWidth()
    {
        return normalizedWidth;
    }
    
    /**
     * Get normalised height of the texture
     * 
     * @return Normalised height of the texture
     */
    public float getNormalizedHeight()
    {
        return normalizedHeight;
    }
}
