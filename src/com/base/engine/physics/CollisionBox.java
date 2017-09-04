package com.base.engine.physics;

import com.base.engine.math.Vec;

public class CollisionBox extends CollisionPrimitive
{
    public Vec halfSize;

    public CollisionBox()
    {
        body = new Body();
        halfSize = new Vec();
    }
}
