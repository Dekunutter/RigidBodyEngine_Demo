package com.base.game;

/**
 * Sets up a countdown that is used to delay operations
 * 
 * @author JordanG
 */
public class Delay
{
    private int length;                                                         
    private long endTime;                                                       
    boolean started;                                                            
    
    /**
     * Initialise but do not start a delay timer
     * 
     * @param length Length of the countdown
     */
    public Delay(int length)                                                    
    {
        this.length = length;                                                   
        started = false;                                                        
    }
    
    /**
     * Check if the countdown was started and timed out
     * 
     * @return 
     */
    public boolean isOver()                                                     
    {
        if(!started)                                                            
        {
            return false;                                                       
        }
        
        return Time.getTime() >= endTime;                                       
    }
    
    /**
     * Check if the delay is active
     * 
     * @return 
     */
    public boolean isActive()                                                  
    {
        return started;                                                         
    }
    
    /**
     * Restart the delay, resetting the end time
     */
    public void restart()                                                       
    {
        started = true;                                                       
        endTime = (length * 1000000) + Time.getTime();                       
    }
    
    /**
     * Stop the delay countdown
     */
    public void stop()                                                          
    {
        started = false;                                                      
    }

    /**
     * End the countdown as if it expired naturally
     */
    public void terminate()                                                  
    {
        started = true;                                                         
        endTime = 0;                                                          
    }
}
