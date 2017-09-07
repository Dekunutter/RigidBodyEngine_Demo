package com.base.engine.loop;

/**
 * Required functions of all states running during the game loop
 * 
 * @author JordanG
 */
public interface GameLoop
{
    public void getInput();
    public void update();
    public void render();
}
