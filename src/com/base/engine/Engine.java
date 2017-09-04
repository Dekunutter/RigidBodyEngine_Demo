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
        
        initDisplay();                                                          //initialize the display
        initGL();                                                               //initialize OpenGL
        initAL();
        initTextureLoader();
        initKeyboard();
        
        initIntro();                                                             //initialize the game itself
        initMainMenu();
        initGame();
        
        //LOOP EVERY FRAME
        gameLoop2();                                                             //activate the game loop, executing logic every frame
        
        //CALL ONCE UPON TERMINATION
        cleanUp();                                                              //clean up after the program has been close
    }
    
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

    private static void initDisplay()                                           //initialize the display window
    {
        try
        {
            Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));                  //set up the display window to 800x600
            Display.create();                                                   //create the window
            //Display.setVSyncEnabled(true);                                      //synchronize the frame rate to the monitor's refresh rate
        }
        catch (LWJGLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    private static void initGL()
    {
        render = new Render();
    }
    
    private static void initAL()
    {
        SoundManager.soundManager = new SoundManager();
        SoundManager.soundManager.initialize(8);
    }
    
    private static void initTextureLoader()
    {
        TextureLoader.textureLoader = new TextureLoader();
    }
    
    private static void initKeyboard()
    {   
        input = new Input();
    }
    
    private static void initIntro()
    {
        Intro.intro = new Intro();
    }
    
    private static void initMainMenu()
    {
        MainMenu.mainMenu = new MainMenu();
    }
    
    public static void initGame()                                              //initialize the Game object which will be used in the game loop
    {
        Game.game = new Game();                                                 //create new Game object
    }
    
    private static void gameLoop()                                              //game logic to execute every loop
    {
        Time.init();                                                            //initialize the timer object
        
        int frames = 0;                                                         //variable to count the number of frames that have passed
        long lastTime = System.nanoTime();                                      //hold the time of the last frame, initialized to the starting value in nanoseconds
        long totalTime = 0;                                                     //store the total time passed so far
        
        Update.update = new Update();
        
        while(!Display.isCloseRequested() && !quit)                                      //while the program has not been commanded to close in any way
        {   
            long now = System.nanoTime();                                       //set the time to the current system time in nanoseconds
            long passed = now - lastTime;                                       //store the time that has passed since the last frame by subtracting the current frame's time from the last frame's time
            lastTime = now;                                                     //set the last frame's time to the current frame's time in preparation for the calculations of time between this frame and the next one to come
            totalTime += passed;                                                //add the time that has passed to the total time
            
            if(totalTime >= 1000000000)                                         //if the total time passed is equal to or greater than 1 billion nanseconds
            {
                framesPassed = frames;
                System.out.println("FPS: " + frames + " " + Time.getDelta());                                     //print out the framerate
                totalTime = 0;                                                  //reset the total time counter
                frames = 0;                                                     //reset the frame counter
            }
            Time.update();                                                      //update the timer object
            
            input.getInput();                                                         //check for input every frame
            Update.update.update();                                                           //update the logic every frame
            render.render();                                                           //draw to the display every frame
        
            frames++;                                                           //increment the frame counter by one at the end of every game loop
        }
    }
    
    private static void gameLoop2()                                              //game logic to execute every loop
    {
        Time.init();                                                            //initialize the timer object
        
        int frames = 0;                                                         //variable to count the number of frames that have passed
        long lastTime = System.nanoTime();                                      //hold the time of the last frame, initialized to the starting value in nanoseconds
        long totalTime = 0;                                                     //store the total time passed so far
        long updateTime = 0;
        
        Update.update = new Update();
        
        while(!Display.isCloseRequested() && !quit)                                      //while the program has not been commanded to close in any way
        {   
            long now = System.nanoTime();                                       //set the time to the current system time in nanoseconds
            long passed = now - lastTime;                                       //store the time that has passed since the last frame by subtracting the current frame's time from the last frame's time
            lastTime = now;                                                     //set the last frame's time to the current frame's time in preparation for the calculations of time between this frame and the next one to come
            totalTime += passed;                                                //add the time that has passed to the total time
            updateTime += passed;
            
            //16666666.6667f run physics once every 16 milliseconds, basically a 60FPS physics system
            //20000000, run physics once every 20 milliseconds, basically a 50FPS physics system
            while(updateTime >= 16666666.6667f)
            {
                framesPassed = frames;
                Update.update.update();
                updateTime -= 16666666.6667f;
            }
            
            input.getInput();                                                                 //update the logic every frame
            
            //render.interpolate(totalTime/Time.getDelta());
            render.render();                                                           //draw to the display every frame
        
            if(totalTime >= 1000000000)                                         //if the total time passed is equal to or greater than 1 billion nanseconds
            {
                framesPassed = frames;
                System.out.println("FPS: " + frames + " " + Time.getDelta());                                     //print out the framerate
                totalTime = 0;                                                  //reset the total time counter
                frames = 0;                                                     //reset the frame counter
            }
            frames++;                                                           //increment the frame counter by one at the end of every game loop
        }
    }
    
    public static void nextState()
    {
        state = state.getNext();
    }
    
    public static void setState(State s)
    {
        state = s;
    }
    
    private static void cleanUp()                                               //handle the freeing of resources when the program has been closed
    {
        Display.destroy();                                                      //destroy the display window
        Keyboard.destroy();                                                     //stop looking for input
    }
}