package com.base.game;

public class Time
{
    public static final float DAMPING = 0.00000004f;                            //constant value used to moderate the speed of the game
    
    private static long currentTime;                                            //variable used to store the time of the current frame
    private static long lastTime;                                               //variable used to store the time of the last frame
    
    public static long getTime()                                                //get the current system time for the current frame
    {
        return System.nanoTime();                                               //return the system time in nanoseconds
    }
    
    //Maybe apply a delta minimum value to stop FPS below 10 from causing
    //movement/collision issues
    public static float getDelta()                                              //get the delta (time between this frame and the last)
    {
        //return 1.0f/60.0f;
        return 0.66f;
        
        //return 0.016666666666f;
        //return 1;
        //return (currentTime - lastTime) * DAMPING;                              //return the delta multiplied by the damping constant to moderate the global game speed
    }
    
    public static float getPhysicsDelta()
    {
        return 0.01666666666f;
    }
    
    public static void update()                                                 //update the time values for the new frame
    {
        lastTime = currentTime;                                                 //the current frame is now the last frame
        currentTime = getTime();                                                //the current frame needs a fresh system time value
    }
    
    public static void init()                                                   //initialize the time object
    {
        currentTime = getTime();                                                //get the system time for the current frame
        lastTime = getTime();                                                   //get the system time for the last frame
    }
    
    public static float getFrameDuration()
    {
        return currentTime - lastTime;
    }
}
