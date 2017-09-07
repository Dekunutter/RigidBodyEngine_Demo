package com.base.engine.physics;

/**
 * Ties a body to a force generator
 * 
 * @author JordanG
 */
public class ForceRegistration
{
    protected Body body;
    protected ForceGenerator generator;

    public ForceRegistration()
    {
        body = null;
        generator = null;
    }

    public ForceRegistration(Body body, ForceGenerator fg)
    {
        this.body = body;
        this.generator = fg;
    }
}
