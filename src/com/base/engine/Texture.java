package com.base.engine;

import static org.lwjgl.opengl.GL11.*;

public class Texture
{
    private int target, textureID, width, height, textureWidth, textureHeight;
    private float widthRatio, heightRatio, normalizedWidth, normalizedHeight;
    
    public Texture(int target, int textureID)
    {
        this.target = target;
        this.textureID = textureID;
    }
    
    public void bind()
    {
        glBindTexture(target, textureID);
    }
    
    public void setHeight(int height)
    {
        this.height = height;
        setHeight();
    }
    
    public void setWidth(int width)
    {
        this.width = width;
        setWidth();
    }
    
    public int getImageHeight()
    {
        return height;
    }
    
    public int getImageWidth()
    {
        return width;
    }
    
    public float getHeight()
    {
        return heightRatio;
    }
    
    public float getWidth()
    {
        return widthRatio;
    }
    
    public void setTextureHeight(int textureHeight)
    {
        this.textureHeight = textureHeight;
        setHeight();
    }
    
    public void setTextureWidth(int textureWidth)
    {
        this.textureWidth = textureWidth;
        setWidth();
    }
    
    private void setHeight()
    {
        if(textureHeight != 0)
        {
            heightRatio = ((float)height)/textureHeight;
        }
    }
    
    private void setWidth()
    {
        if(textureWidth != 0)
        {
            widthRatio = ((float)width/textureWidth);
        }
    }
    
    public void setNormalizedWidth(int potWidth)
    {
        normalizedWidth = width/(float)potWidth;
    }
    
    public void setNormalizedHeight(int potHeight)
    {
        normalizedHeight = height/(float)potHeight;
    }
    
    public float getNormalizedWidth()
    {
        return normalizedWidth;
    }
    
    public float getNormalizedHeight()
    {
        return normalizedHeight;
    }
}
