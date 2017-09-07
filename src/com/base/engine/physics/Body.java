package com.base.engine.physics;

import com.base.engine.math.Matrix3;
import com.base.engine.math.Matrix4;
import com.base.engine.math.Quaternion;
import com.base.engine.math.Vec;

/**
 * Holds all common physics data for any physics-based object in the engine
 * 
 * @author JordanG
 */
public class Body
{
    protected float inverseMass;
    protected float sleepEpsilon = 0.3f;
     
    protected float linearDamping;
    protected float angularDamping;
    
    protected Vec forceAccum;
    protected Vec torqueAccum;
    protected Vec position;
    protected Quaternion orientation;
    protected Vec velocity;
    protected Vec rotation;
    protected Vec acceleration;
    protected Vec lastFrameAcceleration;
    
    protected Matrix3 inverseInertiaTensor;  
    protected Matrix3 inverseInertiaTensorWorld;
    protected Matrix4 transformMatrix;
    
    protected float motion;
    protected boolean isAwake;
    protected boolean canSleep;
    
    /**
     * Initialise a body
     */
    public Body()
    {
        forceAccum = new Vec();
        torqueAccum = new Vec();
        position = new Vec();
        orientation = new Quaternion();
        velocity = new Vec();
        rotation = new Vec();
        acceleration = new Vec();
        lastFrameAcceleration = new Vec();
        
        inverseInertiaTensor = new Matrix3();
        inverseInertiaTensorWorld = new Matrix3();
        transformMatrix = new Matrix4();
    }

    /**
     * Set the inverse mass of a body via its current mass
     * 
     * @param mass 
     */
    public void setMass(float mass)
    {
        if (mass != 0)
        {
            inverseMass = 1.0f / mass;
        }
    }

    /**
     * Get the mass of the body from its inverse mass
     * 
     * @return Mass
     */
    public float getMass()
    {
        if (inverseMass == 0)
        {
                return Float.MAX_VALUE;
        }
        else
        {
                return 1.0f / inverseMass;
        }
    }

    /**
     * Set the inverse mass directly
     * 
     * @param inverseMass 
     */
    public void setInverseMass(float inverseMass)
    {
        this.inverseMass = inverseMass;
    }

    /**
     * Get the inverse mass
     * 
     * @return Inverse mass 
     */
    public float getInverseMass()
    {
        return inverseMass;
    }

    /**
     * Check if a body as infinite mass
     * 
     * @return 
     */
    public boolean hasFiniteMass()
    {
        if (inverseMass >= 0.0f)
        {
            return true;
        }
        return false;
    }

    /**
     * Set the inertia tensor of a body from its inverse inertia tensor
     * 
     * @param inertiaTensor 
     */
    public void setInertiaTensor(Matrix3 inertiaTensor)
    {
        inverseInertiaTensor.setInverse(inertiaTensor);
    }

    /**
     * Get the inertia tensor of a body from its inverse inertia tensor
     * 
     * @param inertiaTensor 
     */
    public void getInertiaTensor(Matrix3 inertiaTensor)
    {
        inertiaTensor.setInverse(inverseInertiaTensor);
    }

    /**
     * Get the inertia tensor of the body
     * @return 
     */
    public Matrix3 getInertiaTensor()
    {
        Matrix3 it = new Matrix3();
        getInertiaTensor(it);
        return it;
    }

    /**
     * Get the inertia tensor of a body in relation to the world via its inverse
     * 
     * @param inertiaTensor 
     */
    public void getInertiaTensorWorld(Matrix3 inertiaTensor)
    {
        inertiaTensor.setInverse(inverseInertiaTensorWorld);
    }

    /**
     * Get the inertia tensor a body in relation to the world
     * @return 
     */
    public Matrix3 getInertiaTensorWorld()
    {
        Matrix3 it = new Matrix3();
        getInertiaTensorWorld(it);
        return it;
    }

    /**
     * Get the inverse inertia tensor of a body
     * 
     * @return 
     */
    public Matrix3 getInverseInertiaTensor()
    {
        return inverseInertiaTensor;
    }

    /**
     * Set the inverse inertia tensor a body directly
     * 
     * @param inverseInertiaTensor 
     */
    public void setInverseInertiaTensor(Matrix3 inverseInertiaTensor)
    {
        this.inverseInertiaTensor = inverseInertiaTensor;
    }

    /**
     * Get the linear damping value of a body
     * 
     * @return 
     */
    public float getLinearDamping()
    {
        return linearDamping;
    }

    /**
     * Set the linear damping value of a body
     * 
     * @param linearDamping 
     */
    public void setLinearDamping(float linearDamping)
    {
        this.linearDamping = linearDamping;
    }

    /**
     * Get the angular damping value of a body
     * 
     * @return 
     */
    public float getAngularDamping()
    {
        return angularDamping;
    }

    /**
     * Set the angular damping value of a body
     * 
     * @param angularDamping 
     */
    public void setAngularDamping(float angularDamping)
    {
        this.angularDamping = angularDamping;
    }

    /**
     * Get the position of the body in the world
     * 
     * @return 
     */
    public Vec getPosition()
    {
        return position;
    }

    /**
     * Set the position of the body in the world
     * 
     * @param position 
     */
    public void setPosition(Vec position)
    {
        this.position = position;
    }

    /**
     * Get the orientation of the body
     * 
     * @return 
     */
    public Quaternion getOrientation()
    {
        return orientation;
    }

    /**
     * Set the orientation of the body
     * 
     * @param orientation 
     */
    public void setOrientation(Quaternion orientation)
    {
        this.orientation = orientation;
    }

    /**
     * Get the velocity of the body
     * 
     * @return 
     */
    public Vec getVelocity()
    {
        return velocity;
    }

    /**
     * Set the velocity of the body
     * 
     * @param velocity 
     */
    public void setVelocity(Vec velocity)
    {
        this.velocity = velocity;
    }

    /**
     * Get the rotation velocity of the body
     * 
     * @return 
     */
    public Vec getRotation()
    {
        return rotation;
    }

    /**
     * Set the rotation velocity of the body
     * 
     * @param rotation 
     */
    public void setRotation(Vec rotation)
    {
        this.rotation = rotation;
    }

    /**
     * Get the inverse inertia tensor of the body in relation to the world directly
     * 
     * @return 
     */
    public Matrix3 getInverseInertiaTensorWorld()
    {
        return inverseInertiaTensorWorld;
    }

    /**
     * Set the inverse inertia tensor of the body in relation to the world directly
     * 
     * @param inverseInertiaTensorWorld 
     */
    public void setInverseInertiaTensorWorld(Matrix3 inverseInertiaTensorWorld)
    {
        this.inverseInertiaTensorWorld = inverseInertiaTensorWorld;
    }

    /**
     * Get the motion of the body
     * 
     * @return 
     */
    public float getMotion()
    {
        return motion;
    }

    /**
     * Set the motion of the body
     * 
     * @param motion 
     */
    public void setMotion(float motion)
    {
        this.motion = motion;
    }

    /**
     * Check if the body is awake from any exerting forces this frame
     * 
     * @return 
     */
    public boolean isAwake()
    {
        return isAwake;
    }

    /**
     * Check if the body is free from exerting forces this frame and can sleep itself
     * 
     * @return 
     */
    public boolean isCanSleep()
    {
        return canSleep;
    }

    /**
     * Set the body into a state where it can sleep this frame
     * 
     * @param canSleep 
     */
    public void setCanSleep(boolean canSleep)
    {
        this.canSleep = canSleep;
    }

    /**
     * Get the transform matrix of the body
     * 
     * @return 
     */
    public Matrix4 getTransformMatrix()
    {
        return transformMatrix;
    }

    /**
     * Set the transform matrix of the body
     * 
     * @param transformMatrix 
     */
    public void setTransformMatrix(Matrix4 transformMatrix)
    {
        this.transformMatrix = transformMatrix;
    }

    /**
     * Get the force accumulator of the body
     * 
     * @return 
     */
    public Vec getForceAccum()
    {
        return forceAccum;
    }

    /**
     * Set the force accumulator of the body to the specified value
     * 
     * @param forceAccum 
     */
    public void setForceAccum(Vec forceAccum)
    {
        this.forceAccum = forceAccum;
    }

    /**
     * Get the torque accumulator of the body
     * 
     * @return 
     */
    public Vec getTorqueAccum()
    {
        return torqueAccum;
    }

    /**
     * Set the torque accumulator of the body to the specified value
     * 
     * @param torqueAccum 
     */
    public void setTorqueAccum(Vec torqueAccum)
    {
        this.torqueAccum = torqueAccum;
    }

    /**
     * Get the current acceleration of the body
     * 
     * @return 
     */
    public Vec getAcceleration()
    {
        return acceleration;
    }

    /**
     * Set the current acceleration of the body
     * 
     * @param acceleration 
     */
    public void setAcceleration(Vec acceleration)
    {
        this.acceleration = new Vec(acceleration);
    }

    /**
     * Get the last frame's acceleration on the body
     * 
     * @return 
     */
    public Vec getLastFrameAcceleration()
    {
        return lastFrameAcceleration;
    }

    /**
     * Set the last frame's acceleration on the body
     * 
     * @param lastFrameAcceleration 
     */
    public void setLastFrameAcceleration(Vec lastFrameAcceleration)
    {
        this.lastFrameAcceleration = lastFrameAcceleration;
    }

    /**
     * Get the transform matrix of the body
     * 
     * @param transform 
     */
    public void getTransform(Matrix4 transform)
    {
        transform = new Matrix4(transformMatrix);
    }

    /**
     * Get the transform matrix of the body from a float array of matrix data
     * 
     * @param matrix 
     */
    public void getTransform(float[] matrix)
    {
        for (int i = 0; i < 12; i++)
        {
                matrix[i] = transformMatrix.data[i];
        }

        matrix[12] = matrix[14] = matrix[14] = 0.0f;
        matrix[15] = 1.0f;
    }

    /**
     * Transform the transform matrix data into an array of data for rendering
     * 
     * @param matrix 
     */
    public void getGLTransform(float[] matrix)
    {
        matrix[0] = transformMatrix.data[0];
        matrix[1] = transformMatrix.data[4];
        matrix[2] = transformMatrix.data[8];
        matrix[3] = 0.0f;

        matrix[4] = transformMatrix.data[1];
        matrix[5] = transformMatrix.data[5];
        matrix[6] = transformMatrix.data[9];
        matrix[7] = 0.0f;

        matrix[8] = transformMatrix.data[2];
        matrix[9] = transformMatrix.data[6];
        matrix[10] = transformMatrix.data[10];
        matrix[11] = 0.0f;

        matrix[12] = transformMatrix.data[3];
        matrix[13] = transformMatrix.data[7];
        matrix[14] = transformMatrix.data[11];
        matrix[15] = 1.0f;
    }

    /**
     * Get the transform matrix of the body
     * 
     * @return 
     */
    public Matrix4 getTransform()
    {
        return transformMatrix;
    }

    /**
     * Calculate the derived data of the body by normalising the orientation, calculating the transform matrix and transforming the inertia tensor
     */
    public void calculateDerivedData()
    {
        orientation.normalise();

        calculateTransformMatrix();
        transformInertiaTensor(inverseInertiaTensorWorld, orientation, inverseInertiaTensor, transformMatrix);
    }

    /**
     * Transform the inertia tensor by the current quaternion, rotation matrix an inverse inertia tensor of the body
     * 
     * @param inverseInertiaTensorWorld
     * @param quat
     * @param inverseInertiaTensorBody
     * @param rotationMatrix 
     */
    private void transformInertiaTensor(Matrix3 inverseInertiaTensorWorld, Quaternion quat, Matrix3 inverseInertiaTensorBody, Matrix4 rotationMatrix)
    {
        float t4 = rotationMatrix.data[0] * inverseInertiaTensorBody.data[0] + rotationMatrix.data[1] * inverseInertiaTensorBody.data[3] + rotationMatrix.data[2] * inverseInertiaTensorBody.data[6];
        float t9 = rotationMatrix.data[0] * inverseInertiaTensorBody.data[1] + rotationMatrix.data[1] * inverseInertiaTensorBody.data[4] + rotationMatrix.data[2] * inverseInertiaTensorBody.data[7];
        float t14 = rotationMatrix.data[0] * inverseInertiaTensorBody.data[2] + rotationMatrix.data[1] * inverseInertiaTensorBody.data[5] + rotationMatrix.data[2] * inverseInertiaTensorBody.data[8];
        float t28 = rotationMatrix.data[4] * inverseInertiaTensorBody.data[0] + rotationMatrix.data[5] * inverseInertiaTensorBody.data[3] + rotationMatrix.data[6] * inverseInertiaTensorBody.data[6];
        float t33 = rotationMatrix.data[4] * inverseInertiaTensorBody.data[1] + rotationMatrix.data[5] * inverseInertiaTensorBody.data[4] + rotationMatrix.data[6] * inverseInertiaTensorBody.data[7];
        float t38 = rotationMatrix.data[4] * inverseInertiaTensorBody.data[2] + rotationMatrix.data[5] * inverseInertiaTensorBody.data[5] + rotationMatrix.data[6] * inverseInertiaTensorBody.data[8];
        float t52 = rotationMatrix.data[8] * inverseInertiaTensorBody.data[0] + rotationMatrix.data[9] * inverseInertiaTensorBody.data[3] + rotationMatrix.data[10] * inverseInertiaTensorBody.data[6];
        float t57 = rotationMatrix.data[8] * inverseInertiaTensorBody.data[1] + rotationMatrix.data[9] * inverseInertiaTensorBody.data[4] + rotationMatrix.data[10] * inverseInertiaTensorBody.data[7];
        float t62 = rotationMatrix.data[8] * inverseInertiaTensorBody.data[2] + rotationMatrix.data[9] * inverseInertiaTensorBody.data[5] + rotationMatrix.data[10] * inverseInertiaTensorBody.data[8];

        inverseInertiaTensorWorld.data[0] = t4 * rotationMatrix.data[0] + t9 * rotationMatrix.data[1] + t14 * rotationMatrix.data[2];
        inverseInertiaTensorWorld.data[1] = t4 * rotationMatrix.data[4] + t9 * rotationMatrix.data[5] + t14 * rotationMatrix.data[6];
        inverseInertiaTensorWorld.data[2] = t4 * rotationMatrix.data[8] + t9 * rotationMatrix.data[9] + t14 * rotationMatrix.data[10];
        inverseInertiaTensorWorld.data[3] = t28 * rotationMatrix.data[0] + t33 * rotationMatrix.data[1] + t38 * rotationMatrix.data[2];
        inverseInertiaTensorWorld.data[4] = t28 * rotationMatrix.data[4] + t33 * rotationMatrix.data[5] + t38 * rotationMatrix.data[6];
        inverseInertiaTensorWorld.data[5] = t28 * rotationMatrix.data[8] + t33 * rotationMatrix.data[9] + t38 * rotationMatrix.data[10];
        inverseInertiaTensorWorld.data[6] = t52 * rotationMatrix.data[0] + t57 * rotationMatrix.data[1] + t62 * rotationMatrix.data[2];
        inverseInertiaTensorWorld.data[7] = t52 * rotationMatrix.data[4] + t57 * rotationMatrix.data[5] + t62 * rotationMatrix.data[6];
        inverseInertiaTensorWorld.data[8] = t52 * rotationMatrix.data[8] + t57 * rotationMatrix.data[9] + t62 * rotationMatrix.data[10];
    }

    /**
     * Calculate the transform matrix via the current orientation of the body
     */
    private void calculateTransformMatrix()
    {
        transformMatrix.data[0] = 1 - 2 * orientation.j * orientation.j - 2 * orientation.k * orientation.k;
        transformMatrix.data[1] = 2 * orientation.i * orientation.j - 2 * orientation.r * orientation.k;
        transformMatrix.data[2] = 2 * orientation.i * orientation.k + 2 * orientation.r * orientation.j;
        transformMatrix.data[3] = position.x;

        transformMatrix.data[4] = 2 * orientation.i * orientation.j + 2 * orientation.r * orientation.k;
        transformMatrix.data[5] = 1 - 2 * orientation.i * orientation.i - 2 * orientation.k * orientation.k;
        transformMatrix.data[6] = 2 * orientation.j * orientation.k - 2 * orientation.r * orientation.i;
        transformMatrix.data[7] = position.y;

        transformMatrix.data[8] = 2 * orientation.i * orientation.k - 2 * orientation.r * orientation.j;
        transformMatrix.data[9] = 2 * orientation.j * orientation.k + 2 * orientation.r * orientation.i;
        transformMatrix.data[10] = 1 - 2 * orientation.i * orientation.i - 2 * orientation.j * orientation.j;
        transformMatrix.data[11] = position.z;
    }

    /**
     * Update the body's physics values for this frame
     * 
     * @param duration 
     */
    public void integrate(float duration)
    {
        if(!isAwake)
        {
            return;
        }

        lastFrameAcceleration = new Vec(acceleration);
        lastFrameAcceleration = lastFrameAcceleration.addScaledVector(forceAccum, inverseMass);
        Vec angularAcceleration = new Vec(inverseInertiaTensorWorld.transform(torqueAccum));

        velocity = velocity.addScaledVector(lastFrameAcceleration, duration);
        rotation = rotation.addScaledVector(angularAcceleration, duration);
        velocity = velocity.multiply((float)Math.pow(linearDamping, duration));
        rotation = rotation.multiply((float)Math.pow(angularDamping, duration));

        position = position.addScaledVector(velocity, duration);
        orientation = orientation.addScaledVector(rotation, duration);

        velocity = velocity.multiply((float)Math.pow(linearDamping, duration));
        rotation = rotation.multiply((float)Math.pow(angularDamping, duration));
        
        calculateDerivedData();
        clearAccumulators();

        if (canSleep)
        {
            float currentMotion = velocity.dotProd(velocity) + rotation.dotProd(rotation);
            float bias = (float)Math.pow(0.5f,  duration);
            motion = bias * motion + (1 - bias) * currentMotion;

            if (motion < sleepEpsilon)
            {
                setAwake(false);
            }
            else if(motion > 10 * sleepEpsilon)
            {
                motion = 10 * sleepEpsilon;
            }
        }
    }

    /**
     * Clear the force and torque accumulators of values for this frame
     */
    public void clearAccumulators()
    {
        forceAccum.clear();
        torqueAccum.clear();
    }

    /**
     * Add a force to the force accumulator
     * 
     * @param force 
     */
    public void addForce(Vec force)
    {
        forceAccum = forceAccum.add(force);
        isAwake = true;
    }

    /**
     * Add a force at a specific point to the force accumulator
     * 
     * @param force
     * @param point 
     */
    public void addForceAtPoint(Vec force, Vec point)
    {
        Vec pointRelative = new Vec(point);
        pointRelative = pointRelative.subtract(position);

        forceAccum = forceAccum.add(force);
        torqueAccum = torqueAccum.add(pointRelative.crossProd(force));

        isAwake = true;

    }

    /**
     * Get the specified point in world space
     * 
     * @param point
     * @return 
     */
    public Vec getPointInWorldSpace(Vec point)
    {
       return transformMatrix.transform(point);
    }

    /**
     * Add a force to the body at the specified point and wake up the body
     * 
     * @param force
     * @param point 
     */
    public void addForceAtBodyPoint(Vec force, Vec point)
    {
        Vec pointWorld = new Vec(getPointInWorldSpace(point));
        addForceAtPoint(force, pointWorld);

        isAwake = true;
    }

    /**
     * Add torque to the torque accumulator and wake up the body
     * 
     * @param torque 
     */
    public void addTorque(Vec torque)
    {
        torqueAccum = torqueAccum.add(torque);
        isAwake = true;
    }

    /**
     * Set the acceleration of the body
     * 
     * @param x
     * @param y
     * @param z 
     */
    public void setAcceleration(float x, float y, float z)
    {
        acceleration.x = x;
        acceleration.y = y;
        acceleration.z = z;
    }

    /**
     * Set the acceleration of the body
     * 
     * @param acceleration 
     */
    public void getAcceleration(Vec acceleration)
    {
        acceleration = this.acceleration;
    }

    /**
     * Set the linear and angular damping values of the body
     * 
     * @param linear
     * @param angular 
     */
    public void setDamping(float linear, float angular)
    {
        linearDamping = linear;
        angularDamping = angular;
    }

    /**
     * Check if the body is awake
     * 
     * @return 
     */
    public boolean getAwake()
    {
        return isAwake;
    }

    /**
     * Wake up the body
     */
    public void setAwake()
    {
        isAwake = true;
    }
    
    /**
     * Set the forces of the body to the current values if awake or asleep
     * 
     * @param awake 
     */
    public void setAwake(boolean awake)
    {
        if(awake)
        {
            isAwake = true;
            motion = sleepEpsilon * 2.0f;
        }
        else
        {
            isAwake = false;
            velocity.clear();
            rotation.clear();
        }
    }

    /**
     * Add velocity to the body
     * 
     * @param amount 
     */
    public void addVelocity(Vec amount)
    {
            velocity = velocity.add(amount);
    }

    /**
     * Add rotation to the body
     * 
     * @param amount 
     */
    public void addRotation(Vec amount)
    {
        rotation = rotation.add(amount);
    }

    /**
     * Get the point in local space of the body
     * 
     * @param point
     * @return 
     */
    public Vec getPointInLocalSpace(Vec point)
    {
        return transformMatrix.transformInverse(point);
    }

    /**
     * Get the direction vector in local space of the body
     * 
     * @param direction
     * @return 
     */
    public Vec getDirectionInLocalSpace(Vec direction)
    {
        return transformMatrix.transformInverseDirection(direction);
    }
}
