package com.base.engine.physics;

import com.base.engine.math.Matrix3;
import com.base.engine.math.Quaternion;
import com.base.engine.math.Vec;

/**
 * Data relating to a single contact during collision detection
 * 
 * @author JordanG
 */
public class Contact
{	
    public static final float velocityLimit = 0.25f;
    public static final float angularLimit = 0.2f;
    
    public Body[] body;
    public float friction;
    public float restitution;
    
    public Vec contactPoint;
    public Vec contactNormal;
    public float penetration;
    protected Matrix3 contactToWorld;
    protected Vec contactVelocity;
    
    protected float desiredDeltaVelocity;
    protected Vec[] relativeContactPosition;

    public Contact()
    {
        body = new Body[2];
        
        contactPoint = new Vec();
        contactNormal = new Vec();
        contactToWorld = new Matrix3();
        contactVelocity = new Vec();
        relativeContactPosition = new Vec[2];
    }

    public void setBodyData(Body one, Body two, float friction, float restitution)
    {
        body[0] = one;
        body[1] = two;
        this.friction = friction;
        this.restitution = restitution;
    }

    /**
     * Calculate basic collision data
     * 
     * @param duration 
     */
    public void calculateInternals(float duration)
    {
        if(body[0] == null)
        {
            swapBodies();
        }

        if(body[0] != null)
        {
            calculateContactBasis();

            relativeContactPosition[0] = new Vec(contactPoint);
            relativeContactPosition[0] = relativeContactPosition[0].subtract(body[0].getPosition());

            if(body[1] != null)
            {
                relativeContactPosition[1] = new Vec(contactPoint);
                relativeContactPosition[1] = relativeContactPosition[1].subtract(body[1].getPosition());
            }

            contactVelocity = calculateLocalVelocity(0, duration);

            if(body[1] != null)
            {
                contactVelocity = contactVelocity.subtract(calculateLocalVelocity(1, duration));
            }

            calculateDesiredDeltaVelocity(duration);
        }

    }

    /**
     * Calculate resulting velocity of the collision
     * 
     * @param duration 
     */
    void calculateDesiredDeltaVelocity(float duration)
    {
        float velocityFromAcc = 0.0f;

        if(body[0].getAwake())
        {
            Vec lfa = new Vec(body[0].getLastFrameAcceleration());
            lfa = lfa.multiply(duration);
            velocityFromAcc = lfa.dotProd(contactNormal);
        }

        if(body[1] != null && body[1].getAwake())
        {
            Vec lfa2 = new Vec(body[1].getLastFrameAcceleration());
            lfa2 = lfa2.multiply(duration);

            velocityFromAcc -= lfa2.dotProd(contactNormal);
        }

        float thisRestitution = restitution;
        if(Math.abs(contactVelocity.x) < velocityLimit)
        {
                thisRestitution = 0.0f;
        }

        desiredDeltaVelocity = -contactVelocity.x - thisRestitution * (contactVelocity.x - velocityFromAcc);
    }


    private Vec calculateLocalVelocity(int index, float duration)
    {
        Body thisBody = body[index];

        Vec velocity = new Vec(thisBody.getRotation().crossProd(relativeContactPosition[index]));
        velocity = velocity.add(thisBody.getVelocity());

        Vec localVelocity = contactToWorld.transformTranspose(velocity);

        Vec accVelocity = thisBody.getLastFrameAcceleration().multiply(duration);

        accVelocity = contactToWorld.transformTranspose(accVelocity);

        accVelocity.x = 0.0f;

        localVelocity = localVelocity.add(accVelocity);

        return localVelocity;
    }

    private void calculateContactBasis()
    {
        Vec[] contactTangent = new Vec[2];
        contactTangent[0] = new Vec();
        contactTangent[1] = new Vec();

        if(Math.abs(contactNormal.x) > Math.abs(contactNormal.y))
        {
            float scale = 1.0f / (float)Math.sqrt(contactNormal.z * contactNormal.z + contactNormal.x * contactNormal.x);

            contactTangent[0].x = contactNormal.z * scale;
            contactTangent[0].y = 0.0f;
            contactTangent[0].z = -contactNormal.x * scale;

            contactTangent[1].x = contactNormal.z * contactTangent[0].x;
            contactTangent[1].y = contactNormal.z * contactTangent[0].x - contactNormal.x * contactTangent[0].z;
            contactTangent[1].z = -contactNormal.y * contactTangent[0].x;
        }
        else
        {
            float scale = 1.0f / (float)Math.sqrt(contactNormal.z * contactNormal.z + contactNormal.y * contactNormal.y);

            contactTangent[0].x = 0.0f;
            contactTangent[0].y = -contactNormal.z * scale;
            contactTangent[0].z = contactNormal.y * scale;

            contactTangent[1].x = contactNormal.y * contactTangent[0].z - contactNormal.z * contactTangent[0].y;
            contactTangent[1].y = -contactNormal.x * contactTangent[0].z;
            contactTangent[1].z = contactNormal.x * contactTangent[0].y;
        }

        contactToWorld.setComponents(contactNormal, contactTangent[0], contactTangent[1]);
    }

    private void swapBodies()
    {
        contactNormal = contactNormal.multiply(-1.0f);

        Body temp = body[0];
        body[0] = body[1];
        body[1] = temp;
    }

    /**
     * Wake up any sleeping bodies
     */
    public void matchAwakeState()
    {
        if(body[1] == null)
        {
            return;
        }

        boolean body0awake = body[0].getAwake();
        boolean body1awake = body[1].getAwake();

        if(body0awake ^ body1awake)
        {
            if(body0awake)
            {
                body[1].setAwake();
            }
            else
            {
                body[0].setAwake();
            }
        }
    }

    /**
     * Apply the new positions to the collided bodies
     * 
     * @param linearChange
     * @param angularChange
     * @param max 
     */
    public void applyPositionChange(Vec[] linearChange, Vec[] angularChange, float max)
    {
        float[] angularMove = new float[2];
        float[] linearMove = new float[2];

        float totalInertia = 0.0f;

        float[] linearInertia = new float[2];
        float[] angularInertia = new float[2];

        for (int i = 0; i < 2; i++)
        {
            if (body[i] != null)
            {
                Vec angularInertiaWorld = new Vec(relativeContactPosition[i]);
                angularInertiaWorld = angularInertiaWorld.crossProd(contactNormal);
                angularInertiaWorld = body[i].getInverseInertiaTensorWorld().transform(angularInertiaWorld);
                angularInertiaWorld = angularInertiaWorld.crossProd(relativeContactPosition[i]);
                angularInertia[i] = angularInertiaWorld.dotProd(contactNormal);

                linearInertia[i] = body[i].getInverseMass();

                totalInertia += linearInertia[i] + angularInertia[i];
            }
        }

        for(int i = 0; i < 2; i++)
        {
            if(body[i] != null)
            {
                float sign = (i == 0) ? 1.0f : -1.0f;

                angularMove[i] = sign * penetration * (angularInertia[i]/totalInertia);
                linearMove[i] = sign * penetration * (linearInertia[i]/totalInertia);

                Vec projection = new Vec(relativeContactPosition[i]);
                projection = projection.addScaledVector(contactNormal, -relativeContactPosition[i].dotProd(contactNormal));

                float maxMagnitude = angularLimit * projection.magnitude();

                if(angularMove[i] < -maxMagnitude)
                {
                    float totalMove = angularMove[i] + linearMove[i];
                    angularMove[i] = -maxMagnitude;
                    linearMove[i] = totalMove - angularMove[i];
                }
                else if(angularMove[i] > maxMagnitude)
                {
                    float totalMove = angularMove[i] + linearMove[i];
                    angularMove[i] = maxMagnitude;
                    linearMove[i] = totalMove - angularMove[i];
                }

                if (angularMove[i] == 0.0f)
                {
                    angularChange[i].clear();
                }
                else
                {
                    Vec targetAngularDirection = relativeContactPosition[i].crossProd(contactNormal);

                    Vec transform = new Vec(body[i].getInverseInertiaTensorWorld().transform(targetAngularDirection));
                    transform = transform.multiply(angularMove[i]/angularInertia[i]);
                    angularChange[i] = new Vec(transform);
                }

                linearChange[i] = contactNormal.multiply(linearMove[i]);

                Vec position = new Vec(body[i].getPosition());
                position = position.addScaledVector(contactNormal, linearMove[i]);
                body[i].setPosition(position);

                Quaternion quat = new Quaternion(body[i].getOrientation());
                quat = quat.addScaledVector(angularChange[i],  1.0f);
                body[i].setOrientation(quat);

                if(!body[i].getAwake())
                {
                    body[i].calculateDerivedData();
                }
            }
        }
    }

    /**
     * Apply the new velocities to the collided bodies
     * 
     * @param velocityChange
     * @param rotationChange 
     */
    public void applyVelocityChange(Vec[] velocityChange, Vec[] rotationChange)
    {
        Matrix3[] inverseInertiaTensor = new Matrix3[2];

        inverseInertiaTensor[0] = new Matrix3();
        inverseInertiaTensor[0] = body[0].getInverseInertiaTensorWorld();

        if(body[1] != null)
        {
            inverseInertiaTensor[1] = new Matrix3();
            inverseInertiaTensor[1] = body[1].getInverseInertiaTensorWorld();
        }

        Vec impulseContact;

        if(friction == 0.0f)
        {
            impulseContact = new Vec(calculateFrictionlessImpulse(inverseInertiaTensor));
        }
        else
        {
            impulseContact = new Vec(calculateFrictionImpulse(inverseInertiaTensor));
        }

        Vec impulse = new Vec(contactToWorld.transform(impulseContact));
        Vec impulsiveTorque = relativeContactPosition[0].crossProd(impulse);
   
        rotationChange[0] = new Vec(inverseInertiaTensor[0].transform(impulsiveTorque));
        velocityChange[0] = new Vec();
        velocityChange[0] = velocityChange[0].addScaledVector(impulse, body[0].getInverseMass());

        body[0].addVelocity(velocityChange[0]);
        body[0].addRotation(rotationChange[0]);

        if(body[1] != null)
        {
            impulsiveTorque = impulse.crossProd(relativeContactPosition[1]);
            rotationChange[1] = inverseInertiaTensor[1].transform(impulsiveTorque);
            velocityChange[1] = new Vec();
            velocityChange[1] = velocityChange[1].addScaledVector(impulse, -body[1].getInverseMass());

            body[1].addVelocity(velocityChange[1]);
            body[1].addRotation(rotationChange[1]);
        }
    }

    /**
     * Calculate a collision impulse to apply to the colliding bodies that includes friction
     * 
     * @param inverseInertiaTensor
     * @return 
     */
    private Vec calculateFrictionImpulse(Matrix3[] inverseInertiaTensor)
    {
        float inverseMass = body[0].getInverseMass();

        Matrix3 impulseToTorque = new Matrix3();

        impulseToTorque.setSkewSymmetric(relativeContactPosition[0]);

        Matrix3 deltaVelWorld = new Matrix3(impulseToTorque);
        deltaVelWorld = deltaVelWorld.multiply(inverseInertiaTensor[0]);
        deltaVelWorld = deltaVelWorld.multiply(impulseToTorque);
        deltaVelWorld.multiply(-1.0f);

        if(body[1] != null)
        {
            impulseToTorque.setSkewSymmetric(relativeContactPosition[1]);

            Matrix3 deltaVelWorld2 = new Matrix3(impulseToTorque);
            deltaVelWorld2 = deltaVelWorld2.multiply(inverseInertiaTensor[1]);
            deltaVelWorld2 = deltaVelWorld2.multiply(impulseToTorque);
            deltaVelWorld2.multiply(-1.0f);

            deltaVelWorld = deltaVelWorld.add(deltaVelWorld2);

            inverseMass += body[1].getInverseMass();
        }

        Matrix3 deltaVelocity = new Matrix3(contactToWorld.transpose());
        deltaVelocity = deltaVelocity.multiply(deltaVelWorld);
        deltaVelocity = deltaVelocity.multiply(contactToWorld);

        deltaVelocity.data[0] += inverseMass;
        deltaVelocity.data[4] += inverseMass;
        deltaVelocity.data[8] += inverseMass;

        Matrix3 impulseMatrix = new Matrix3(deltaVelocity.inverse());

        Vec velKill = new Vec(desiredDeltaVelocity, -contactVelocity.y, -contactVelocity.z);

        Vec impulseContact = new Vec(impulseMatrix.transform(velKill));

        float planarImpulse = (float)Math.sqrt(impulseContact.y * impulseContact.y + impulseContact.z * impulseContact.z);

        if(planarImpulse > impulseContact.x * friction)
        {
            impulseContact.y /= planarImpulse;
            impulseContact.z /= planarImpulse;

            impulseContact.x = (deltaVelocity.data[0] + deltaVelocity.data[1]*friction*impulseContact.y + deltaVelocity.data[2]*friction*impulseContact.z);
            impulseContact.x = desiredDeltaVelocity / impulseContact.x;
            impulseContact.y *= friction*impulseContact.x;
            impulseContact.z *= friction*impulseContact.x;
        }

        return impulseContact;
    }

    /**
     * Calculate an impulse to apply to the colliding bodies that does not include friction
     * @param inverseInertiaTensor
     * @return 
     */
    private Vec calculateFrictionlessImpulse(Matrix3[] inverseInertiaTensor)
    {
        Vec impulseContact = new Vec();

        Vec deltaVelWorld = relativeContactPosition[0].crossProd(contactNormal);
        deltaVelWorld = inverseInertiaTensor[0].transform(deltaVelWorld);
        deltaVelWorld = deltaVelWorld.crossProd(relativeContactPosition[0]);

        float deltaVelocity = deltaVelWorld.dotProd(contactNormal);

        deltaVelocity += body[0].getInverseMass();

        if(body[1] != null)
        {
            deltaVelWorld = relativeContactPosition[1].crossProd(contactNormal);
            deltaVelWorld = inverseInertiaTensor[1].transform(deltaVelWorld);
            deltaVelWorld = deltaVelWorld.crossProd(relativeContactPosition[1]);

            deltaVelocity += deltaVelWorld.dotProd(contactNormal);

            deltaVelocity += body[1].getInverseMass();
        }

        impulseContact.x = desiredDeltaVelocity / deltaVelocity;
        impulseContact.y = 0.0f;
        impulseContact.z = 0.0f;

        return impulseContact;
    }
}
