package com.base.engine.keyboard;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

public class Keys
{
    public static Keys keys;
    
    public ArrayList<Integer> up, down, left, right, enter, space, shift, control, escape, render;
    
    public Keys()
    {
        up = new ArrayList<Integer>();
        down = new ArrayList<Integer>();
        left = new ArrayList<Integer>();
        right = new ArrayList<Integer>();
        enter = new ArrayList<Integer>();
        space = new ArrayList<Integer>();
        shift = new ArrayList<Integer>();
        control = new ArrayList<Integer>();
        escape = new ArrayList<Integer>();
        render = new ArrayList<Integer>();
             
        up.add(Keyboard.KEY_W);
        up.add(Keyboard.KEY_UP);
        
        down.add(Keyboard.KEY_S);
        down.add(Keyboard.KEY_DOWN);
        
        left.add(Keyboard.KEY_A);
        left.add(Keyboard.KEY_LEFT);
        
        right.add(Keyboard.KEY_D);
        right.add(Keyboard.KEY_RIGHT);
        
        enter.add(Keyboard.KEY_RETURN);
        
        space.add(Keyboard.KEY_SPACE);
        
        shift.add(Keyboard.KEY_LSHIFT);
        shift.add(Keyboard.KEY_RSHIFT);
        
        control.add(Keyboard.KEY_LCONTROL);
        control.add(Keyboard.KEY_RCONTROL);
        
        escape.add(Keyboard.KEY_ESCAPE);
        
        render.add(Keyboard.KEY_R);
    }
}
