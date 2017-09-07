package com.base.game;

/**
 * Handles time-based operations based on system time
 * 
 * @author JordanG
 */
public class Time
{
    public static final float DAMPING = 0.00000004f;                           
    
    private static long currentTime;                                           
    private static long lastTime;                                              
    
    public static long getTime()                                               
    {
        return System.nanoTime();                                             
    }
    
    /**
     * Get the fixed delta of the timer
     * 
     * @return 
     */
    public static float getDelta()                                              
    {
        return 0.66f;                         
    }
    
    /**
     * Get the fixed delta of the time in the decoupled-physics loop
     * @return 
     */
    public static float getPhysicsDelta()
    {
        return 0.01666666666f;
    }
    
    /**
     * Update the system time and last frame's time
     */
    public static void update()                                              
    {
        lastTime = currentTime;                                             
        currentTime = getTime();                                            
    }
    
    /**
     * Initialise the system time
     */
    public static void init()                                        
    {
        currentTime = getTime();                                       
        lastTime = getTime();                                               
    }
    
    /**
     * Get the duration of the last frame
     * 
     * @return 
     */
    public static float getFrameDuration()
    {
        return currentTime - lastTime;
    }
}
