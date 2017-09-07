package com.base.engine.physics;

import com.base.engine.math.Matrix3;
import com.base.engine.math.Quaternion;
import com.base.engine.math.Vec;

/**
 * Basic 3D Box object
 * 
 * @author JordanG
 */
public class Box extends CollisionBox
{	
    public boolean isOverlapping;

    public Box()
    {
        body = new Body();
    }

    public void setState(Vec position, Quaternion orientation, Vec extents, Vec velocity)
    {
        setState(position, orientation, extents, velocity, 8.0f);
    }

    /**
     * Initialise the box with the basic physics data needed for a body
     * @param position
     * @param orientation
     * @param extents
     * @param velocity
     * @param density 
     */
    public void setState(Vec position, Quaternion orientation, Vec extents, Vec velocity, float density)
    {
        body.setPosition(position);
        body.setOrientation(orientation);
        body.setRotation(new Vec());
        body.setAcceleration(velocity);
        
        halfSize = extents;

        float mass = halfSize.x * halfSize.y * halfSize.z * density;
        body.setMass(mass);

        Matrix3 tensor = new Matrix3();
        tensor.setBlockInertiaTensor(halfSize, mass);
        body.setInertiaTensor(tensor);

        body.setLinearDamping(0.95f);
        body.setAngularDamping(0.8f);
        body.clearAccumulators();

        body.setAwake();

        body.calculateDerivedData();
        calculateInternals();

        init(position.x, position.y, 0.1f, 1f, 0.25f, extents.x * 2, extents.y * 2, extents.z * 2); 
    }
}
