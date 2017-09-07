package com.base.engine;

import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import static org.lwjgl.openal.AL10.*;
import org.lwjgl.openal.OpenALException;
import org.lwjgl.util.WaveData;

/**
 * Class to handle all the sound output of the engine
 * 
 * @author JordanG
 */
public class SoundManager
{
    public static SoundManager soundManager;
    
    private IntBuffer scratchBuffer;
    private int[] sources, buffers;
    private boolean soundOutput;
    private int bufferIndex, sourceIndex;
    
    /**
     * Initialise an integer buffer for storing the sounds
     */
    public SoundManager()
    {
        scratchBuffer = BufferUtils.createIntBuffer(256);
        buffers = new int[256];
    }
    
    /**
     * Setup all the sound channels
     * 
     * @param channels Number of channels that the sound manager will be handling
     */
    public void initialize(int channels)
    {
        try
        {
            AL.create();
            
            scratchBuffer.limit(channels);
            alGenSources(scratchBuffer);
            scratchBuffer.rewind();
            scratchBuffer.get(sources = new int[channels]);
            
            if(alGetError() != AL_NO_ERROR)
            {
                throw new LWJGLException("Unable to allocate " + channels + " sources");
            }
            
            soundOutput = true;
        }
        catch(LWJGLException ex)
        {
            ex.printStackTrace();
            System.err.println("Sound error - Sound Disabled");
        }
    }
    
    /**
     * Load a sound from a specified path into the buffer
     * 
     * @param path File path of the sound we wish to load
     * @return Index of the sound in the buffer
     */
    public int addSound(String path)
    {
        scratchBuffer.rewind().position(0).limit(1);
        alGenBuffers(scratchBuffer);
        buffers[bufferIndex] = scratchBuffer.get(0);
        
        WaveData waveFile = WaveData.create("res/" + path);
        
        alBufferData(buffers[bufferIndex], waveFile.format, waveFile.data, waveFile.samplerate);
        
        waveFile.dispose();
        
        return bufferIndex++;
    }
    
    /**
     * Play the sound at the specified buffer index
     * 
     * @param buffer Index of the sound to play
     */
    public void playSound(int buffer)
    {
        if(soundOutput)
        {
            try
            {
                alSourcei(sources[sources.length - 1], AL_BUFFER, buffers[buffer]);
                alSourcePlay(sources[sources.length - 1]);
            }
            catch(OpenALException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    
    //Alternate method to PlaySound
    public void playEffect(int buffer)
    {
        if(soundOutput)
        {
            int channel = sources[(sourceIndex++ % (sources.length - 1))];
            
            alSourcei(channel, AL_BUFFER, buffers[buffer]);
            alSourcePlay(channel);
        }
    }
    
    /**
     * Check if a sound is currently playing
     * 
     * @return Boolean saying whether a sound playing or not
     */
    public boolean isPlayingSound()
    {
        return alGetSourcei(sources[sources.length - 1], AL_SOURCE_STATE) == AL_PLAYING;
    }
    
    /**
     * Deconstruct the sound manager
     */
    public void destroy()
    {
        if(soundOutput)
        {
            scratchBuffer.position(0).limit(sources.length);
            scratchBuffer.put(sources).flip();
            alSourceStop(scratchBuffer);
            
            alDeleteSources(scratchBuffer);
            
            scratchBuffer.position(0).limit(bufferIndex);
            scratchBuffer.put(buffers, 0, bufferIndex).flip();
            alDeleteBuffers(scratchBuffer);
            
            AL.destroy();
        }
    }
}
