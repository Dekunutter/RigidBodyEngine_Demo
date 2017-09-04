package com.base.engine.loop;

import com.base.engine.Engine;
import com.base.engine.states.Game;
import com.base.engine.states.Intro;
import com.base.engine.states.MainMenu;

public class Update
{
    public static Update update;
    
    public Update()
    {
        
    }
    
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
