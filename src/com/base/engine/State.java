package com.base.engine;

/**
 * Enum storing the various states of the engine
 * 
 * @author JordanG
 */
public enum State
{
    INTRO, MAIN_MENU, GAME;
    
    public State getNext()
    {
        return values() [(ordinal() + 1) % values().length];
    }
}
