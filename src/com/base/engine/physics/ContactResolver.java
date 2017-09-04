package com.base.engine.physics;

import com.base.engine.math.Vec;
import java.util.ArrayList;

public class ContactResolver
{
    protected int velocityIterations;
    protected int positionIterations;
    protected float velocityEpsilon;
    protected float positionEpsilon;

    public int velocityIterationsUsed;
    public int positionIterationsUsed;

    public ContactResolver(int i)
    {
        setIterations(i, i);
    }

    public final void setIterations(int velocityIterations, int positionIterations)
    {
        this.velocityIterations = velocityIterations;
        this.positionIterations = positionIterations;
        
        positionEpsilon = 0.01f;
        velocityEpsilon = 0.01f;
    }

    public void resolveContacts(ArrayList<Contact> contacts, float duration)
    {
        if (contacts.isEmpty())
        {
            return;
        }
        if (!isValid())
        {
            return;
        }

        prepareContacts(contacts, duration);

        adjustPositions(contacts);
        adjustVelocities(contacts, duration);
    }

    private void adjustVelocities(ArrayList<Contact> contacts, float duration) 
    {
        Vec[] velocityChange = new Vec[2];
        Vec[] rotationChange = new Vec[2];

        velocityChange[0] = new Vec();
        velocityChange[1] = new Vec();

        rotationChange[0] = new Vec();
        rotationChange[1] = new Vec();

        

        //velocityIterations = contacts.size();
        velocityIterations = 15;
        velocityIterationsUsed = 0;

        while(velocityIterationsUsed < velocityIterations)
        {
            double max = velocityEpsilon;
            int index = contacts.size();

            for(int i = 0; i < contacts.size(); i++)
            {
                if(contacts.get(i).desiredDeltaVelocity > max)
                {
                    max = contacts.get(i).desiredDeltaVelocity;
                    index = i;
                }
            }
            if(index == contacts.size())
            {
                break;
            }

            contacts.get(index).matchAwakeState();
            contacts.get(index).applyVelocityChange(velocityChange, rotationChange);

            Vec deltaVel;
            for(int i = 0; i < contacts.size(); i++)
            {
                for(int j = 0; j < 2; j++)
                {
                    if(contacts.get(i).body[j] != null)
                    {
                        for(int k = 0; k < 2; k++)
                        {
                            if(contacts.get(i).body[j] == contacts.get(index).body[k])
                            {
                                deltaVel = new Vec(velocityChange[k]);
                                deltaVel = deltaVel.add(rotationChange[k].crossProd(contacts.get(i).relativeContactPosition[j]));

                                float sign;
                                if (j == 1)
                                {
                                    sign = -1.0f;
                                }
                                else
                                {
                                    sign = 1.0f;
                                }

                                Vec temp = new Vec(contacts.get(i).contactToWorld.transformTranspose(deltaVel));
                                temp = temp.multiply(sign);

                                contacts.get(i).contactVelocity = contacts.get(i).contactVelocity.add(temp);
                                contacts.get(i).calculateDesiredDeltaVelocity(duration);
                            }
                        }

                    }
                }
            }
            velocityIterationsUsed++;
        }
    }



    private void adjustPositions(ArrayList<Contact> contacts) 
    {
        int index;
        float max;

        Vec[] linearChange = new Vec[2];
        Vec[] angularChange = new Vec[2];

        linearChange[0] = new Vec();
        linearChange[1] = new Vec();
        angularChange[0] = new Vec();
        angularChange[1] = new Vec();

        Vec deltaPosition = new Vec();

        positionIterations = 15;
        positionIterationsUsed = 0;

        while(positionIterationsUsed < positionIterations)
        {
            max = positionEpsilon;
            index = contacts.size();

            for(int i = 0; i < contacts.size(); i++)
            {
                if(contacts.get(i).penetration > max)
                {
                    max = contacts.get(i).penetration;
                    index = i;
                }
            }
            if(index >= contacts.size())
            {
                break;
            }

            contacts.get(index).matchAwakeState();
            contacts.get(index).applyPositionChange(linearChange, angularChange, max);

            for(int i = 0; i < contacts.size(); i++)
            {
                for(int b = 0; b < 2; b++)
                {
                    if(contacts.get(i).body[b] != null)
                    {
                        for(int d = 0; d < 2; d++)
                        {
                            if(contacts.get(i).body[b] == contacts.get(index).body[d])
                            {
                                deltaPosition = new Vec(linearChange[d]);
                                deltaPosition = deltaPosition.add(angularChange[d].crossProd(contacts.get(i).relativeContactPosition[b]));

                                if(b == 0)
                                {
                                    contacts.get(i).penetration -= deltaPosition.dotProd(contacts.get(i).contactNormal);
                                }
                                else
                                {
                                    contacts.get(i).penetration += deltaPosition.dotProd(contacts.get(i).contactNormal);
                                }
                            }
                        }
                    }
                }
            }
            positionIterationsUsed++;
        }
    }

    private void prepareContacts(ArrayList<Contact> contacts, float duration)
    {
        for (int i = 0; i < contacts.size(); i++)
        {
            contacts.get(i).calculateInternals(duration);
        }
    }

    private boolean isValid()
    {
        return (velocityIterations > 0) && (positionIterations > 0) && (positionEpsilon >= 0.0f) && (velocityEpsilon >= 0.0f);
    }
}
