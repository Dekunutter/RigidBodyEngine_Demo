package com.base.engine.physics;

import com.base.engine.math.Matrix3;
import com.base.engine.math.Matrix4;
import com.base.engine.math.Quaternion;
import com.base.engine.math.Vec;

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

    public void setMass(float mass)
    {
        if (mass != 0)
        {
            inverseMass = 1.0f / mass;
        }
    }

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

    public void setInverseMass(float inverseMass)
    {
        this.inverseMass = inverseMass;
    }

    public float getInverseMass()
    {
        return inverseMass;
    }

    public boolean hasFiniteMass()
    {
        if (inverseMass >= 0.0f)
        {
            return true;
        }
        return false;
    }

    public void setInertiaTensor(Matrix3 inertiaTensor)
    {
        inverseInertiaTensor.setInverse(inertiaTensor);
    }

    public void getInertiaTensor(Matrix3 inertiaTensor)
    {
        inertiaTensor.setInverse(inverseInertiaTensor);
    }

    public Matrix3 getInertiaTensor()
    {
        Matrix3 it = new Matrix3();
        getInertiaTensor(it);
        return it;
    }

    public void getInertiaTensorWorld(Matrix3 inertiaTensor)
    {
        inertiaTensor.setInverse(inverseInertiaTensorWorld);
    }

    public Matrix3 getInertiaTensorWorld()
    {
        Matrix3 it = new Matrix3();
        getInertiaTensorWorld(it);
        return it;
    }

    public Matrix3 getInverseInertiaTensor()
    {
        return inverseInertiaTensor;
    }

    public void setInverseInertiaTensor(Matrix3 inverseInertiaTensor)
    {
        this.inverseInertiaTensor = inverseInertiaTensor;
    }

    public float getLinearDamping()
    {
        return linearDamping;
    }

    public void setLinearDamping(float linearDamping)
    {
        this.linearDamping = linearDamping;
    }

    public float getAngularDamping()
    {
        return angularDamping;
    }

    public void setAngularDamping(float angularDamping)
    {
        this.angularDamping = angularDamping;
    }

    public Vec getPosition()
    {
        return position;
    }

    public void setPosition(Vec position)
    {
        this.position = position;
    }

    public Quaternion getOrientation()
    {
        return orientation;
    }

    public void setOrientation(Quaternion orientation)
    {
        this.orientation = orientation;
    }

    public Vec getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vec velocity)
    {
        this.velocity = velocity;
    }

    public Vec getRotation()
    {
        return rotation;
    }

    public void setRotation(Vec rotation)
    {
        this.rotation = rotation;
    }

    public Matrix3 getInverseInertiaTensorWorld()
    {
        return inverseInertiaTensorWorld;
    }

    public void setInverseInertiaTensorWorld(Matrix3 inverseInertiaTensorWorld)
    {
        this.inverseInertiaTensorWorld = inverseInertiaTensorWorld;
    }

    public float getMotion()
    {
        return motion;
    }

    public void setMotion(float motion)
    {
        this.motion = motion;
    }

    public boolean isAwake()
    {
        return isAwake;
    }

    public boolean isCanSleep()
    {
        return canSleep;
    }

    public void setCanSleep(boolean canSleep)
    {
        this.canSleep = canSleep;
    }

    public Matrix4 getTransformMatrix()
    {
        return transformMatrix;
    }

    public void setTransformMatrix(Matrix4 transformMatrix)
    {
        this.transformMatrix = transformMatrix;
    }

    public Vec getForceAccum()
    {
        return forceAccum;
    }

    public void setForceAccum(Vec forceAccum)
    {
        this.forceAccum = forceAccum;
    }

    public Vec getTorqueAccum()
    {
        return torqueAccum;
    }

    public void setTorqueAccum(Vec torqueAccum)
    {
        this.torqueAccum = torqueAccum;
    }

    public Vec getAcceleration()
    {
        return acceleration;
    }

    public void setAcceleration(Vec acceleration)
    {
        this.acceleration = new Vec(acceleration);
    }

    public Vec getLastFrameAcceleration()
    {
        return lastFrameAcceleration;
    }

    public void setLastFrameAcceleration(Vec lastFrameAcceleration)
    {
        this.lastFrameAcceleration = lastFrameAcceleration;
    }

    public void getTransform(Matrix4 transform)
    {
        transform = new Matrix4(transformMatrix);
    }

    public void getTransform(float[] matrix)
    {
        for (int i = 0; i < 12; i++)
        {
                matrix[i] = transformMatrix.data[i];
        }

        matrix[12] = matrix[14] = matrix[14] = 0.0f;
        matrix[15] = 1.0f;
    }

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

    public Matrix4 getTransform()
    {
        return transformMatrix;
    }

    public void calculateDerivedData()
    {
        orientation.normalise();

        calculateTransformMatrix();
        transformInertiaTensor(inverseInertiaTensorWorld, orientation, inverseInertiaTensor, transformMatrix);
    }

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

    public void clearAccumulators()
    {
        forceAccum.clear();
        torqueAccum.clear();
    }

    public void addForce(Vec force)
    {
        forceAccum = forceAccum.add(force);
        isAwake = true;
    }

    public void addForceAtPoint(Vec force, Vec point)
    {
        Vec pointRelative = new Vec(point);
        pointRelative = pointRelative.subtract(position);

        forceAccum = forceAccum.add(force);
        torqueAccum = torqueAccum.add(pointRelative.crossProd(force));

        isAwake = true;

    }

    public Vec getPointInWorldSpace(Vec point)
    {
       return transformMatrix.transform(point);
    }

    public void addForceAtBodyPoint(Vec force, Vec point)
    {
        Vec pointWorld = new Vec(getPointInWorldSpace(point));
        addForceAtPoint(force, pointWorld);

        isAwake = true;
    }

    public void addTorque(Vec torque)
    {
        torqueAccum = torqueAccum.add(torque);
        isAwake = true;
    }


    public void setAcceleration(float x, float y, float z)
    {
        acceleration.x = x;
        acceleration.y = y;
        acceleration.z = z;
    }

    public void getAcceleration(Vec acceleration)
    {
        acceleration = this.acceleration;
    }

    public void setDamping(float linear, float angular)
    {
        linearDamping = linear;
        angularDamping = angular;
    }

    public boolean getAwake()
    {
        return isAwake;
    }

    public void setAwake()
    {
        isAwake = true;
    }
    
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

    public void addVelocity(Vec amount)
    {
            velocity = velocity.add(amount);
    }

    public void addRotation(Vec amount)
    {
        rotation = rotation.add(amount);
    }

    public Vec getPointInLocalSpace(Vec point)
    {
        return transformMatrix.transformInverse(point);
    }

    public Vec getDirectionInLocalSpace(Vec direction)
    {
        return transformMatrix.transformInverseDirection(direction);
    }
}
