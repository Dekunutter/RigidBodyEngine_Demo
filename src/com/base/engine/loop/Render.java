package com.base.engine.loop;

import com.base.engine.Engine;
import com.base.engine.states.Game;
import com.base.engine.states.Intro;
import com.base.engine.states.MainMenu;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.glu.GLU;

public class Render
{
    public static Render render;
    
    public Render()
    {
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        
        glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);

        glMatrixMode(GL_MODELVIEW);
        
        glDisable(GL_DEPTH_TEST);
        
        glClearColor(0, 0, 0, 0);  
        
        //glViewport(0, 0, Display.getWidth(), Display.getHeight());
        
    }
    
    public void render()
    {
        //glClear(GL_COLOR_BUFFER_BIT);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glLoadIdentity();
        
        switch(Engine.state)
        {
            case INTRO:
                Intro.intro.render();
                break;
            case MAIN_MENU:
                MainMenu.mainMenu.render();
                break;
            case GAME:
                Game.game.render();
                break;
        }
        
        Display.update();
        Display.sync(60);
    }
}
