package com.base.engine.keyboard;

import org.lwjgl.input.Keyboard;

//3rd bool for each key as an escape point so that only one function is called with each
//e.g: game is not unpaused instantly
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
    
    public boolean isLeftOnce()
    {
        if(leftPressed && !leftWasPressed)
        {
            return true;
        }
        return false;
    }    
    
    public boolean isRightOnce()
    {
        if(rightPressed && !rightWasPressed)
        {
            return true;
        }
        return false;
    }    
    
    public boolean isUpOnce()
    {
        if(upPressed && !upWasPressed)
        {
            return true;
        }
        return false;
    }    
    
    public boolean isDownOnce()
    {
        if(downPressed && !downWasPressed)
        {
            return true;
        }
        return false;
    }    
    
    public boolean isEnterOnce()
    {
        if(enterPressed && !enterWasPressed)
        {
            return true;
        }
        return false;
    }
    
    public boolean isSpaceOnce()
    {
        if(spacePressed && !spaceWasPressed)
        {
            return true;
        }
        return false;
    }
    
    public boolean isShiftOnce()
    {
        if(shiftPressed && !shiftWasPressed)
        {
            return true;
        }
        return false;
    }
    
    public boolean isControlOnce()
    {
        if(controlPressed && !controlWasPressed)
        {
            return true;
        }
        return false;
    }
    
    public boolean isEscapeOnce()
    {
        if(escapePressed && !escapeWasPressed)
        {
            return true;
        }
        return false;
    }
    
    public boolean isRenderOnce()
    {
        if(renderPressed && !renderWasPressed)
        {
            return true;
        }
        return false;
    }
}
