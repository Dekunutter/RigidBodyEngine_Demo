package com.base.engine;

public enum State
{
    INTRO, MAIN_MENU, GAME;
    
    public State getNext()
    {
        return values() [(ordinal() + 1) % values().length];
    }
}
