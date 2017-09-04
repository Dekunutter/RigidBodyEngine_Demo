package com.base.engine.physics;

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
