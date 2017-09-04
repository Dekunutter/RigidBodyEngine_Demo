package com.base.engine.math;

public class Vec
{
    public float x;
    public float y;
    public float z;

    public Vec()
    {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public Vec(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec(Vec other)
    {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public void clear()
    {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public Vec multiply(float scale)
    {
        return new Vec(x*scale, y*scale, z*scale);
    }

    public Vec addScaledVector(Vec other, float scale)
    {
        Vec scaled = new Vec(other.x * scale, other.y * scale, other.z * scale);
        return add(scaled);
    }
    
    public Vec add(Vec other)
    {
        return new Vec(x + other.x, y + other.y, z + other.z);
    }

    public float magnitude()
    {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public float squaredMagnitude()
    {
        return (x * x) + (y * y) + (z * z);
    }

    public Vec normalize()
    {
        float length = magnitude();

        if (length > 0.0f)
        {
            x *= 1.0f / length;
            y *= 1.0f / length;
            z *= 1.0f / length;
            return new Vec(x, y, z);
        }
        return this;
    }
    
    public Vec subtract(Vec other)
    {
        return new Vec(x - other.x, y - other.y, z - other.z);
    }

    public float dotProd(Vec other)
    {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public Vec multiply(Vec other)
    {
        return new Vec(x * other.x, y * other.y, z * other.z);
    }

    public Vec crossProd(Vec other)
    {
        return new Vec((y * other.z) - (z * other.y), (z * other.x) - (x * other.z), (x * other.y) - (y * other.x));
    }
    
    public Vec invert()
    {
        return new Vec(x * -1, y * -1, z * -1);
    }

    public boolean equals(Vec other)
    {
        boolean results = false;

        if ((x == other.x) && (y == other.y) && (z == other.z))
        {
            results = true;
        }

        return results;
    }

    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}

