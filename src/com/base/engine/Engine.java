package com.base.engine;

import com.base.engine.keyboard.KeyStates;
import com.base.engine.loop.Input;
import com.base.engine.loop.Render;
import com.base.engine.loop.Update;
import com.base.engine.states.Game;
import com.base.engine.states.Intro;
import com.base.engine.states.MainMenu;
import com.base.game.Time;
import java.net.URL;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Handles the initialisation, looping and destruction of the engine's main components
 * 
 * @author JordanG
 */
public class Engine
{    
    public static Engine engine;
    public static State state;
    private static Input input;
    private static Render render;
    
    public static KeyStates p1Key;
    private static int displayWidth, displayHeight;
    public static int framesPassed;
    public static boolean quit;
    
    public Engine()
    {
        state = State.GAME;
        framesPassed = 30;
        quit = false;
        
        //CALL ONCE UPON LAUNCH
        checkSettings();
        
        initDisplay();                                                          
        initGL();                                                               
        initAL();
        initTextureLoader();
        initKeyboard();
        
        initIntro();                                                             
        initMainMenu();
        initGame();
        
        //LOOP EVERY FRAME
        gameLoop2();                                                            
        
        //CALL ONCE UPON TERMINATION
        cleanUp();                                                              
    }
    
    /**
     * Check for the existance of the settings file in the project folder
     * Was used to extract the dimensions of the display window but currently unused
     */
    private static void checkSettings()
    {
        URL url = Main.class.getClassLoader().getResource("settings/settings.txt");
        
        if(url == null)
        {
            System.out.println("Not found");
            displayWidth = 800;
            displayHeight = 600;
            return;
        }
        else
        {
            System.out.println("Found");
            displayWidth = 800;
            displayHeight = 600;
        }
    }

    /**
     * Initialise the LWJGL display window
     */
    private static void initDisplay()                                           
    {
        try
        {
            Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));                  
            Display.create();                                                   
            //Display.setVSyncEnabled(true);                                    //disabled V-sync                            
        }
        catch (LWJGLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * Initialise the OpenGL render class that will be used while the engine is running
     */
    private static void initGL()
    {
        render = new Render();
    }
    
    /**
     * Initialise the OpenAL sound manager that will be used while the engine is running
     * Setup with 8 sound channels
     */
    private static void initAL()
    {
        SoundManager.soundManager = new SoundManager();
        SoundManager.soundManager.initialize(8);
    }
    
    /**
     * Initialise the texture loader class that will be used to load all texture images while the engine is running
     */
    private static void initTextureLoader()
    {
        TextureLoader.textureLoader = new TextureLoader();
    }
    
    /**
     * Initialise the keyboard class that will be used to handle all keyboard input while the engine is running
     */
    private static void initKeyboard()
    {   
        input = new Input();
    }
    
    /**
     * Initialise the intro splash screen state of the engine
     */
    private static void initIntro()
    {
        Intro.intro = new Intro();
    }
    
    /**
     * Initialise the main menu screen state of the engine
     */
    private static void initMainMenu()
    {
        MainMenu.mainMenu = new MainMenu();
    }
    
    /**
     * Initialise the game state of the engine (the main state)
     */
    public static void initGame()                                              
    {
        Game.game = new Game();                                                 
    }
    
    /**
     * Fixed timestep game loop with coupled physics
     */
    private static void gameLoop()                                             
    {
        Time.init();                                                            
        
        int frames = 0;                                                         
        long lastTime = System.nanoTime();                                    
        long totalTime = 0;                                                     
        
        Update.update = new Update();
        
        while(!Display.isCloseRequested() && !quit)                                     
        {   
            long now = System.nanoTime();                                       
            long passed = now - lastTime;                                       
            lastTime = now;                                                     
            totalTime += passed;                                               
            
            if(totalTime >= 1000000000)                                        
            {
                framesPassed = frames;
                System.out.println("FPS: " + frames + " " + Time.getDelta());                               
                totalTime = 0;                                                  
                frames = 0;                                                  
            }
            Time.update();                                                      
            
            input.getInput();                                                         
            Update.update.update();                                                         
            render.render();                                                          
        
            frames++;                                                           
        }
    }
    
    /**
     * Runs the semi-fixed timestep game loop with decoupled physics while the engine is active
     */
    private static void gameLoop2()                                             
    {
        Time.init();                                                            
        
        int frames = 0;                                                         
        long lastTime = System.nanoTime();                                     
        long totalTime = 0;                                                    
        long updateTime = 0;
        
        Update.update = new Update();
        
        while(!Display.isCloseRequested() && !quit)                                  
        {   
            long now = System.nanoTime();                                     
            long passed = now - lastTime;                                       
            lastTime = now;                                                   
            totalTime += passed;                                               
            updateTime += passed;
            
            //16666666.6667f run physics once every 16 milliseconds, basically a 60FPS physics system
            while(updateTime >= 16666666.6667f)
            {
                framesPassed = frames;
                Update.update.update();
                updateTime -= 16666666.6667f;
            }
            
            input.getInput();                                                                 
            
            //render.interpolate(totalTime/Time.getDelta());
            render.render();                                                         
        
            if(totalTime >= 1000000000)                                       
            {
                framesPassed = frames;
                System.out.println("FPS: " + frames + " " + Time.getDelta());                                 
                totalTime = 0;                                                 
                frames = 0;                                                
            }
            frames++;                                                          
        }
    }
    
    /**
     * Moves onto the next engine state in the state "queue"
     */
    public static void nextState()
    {
        state = state.getNext();
    }
    
    /**
     * Sets the engine state to the specified state
     * 
     * @param state the specified state to set the engine state to
     */
    public static void setState(State state)
    {
        state = state;
    }
    
    /**
     * Cleanup the engine on deconstruction by destroying the display and keyboard input classes
     */
    private static void cleanUp()                                               
    {
        Display.destroy();                                                      
        Keyboard.destroy();                                                    
    }
}