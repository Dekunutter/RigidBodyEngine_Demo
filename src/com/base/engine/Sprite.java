package com.base.engine;

import java.io.IOException;
import java.util.ArrayList;
import com.base.engine.math.Vec;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.Sphere;

public class Sprite
{
    private float red, green, blue, sizeX, sizeY, sizeZ;                               //declare the numerical colour values and size values of the image
    private Texture texture;
    private boolean fromJson;
    private Vec center, position;
    private float rotation;
    
    public Sprite(float sizeX, float sizeY)
    {
        red = 1;
        green = 1;
        blue = 1;
        
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        
        fromJson = false;
    }
    
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
    
    public Sprite(float sizeX, float sizeY, String reference, boolean json)
    {
        this(sizeX, sizeY, reference);
        fromJson = json;
    }
    
    public Sprite(float red, float green, float blue, float sizeX, float sizeY)
    {
        this.red = red;                                                         //set up the colour of the image
        this.green = green;
        this.blue = blue;
        this.sizeX = sizeX;                                                     //set up the size of the image
        this.sizeY = sizeY;
        fromJson = false;
    }
    
    public Sprite(float red, float green, float blue, float sizeX, float sizeY, float sizeZ)
    {
        this.red = red;                                                         //set up the colour of the image
        this.green = green;
        this.blue = blue;
        this.sizeX = sizeX;                                                     //set up the size of the image
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        fromJson = false;
    }
    
    public Sprite(float red, float green, float blue, float radius)
    {
        this.red = red;                                                         //set up the colour of the image
        this.green = green;
        this.blue = blue;
        this.sizeX = radius; 
        fromJson = false;
    }
    
    public void render()                                                        //draw the image
    {
        glColor3f(red, green, blue);                                            //change the drawing colour of OpenGL
        
        //reference for when I put in textures. Coords need to change
        //they are made for box2D origins, not OpenGL origins
        /*if(texture != null)
        {
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            texture.bind();
            if(!fromJson)
            {
                glBegin(GL_QUADS);
                {
                    glTexCoord2f(0, texture.getNormalizedHeight());                             //map a texture onto a point at 0 - texture's height
                    glVertex2f(-sizeX/2, -sizeY/2);                                               //draw a point in the quad at (0, 0)
                    glTexCoord2f(0, 0);
                    glVertex2f(-sizeX/2, sizeY/2);
                    glTexCoord2f(texture.getNormalizedWidth(), 0);
                    glVertex2f(sizeX/2, sizeY/2);
                    glTexCoord2f(texture.getNormalizedWidth(), texture.getNormalizedHeight());
                    glVertex2f(sizeX/2, -sizeY/2);
                }
                glEnd();
            }
            else
            {
                glBegin(GL_QUADS);
                {
                    //Works for Json objects, but not normal texturing?
                    glTexCoord2f(0, texture.getNormalizedHeight());
                    glVertex2f(0, 0);
                    glTexCoord2f(texture.getNormalizedWidth(), texture.getNormalizedHeight());
                    glVertex2f(sizeX, 0);
                    glTexCoord2f(texture.getNormalizedWidth(), 0);
                    glVertex2f(sizeX, sizeY);
                    glTexCoord2f(0, 0);
                    glVertex2f(0, sizeY);
                }
                glEnd();
            }
            glDisable(GL_TEXTURE_2D);
        }
        else
        {
            glBegin(GL_QUADS);
            {
                glVertex2f(-sizeX/2, -sizeY/2);
                glVertex2f(-sizeX/2, sizeY/2);
                glVertex2f(sizeX/2, sizeY/2);
                glVertex2f(sizeX/2, -sizeY/2);
            }
            glEnd();
        }*/
        
        //bottom-left origin
        /*glBegin(GL_QUADS);                                                      //draw a quad in OpenGL
        {
            glVertex2f(0, 0);                                                   //draw a point in the quad at (0, 0)
            glVertex2f(0, sizeY);
            glVertex2f(sizeX, sizeY);
            glVertex2f(sizeX, 0);
        }
        glEnd();*/                                                            //finish drawing the quad
        //centre origin
        //SAT requires centred origins
        glBegin(GL_QUADS);
        {
            glVertex2f(-sizeX/2, -sizeY/2);
            glVertex2f(-sizeX/2, sizeY/2);
            glVertex2f(sizeX/2, sizeY/2);
            glVertex2f(sizeX/2, -sizeY/2);
        }
        glEnd(); 
    }
    
    public void render3D()                                                        //draw the image
    {
        glColor3f(red, green, blue);                                            //change the drawing colour of OpenGL
        
        //centre origin
        //SAT requires centred origins
        glBegin(GL_QUADS);
        {
            /*glVertex3f(-sizeX/2, -sizeY/2, 0);
            glVertex3f(-sizeX/2, sizeY/2, 0);
            glVertex3f(sizeX/2, sizeY/2, 0);
            glVertex3f(sizeX/2, -sizeY/2, 0);
            
            glVertex3f(-sizeX/2, -sizeY/2, 1);
            glVertex3f(-sizeX/2, sizeY/2, 1);
            glVertex3f(sizeX/2, sizeY/2, 1);
            glVertex3f(sizeX/2, -sizeY/2, 1);*/
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
    
    public void drawProjectionX(float min, float max)
    {
        glColor3f(red, green, 1);
        glBegin(GL_LINES);
        {
            //glVertex2f(300, y + sizeY/2);
            //glVertex2f(300, y + (-sizeY/2));
            glVertex2f(min, -100);
            glVertex2f(max, -100);
        }
        glEnd();
    }
    
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
    
    public float getSizeX()
    {
        return sizeX;
    }
    
    public void setSizeX(float sizeX)
    {
        this.sizeX = sizeX;
    }
    
    public float getSizeY()
    {
        return sizeY;
    }
    
    public void setSizeY(float sizeY)
    {
        this.sizeY = sizeY;
    }
    
    public float getSizeZ()
    {
        return sizeZ;
    }
    
    public void setSizeZ(float sizeZ)
    {
        this.sizeZ = sizeZ;
    }
}
