package com.base.engine.loop;

import com.base.engine.Engine;
import com.base.engine.states.Game;
import com.base.engine.states.Intro;
import com.base.engine.states.MainMenu;

/**
 * Class that handles all top-level update logic of the states in the engine
 * 
 * @author JordanG
 */
public class Update
{
    public static Update update;
    
    public Update()
    {
        
    }
    
    /**
     * Perform update logic
     */
    public void update()
    {
        switch(Engine.state)
        {
            case INTRO:
                Intro.intro.update();
                break;
            case MAIN_MENU:
                MainMenu.mainMenu.update();
                break;
            case GAME:
                Game.game.update();
                break;
        }
    }
}
