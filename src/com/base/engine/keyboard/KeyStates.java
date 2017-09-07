package com.base.engine.keyboard;

import org.lwjgl.input.Keyboard;

/**
 * Stores states of keys for input processing
 * 
 * @author JordanG
 */
public class KeyStates
{
    public static KeyStates p1Key;
    
    public boolean leftPressed, leftWasPressed;
    public boolean rightPressed, rightWasPressed;
    public boolean upPressed, upWasPressed;
    public boolean downPressed, downWasPressed;
    public boolean enterPressed, enterWasPressed;
    public boolean spacePressed, spaceWasPressed;
    public boolean shiftPressed, shiftWasPressed;
    public boolean controlPressed, controlWasPressed;
    public boolean escapePressed, escapeWasPressed;
    public boolean renderPressed, renderWasPressed;
    
    /**
     * Check input states of each key
     */
    public void getInput()
    {
        isLeftPressed();
        isRightPressed();
        isUpPressed();
        isDownPressed();
        isEnterPressed();
        isSpacePressed();
        isShiftPressed();
        isControlPressed();
        isEscapePressed();
        isRenderPressed();
    }
    
    /**
     * Reset all key input states
     */
    public void resetInput()
    {
        leftWasPressed = leftPressed;
        rightWasPressed = rightPressed;
        upWasPressed = upPressed;
        downWasPressed = downPressed;
        enterWasPressed = enterPressed;
        spaceWasPressed = spacePressed;
        controlWasPressed = controlPressed;
        escapeWasPressed = escapePressed;
        renderWasPressed = renderPressed;
    }

    /**
     * Check if the left movement key is pressed
     * 
     * @return 
     */
    public boolean isLeftPressed()
    {
        for(int key : Keys.keys.left)
        {
            if(Keyboard.isKeyDown(key))
            {
                leftPressed = true;
                return leftPressed;
            }
        }
        leftPressed = false;
        return leftPressed;
    }
    
    /**
     * Check if the right movement key is pressed
     * 
     * @return 
     */
    public boolean isRightPressed()
    {
        for(int key : Keys.keys.right)
        {
            if(Keyboard.isKeyDown(key))
            {
                rightPressed = true;
                return rightPressed;
            }
        }
        rightPressed = false;
        return rightPressed;
    }
    
    /**
     * Check if the up movement key is pressed
     * 
     * @return 
     */
    public boolean isUpPressed()
    {
        for(int key : Keys.keys.up)
        {
            if(Keyboard.isKeyDown(key))
            {
                upPressed = true;
                return upPressed;
            }
        }
        upPressed = false;
        return upPressed;
    }
    
    /**
     * Check if the down movement key is pressed
     * 
     * @return 
     */
    public boolean isDownPressed()
    {
        for(int key : Keys.keys.down)
        {
            if(Keyboard.isKeyDown(key))
            {
                downPressed = true;
                return downPressed;
            }
        }
        downPressed = false;
        return downPressed;
    }
    
    /**
     * Check if the enter key is pressed
     * 
     * @return 
     */
    public boolean isEnterPressed()
    {
        for(int key : Keys.keys.enter)
        {
            if(Keyboard.isKeyDown(key))
            {
                enterPressed = true;
                return enterPressed;
            }
        }
        enterPressed = false;
        return enterPressed;
    }
    
    /**
     * Check if the spacebar is pressed
     * 
     * @return 
     */
    public boolean isSpacePressed()
    {
        for(int key : Keys.keys.space)
        {
            if(Keyboard.isKeyDown(key))
            {
                spacePressed = true;
                return spacePressed;
            }
        }
        spacePressed = false;
        return spacePressed;
    }
    
    /**
     * Check if the left-shift key is pressed
     * 
     * @return 
     */
    public boolean isShiftPressed()
    {
        for(int key : Keys.keys.shift)
        {
            if(Keyboard.isKeyDown(key))
            {
                shiftPressed = true;
                return shiftPressed;
            }
        }
        shiftPressed = false;
        return shiftPressed;
    }
    
    /**
     * Check if the left-control key is pressed
     * 
     * @return 
     */
    public boolean isControlPressed()
    {
        for(int key : Keys.keys.control)
        {
            if(Keyboard.isKeyDown(key))
            {
                controlPressed = true;
                return controlPressed;
            }
        }
        controlPressed = false;
        return controlPressed;
    }
    
    /**
     * Check if the escape key is pressed
     * 
     * @return 
     */
    public boolean isEscapePressed()
    {
        for(int key : Keys.keys.escape)
        {
            if(Keyboard.isKeyDown(key))
            {
                escapePressed = true;
                return escapePressed;
            }
        }
        escapePressed = false;
        return escapePressed;
    }
    
    /**
     * Check if the render state key is pressed
     * 
     * @return 
     */
    public boolean isRenderPressed()
    {
        for(int key : Keys.keys.render)
        {
            if(Keyboard.isKeyDown(key))
            {
                renderPressed = true;
                return renderPressed;
            }
        }
        renderPressed = false;
        return renderPressed;
    }
    
    /**
     * Check if the left key was pressed on this frame
     * 
     * @return 
     */
    public boolean isLeftOnce()
    {
        if(leftPressed && !leftWasPressed)
        {
            return true;
        }
        return false;
    }    
    
    /**
    * Check if the right key was pressed on this frame
    * 
    * @return
    */
    public boolean isRightOnce()
    {
        if(rightPressed && !rightWasPressed)
        {
            return true;
        }
        return false;
    }    
    
    /**
     * Check if the up key was pressed on this frame
     * 
     * @return 
     */
    public boolean isUpOnce()
    {
        if(upPressed && !upWasPressed)
        {
            return true;
        }
        return false;
    }    
    
    /**
     * Check if the down key was pressed on this frame
     * 
     * @return 
     */
    public boolean isDownOnce()
    {
        if(downPressed && !downWasPressed)
        {
            return true;
        }
        return false;
    }    
    
    /**
     * Check if the enter key was pressed on this frame
     * 
     * @return 
     */
    public boolean isEnterOnce()
    {
        if(enterPressed && !enterWasPressed)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Check if the spacebar was pressed on this frame
     * 
     * @return 
     */
    public boolean isSpaceOnce()
    {
        if(spacePressed && !spaceWasPressed)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Check if the left-shift key was pressed on this frame
     * 
     * @return 
     */
    public boolean isShiftOnce()
    {
        if(shiftPressed && !shiftWasPressed)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Check if the left-control key was pressed on this frame
     * 
     * @return 
     */
    public boolean isControlOnce()
    {
        if(controlPressed && !controlWasPressed)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Check is the escape key was pressed on this frame
     * 
     * @return 
     */
    public boolean isEscapeOnce()
    {
        if(escapePressed && !escapeWasPressed)
        {
            return true;
        }
        return false;
    }
    
    /**
     * Check if the render state key was pressed on this frame
     * 
     * @return 
     */
    public boolean isRenderOnce()
    {
        if(renderPressed && !renderWasPressed)
        {
            return true;
        }
        return false;
    }
}
