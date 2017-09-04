package com.base.game;

public class Delay
{
    private int length;                                                         //variable to store the length of the delay
    private long endTime;                                                       //variable to store the ending time of the delay
    boolean started;                                                            //boolean to determine whether or not the delay is active
    
    public Delay(int length)                                                    //initialize the delay object using a length value
    {
        this.length = length;                                                   //initialize the length variable using the passed-in length value
        started = false;                                                        //the delay has yet to start so set the boolean to false
    }
    
    public boolean isOver()                                                     //trigger the end of the delay
    {
        if(!started)                                                            //if the delay was never started in the first place
        {
            return false;                                                       //exit without ending the delay
        }
        
        return Time.getTime() >= endTime;                                       //if the delay was active, end the delay if the current time is greater than or equal to the ending time
    }
    
    public boolean isActive()                                                   //check whether or not the delay is active
    {
        return started;                                                         //return the value indicating whether or not the delay is active
    }
    
    public void restart()                                                       //start/restart the delay
    {
        started = true;                                                         //set the boolean started to true so that the delay is marked as active
        endTime = (length * 1000000) + Time.getTime();                          //declare an ending time by getting the current time and adding it to the length of the delay multiplie by 1 million (to convert nanoseconds to milliseconds)
    }
    
    public void stop()                                                          //stop the delay
    {
        started = false;                                                        //set the delay as inactive
    }

    public void terminate()                                                     //end the delay in whatever state it is in
    {
        started = true;                                                         //set the delay to active
        endTime = 0;                                                            //reset the ending time to 0
    }
}
