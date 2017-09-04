package com.base.engine.physics;

import java.util.ArrayList;

public class ForceRegistry
{
    protected ArrayList<ForceRegistration> registrations;

    public ForceRegistry()
    {
        registrations = new ArrayList<ForceRegistration>();
    }

    public void Add(Body body, ForceGenerator generator)
    {
        ForceRegistration registration = new ForceRegistration();
        registration.body = body;
        registration.generator = generator;
        registrations.add(registration);
    }

    public void Clear()
    {
        registrations.clear();
    }

    public void UpdateForces(float duration)
    {
        for (int i = 0; i < registrations.size(); i++)
        {
                registrations.get(i).generator.UpdateForce(registrations.get(i).body, duration);
        }
    }
}
