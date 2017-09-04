package com.base.engine.states;

import com.base.game.Time;
import java.util.ArrayList;
import com.base.engine.physics.CollisionBox;
import com.base.engine.physics.CollisionData;
import com.base.engine.physics.CollisionDetector;
import com.base.engine.physics.CollisionPrimitive;
import com.base.engine.physics.Contact;
import com.base.engine.physics.ContactResolver;
import com.base.engine.physics.ForceRegistry;
import com.base.engine.math.Quaternion;
import com.base.engine.math.Vec;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_LEQUAL;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearDepth;
import static org.lwjgl.opengl.GL11.glDepthFunc;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glShadeModel;
import org.lwjgl.util.glu.GLU;
import com.base.engine.physics.Box;


public class Game
{   
    public static Game game;                                                    //create an instance of this object to call
    
    private ArrayList<CollisionPrimitive> objects;       
    private ArrayList<Contact> objectContacts;
    ContactResolver objectResolver;
    private static ForceRegistry forceRegistry;
    private static CollisionDetector detector;
    
    //MOVE THESE SOMEWHERE CLEANER
    //DEBUGGING VALUES FOR PAUSING UPDATE LOOP
    private boolean pausePressed, pauseWasPressed, paused = false;
    private boolean incrementPressed, incrementWasPressed, increment = false;
    
    private void generateTestLevel()
    {
        Box box = new Box();
        box.setState(new Vec(0.0f, 6.5f, 0f), new Quaternion(), new Vec(0.5f, 0.5f, 0.5f), new Vec(0, -15, 0));
        objects.add(box);
        Box bottom = new Box();
        bottom.setState(new Vec(0.0f, -1.5f, 0.0f), new Quaternion(), new Vec(0.5f, 0.5f, 0.5f), new Vec(0, 0, 0));
        objects.add(bottom);
        Box side = new Box();
        side.setState(new Vec(0.0f, -1.5f, 1f), new Quaternion(), new Vec(0.5f, 0.5f, 0.5f), new Vec(0, 0, 0));
        objects.add(side);
        Box bottomer = new Box();
        bottomer.setState(new Vec(0.0f, -2.5f, 0f), new Quaternion(), new Vec(0.5f, 0.5f, 0.5f), new Vec(0, 0, 0));
        objects.add(bottomer);
        Box sideBottom = new Box();
        sideBottom.setState(new Vec(0.0f, -2.5f, 1f), new Quaternion(), new Vec(0.5f, 0.5f, 0.5f), new Vec(0, 0, 0));
        objects.add(sideBottom);
        Box rightBottom = new Box();
        rightBottom.setState(new Vec(0.0f, -1.5f, -1f), new Quaternion(), new Vec(0.5f, 0.5f, 0.5f), new Vec(0, 0, 0));
        objects.add(rightBottom);
        Box rightBottomer = new Box();
        rightBottomer.setState(new Vec(0.0f, -2.5f, -1f), new Quaternion(), new Vec(0.5f, 0.5f, 0.5f), new Vec(0, 0, 0));
        objects.add(rightBottomer);
    }
    
    public Game()
    {                
        objects = new ArrayList<CollisionPrimitive>();    
        forceRegistry = new ForceRegistry();
        objectResolver = new ContactResolver(1);
        detector = new CollisionDetector();
        
        generateTestLevel();                                                    //call the generate test level function to generate all the walls appropriately
    }
    
    public void getInput()                                                      //handle user input every frame
    {
        //MOVE THESE. PAUSED AND INCREMENT KEY CHECKS
        pausePressed = Keyboard.isKeyDown(Keyboard.KEY_P);
        if(pausePressed && !pauseWasPressed)
        {
            if(!paused)
            {
                paused = true;
            }
            else
            {
                paused = false;
            }
        }
        pauseWasPressed = pausePressed;
        
        incrementPressed = Keyboard.isKeyDown(Keyboard.KEY_O);
        if(incrementPressed && !incrementWasPressed && paused)
        {
            increment = true;
        }
        incrementWasPressed = incrementPressed;
    }
    
    //Filter out "this" objects and it should be good!
    //render query to filter out off-screen objects from rendering using quadtree rectangle object
    public void update()                                                        //handle updated logic every frame
    {
        //MOVE THIS. PAUSED INCREMENT LOOP. UPDATE METHOD IS LARGE ENOUGH
        if(!paused || increment)
        {
            forceRegistry.UpdateForces(Time.getPhysicsDelta());
            
            for(CollisionPrimitive go : objects)                                            //for each GameObject object in the objects array list
            {
                
                    go.update(Time.getPhysicsDelta());                                                    //update the game logic for this object
                  
            }
            
            CollisionData data = new CollisionData();
            //data.friction = 0.2f;
            //data.restitution = 0.3f;
            data.tolerance = 0.1f;
            detector = new CollisionDetector();
            for(int i = 0; i < objects.size(); i++)
            {
                for(int j = 1; j < objects.size(); j++)
                {
                    if(i == j)
                    {
                        continue;
                    }
                    detector.boxAndBox((CollisionBox)objects.get(i), (CollisionBox)objects.get(j), data);
                }
            }
            objectResolver.resolveContacts(data.contacts, Time.getPhysicsDelta());
        }
        else
        {
            
        }
        increment = false;
    }
    
    public void render()                                                        //redraw the world every frame
    {   
        //enable 3D rendering (disables 2D rendering)
        glEnable(GL_TEXTURE_2D);
        glShadeModel(GL_SMOOTH);
        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        
        glClearColor(0, 0, 0, 0);
        glClearDepth(1.0f);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        GLU.gluPerspective(90, Display.getWidth() / Display.getHeight(), 0.1f, 500);
        
        glMatrixMode(GL_MODELVIEW);
        		
        GLU.gluLookAt(5.0f, 0.0f,  0.0f, 0.0f, 0.0f,  0.0f, 0.0f, 1.0f, 0.0f);
        
        for(CollisionPrimitive go : objects)                                            //for each GameObject object in the objects array list
        {
            go.render();                                                        //draw the object to screen
        }
    }
    
    public ArrayList<CollisionPrimitive> getObjects()                                   //get an array list containing all the game objects in the game
    {
        return objects;
    }
    
    public static ForceRegistry getForceRegistry()
    {
        return forceRegistry;
    }
}