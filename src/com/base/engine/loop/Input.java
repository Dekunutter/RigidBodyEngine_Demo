package com.base.engine.loop;

import com.base.engine.Engine;
import com.base.engine.keyboard.KeyStates;
import com.base.engine.keyboard.Keys;
import com.base.engine.states.Game;
import com.base.engine.states.Intro;
import com.base.engine.states.MainMenu;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;

public class Input
{
    public static Input input;
    
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
