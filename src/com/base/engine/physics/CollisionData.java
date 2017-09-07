package com.base.engine.physics;

import java.util.ArrayList;

/**
 * Stores data of all collisions for the current frame
 * 
 * @author JordanG
 */
public class CollisionData
{
    public int contactArrayIndex;

    public ArrayList<Contact> contacts;

    public float friction;

    public float restitution;

    public float tolerance;

    public CollisionData()
    {
        contacts = new ArrayList<Contact>();
    }

    public boolean hasMoreContacts()
    {
        return true;
    }

    public void reset()
    {
        contacts.clear();
    }
}
