package com.base.engine.loop;

import com.base.engine.Engine;
import com.base.engine.keyboard.KeyStates;
import com.base.engine.keyboard.Keys;
import com.base.engine.states.Game;
import com.base.engine.states.Intro;
import com.base.engine.states.MainMenu;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

/**
 * Class that handles all top-level input of states in the engine
 * 
 * @author JordanG
 */
public class Input
{
    public static Input input;
    
    /**
     * Initialise input handling
     */
    public Input()
    {
        try
        {
            Keyboard.create();
            Keys.keys = new Keys();
            Engine.p1Key = new KeyStates();
        }
        catch(LWJGLException ex)
        {
            ex.printStackTrace();
        }
    }
    
    /**
     * Check for input
     */
    public void getInput()
    {
        Engine.p1Key.getInput();
        switch(Engine.state)
        {
            case INTRO:
                Intro.intro.getInput();
                break;
            case MAIN_MENU:
                MainMenu.mainMenu.getInput();
                break;
            case GAME:
                Game.game.getInput();
                break;
        }
        
        Engine.p1Key.resetInput();
    }
}
