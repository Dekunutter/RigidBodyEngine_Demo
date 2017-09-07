package com.base.engine;

import java.io.IOException;
import java.util.ArrayList;
import com.base.engine.math.Vec;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.Sphere;

/**
 * Sprite class used to store all object data for each image we are rendering in the engine
 * 
 * @author JordanG
 */
public class Sprite
{
    private float red, green, blue, sizeX, sizeY, sizeZ;                        //declare the numerical colour values and size values of the image
    private Texture texture;
    private boolean fromJson;
    private Vec center, position;
    private float rotation;
    
    /**
     * Initialise the sprite with 2D dimensions
     * 
     * @param sizeX Width of sprite
     * @param sizeY Height of sprite
     */
    public Sprite(float sizeX, float sizeY)
    {
        red = 1;
        green = 1;
        blue = 1;
        
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        
        fromJson = false;
    }
    
    /**
     * Initialise the sprite from a file path
     * 
     * @param sizeX Width of sprite
     * @param sizeY Height of sprite
     * @param reference File path of the texture to load from the resources directory
     */
    public Sprite(float sizeX, float sizeY, String reference)
    {
        this(sizeX, sizeY);
        
        if(!reference.isEmpty())
        {
            try
            {
                texture = TextureLoader.textureLoader.getTexture("res/" + reference);
            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Initialise the sprite from a JSON file path
     * 
     * @param sizeX Width of sprite
     * @param sizeY Height of sprite
     * @param reference File path of the texture to load from the resources directory
     * @param json Determine whether we are loading from JSON or not
     */
    public Sprite(float sizeX, float sizeY, String reference, boolean json)
    {
        this(sizeX, sizeY, reference);
        fromJson = json;
    }
    
    /**
     * Initialise the sprite with specific colours
     * 
     * @param red Intensity of the red colouring
     * @param green Intensity of the green colouring
     * @param blue Intensity of the blue colouring
     * @param sizeX Width of the sprite
     * @param sizeY Height of the sprite
     */
    public Sprite(float red, float green, float blue, float sizeX, float sizeY)
    {
        this.red = red;                                                        
        this.green = green;
        this.blue = blue;
        this.sizeX = sizeX;                                                    
        this.sizeY = sizeY;
        fromJson = false;
    }
    
    /**
     * Initialise the sprite with specific colours in 3D
     * 
     * @param red Intensity of the red colouring
     * @param green Intensity of the green colouring
     * @param blue Intensity of the blue colouring
     * @param sizeX Width of the sprite
     * @param sizeY Height of the sprite
     * @param sizeZ Depth of the sprite
     */
    public Sprite(float red, float green, float blue, float sizeX, float sizeY, float sizeZ)
    {
        this.red = red;                                                         
        this.green = green;
        this.blue = blue;
        this.sizeX = sizeX;                                                     
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        fromJson = false;
    }
    
    /**
     * Initialise the sprite with a radius
     * 
     * @param red Intensity of the red colouring
     * @param green Intensity of the green colouring
     * @param blue Intensity of the blue colouring
     * @param radius Radius of the sprite
     */
    public Sprite(float red, float green, float blue, float radius)
    {
        this.red = red;                                                         
        this.green = green;
        this.blue = blue;
        this.sizeX = radius; 
        fromJson = false;
    }
    
    /**
     * Draw the sprite to screen with a centered origin
     */
    public void render()                                                        
    {
        glColor3f(red, green, blue);                                            
        
        glBegin(GL_QUADS);
        {
            glVertex2f(-sizeX/2, -sizeY/2);
            glVertex2f(-sizeX/2, sizeY/2);
            glVertex2f(sizeX/2, sizeY/2);
            glVertex2f(sizeX/2, -sizeY/2);
        }
        glEnd(); 
    }
    
    /**
     * Draw a 3D sprite to screen with a centered origin
     */
    public void render3D()                                                        
    {
        glColor3f(red, green, blue);                                            
        
        glBegin(GL_QUADS);
        {
            glColor3f(1.0f,1.0f,0.0f);           
            glVertex3f( sizeX/2, sizeY/2,-sizeZ/2);        
            glVertex3f(-sizeX/2, sizeY/2,-sizeZ/2);        
            glVertex3f(-sizeX/2, sizeY/2, sizeZ/2);
            glVertex3f( sizeX/2, sizeY/2, sizeZ/2);  
            glColor3f(1.0f,0.5f,0.0f);            
            glVertex3f( sizeX/2,-sizeY/2, sizeZ/2);
            glVertex3f(-sizeX/2,-sizeY/2, sizeZ/2);
            glVertex3f(-sizeX/2,-sizeY/2,-sizeZ/2);
            glVertex3f( sizeX/2,-sizeY/2,-sizeZ/2);
            glColor3f(1.0f,0.0f,0.0f);
            glVertex3f( sizeX/2, sizeY/2, sizeZ/2);
            glVertex3f(-sizeX/2, sizeY/2, sizeZ/2);
            glVertex3f(-sizeX/2,-sizeY/2, sizeZ/2);
            glVertex3f( sizeX/2,-sizeY/2, sizeZ/2);
            glColor3f(0.5f,1.0f,0.0f);
            glVertex3f( sizeX/2,-sizeY/2,-sizeZ/2);
            glVertex3f(-sizeX/2,-sizeY/2,-sizeZ/2);
            glVertex3f(-sizeX/2, sizeY/2,-sizeZ/2);
            glVertex3f( sizeX/2, sizeY/2,-sizeZ/2);
            glColor3f(0.0f,0.0f,1.0f);
            glVertex3f(-sizeX/2, sizeY/2, sizeZ/2);
            glVertex3f(-sizeX/2, sizeY/2,-sizeZ/2);
            glVertex3f(-sizeX/2,-sizeY/2,-sizeZ/2);
            glVertex3f(-sizeX/2,-sizeY/2, sizeZ/2);
            glColor3f(1.0f,0.0f,1.0f);
            glVertex3f( sizeX/2, sizeY/2,-sizeZ/2);
            glVertex3f( sizeX/2, sizeY/2, sizeZ/2);
            glVertex3f( sizeX/2,-sizeY/2, sizeZ/2);
            glVertex3f( sizeX/2,-sizeY/2,-sizeZ/2);
        }
        glEnd(); 
    }
    
    /**
     * Draw a projected line along the X axis between the specified points
     * 
     * @param min Starting point of the line along X
     * @param max Ending point of the line along X
     */
    public void drawProjectionX(float min, float max)
    {
        glColor3f(red, green, 1);
        glBegin(GL_LINES);
        {
            glVertex2f(min, -100);
            glVertex2f(max, -100);
        }
        glEnd();
    }
    
    /**
     * Draw a projected line along the Y axis between the specified points
     * 
     * @param min Starting point of the line along Y
     * @param max Ending point of the line along Y
     */
    public void drawProjectionY(float min, float max)
    {
        glColor3f(red, green, 1);
        glBegin(GL_LINES);
        {
            glVertex2f(300, min);
            glVertex2f(300, max);
        }
        glEnd();
    }
    
    /**
     * Get the width of the sprite
     * 
     * @return Width of the sprite
     */
    public float getSizeX()
    {
        return sizeX;
    }
    
    /**
     * Set the width of the sprite
     * 
     * @param sizeX New width of the sprite
     */
    public void setSizeX(float sizeX)
    {
        this.sizeX = sizeX;
    }
    
    /**
     * Get the height of the sprite
     * 
     * @return Height of the sprite
     */
    public float getSizeY()
    {
        return sizeY;
    }
    
    /**
     * Set the height of the sprite
     * 
     * @param sizeY New height of the sprite
     */
    public void setSizeY(float sizeY)
    {
        this.sizeY = sizeY;
    }
    
    /**
     * Get the depth of the sprite
     * 
     * @return Depth of the sprite
     */
    public float getSizeZ()
    {
        return sizeZ;
    }
    
    /**
     * Set the depth of the sprite
     * 
     * @param sizeZ New depth of the sprite 
     */
    public void setSizeZ(float sizeZ)
    {
        this.sizeZ = sizeZ;
    }
}
