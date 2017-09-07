package com.base.engine.states;

import com.base.engine.Engine;
import com.base.engine.loop.GameLoop;
import com.base.game.Time;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

/**
 * Represents the intro splash screen in the engine loop
 * 
 * @author JordanG
 */
public class Intro implements GameLoop
{
    public static Intro intro;
    private static float blackAlpha;
    private boolean fading;
    
    public Intro()
    {     
        blackAlpha = 1;
        fading = false;
    }
    
    /**
     * Get input during this state
     */
    @Override
    public void getInput()
    {
        if(Engine.p1Key.isEnterOnce())
        {
            fading = true;
        }
    }

    /**
     * Update all logic
     */
    @Override
    public void update()
    {
        if(fading)
        {
            if(blackAlpha > -1)
            {
                blackAlpha -= (Time.getDelta())/20;
            }
            else
            {
                blackAlpha = -1;
            }
        }
        
        if(blackAlpha == -1)
        {
            Engine.nextState();
        }
    }
    
    @Override
    public void render()
    {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glColor4f(1f, 0f, 0f, blackAlpha);
        glRectf(0, 0, Display.getWidth(), Display.getHeight());
        
        glDisable(GL_BLEND);
    }
}
