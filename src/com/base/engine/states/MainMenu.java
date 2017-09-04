package com.base.engine.states;

import com.base.engine.Engine;
import com.base.engine.loop.GameLoop;
import com.base.game.Time;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;

public class MainMenu implements GameLoop
{
    public static MainMenu mainMenu;
    private int selection, numOptions, trueSelection;
    private static float blackAlpha;
    
    public MainMenu()
    {
        selection = 1;
        numOptions = 5;
        trueSelection = 5;
        
        blackAlpha = -1;
    }
    
    @Override
    public void getInput()
    {
        if(Engine.p1Key.isUpOnce())
        {
            if(selection > 1)
            {
                selection -= 1;
            }
            else
            {
                selection = 1;
            }
        }
        
        if(Engine.p1Key.isDownOnce())
        {
            if(selection < numOptions)
            {
                selection += 1;
            }
            else
            {
                selection = numOptions;
            }
        }
        
        if(Engine.p1Key.isEnterOnce())
        {
            switch(selection)
            {
                case 1:
                    Engine.initGame();
                    Engine.nextState();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    Engine.quit = true;
                    break;
            }
        }
        
        if(Engine.p1Key.isEscapeOnce())
        {
            if(selection != 5)
            {
                selection = 5;
            }
            else
            {
                Engine.quit = true;
            }
        }
    }

    @Override
    public void update()
    {
        trueSelection = (numOptions + 1) - selection;
        
        if(blackAlpha < 1)
        {
            blackAlpha += (Time.getDelta()/10);
        }
        else
        {
            blackAlpha = 1;
        }
    }

    @Override
    public void render()
    {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        glColor4f(0f, 0f, 1f, blackAlpha);
        glRectf(0, 0, Display.getWidth(), Display.getHeight());
                
        glColor4f(0.5f, 0.5f, 0.5f, blackAlpha);
        glRectf(300, 150, 100, 100);
        
        glColor4f(0.5f, 0.5f, 0.5f, blackAlpha);
        glRectf(300, 250, 100, 200);
        
        glColor4f(0.5f, 0.5f, 0.5f, blackAlpha);
        glRectf(300, 350, 100, 300);
        
        glColor4f(0.5f, 0.5f, 0.5f, blackAlpha);
        glRectf(300, 450, 100, 400);
        
        glColor4f(0.5f, 0.5f, 0.5f, blackAlpha);
        glRectf(300, 550, 100, 500);
        
        glColor4f(1.0f, 0, 0, blackAlpha);
        
        glRectf(50, (trueSelection * 100) + 50, 100, trueSelection * 100);
        
        glDisable(GL_BLEND);
    }
}
