package com.base.engine.math;

/**
 * Object class for storing all data related to vectors and performing vector operations
 * 
 * @author JordanG
 */
public class Vec
{
    public float x;
    public float y;
    public float z;

    /**
     * Initialise a vector at (0, 0, 0)
     */
    public Vec()
    {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    /**
     * Initialise a vector at the given co-ordinates
     * 
     * @param x
     * @param y
     * @param z 
     */
    public Vec(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Initialise a vector at the co-ordinates of another
     * 
     * @param other 
     */
    public Vec(Vec other)
    {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    /**
     * Set the current vector to (0, 0, 0)
     */
    public void clear()
    {
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    /**
     * Multiply this vector by a scalar
     * 
     * @param scale
     * @return 
     */
    public Vec multiply(float scale)
    {
        return new Vec(x*scale, y*scale, z*scale);
    }

    /**
     * Add a scaled vector to this vector
     * 
     * @param other
     * @param scale
     * @return 
     */
    public Vec addScaledVector(Vec other, float scale)
    {
        Vec scaled = new Vec(other.x * scale, other.y * scale, other.z * scale);
        return add(scaled);
    }
    
    /**
     * Add another vector to this vector
     * 
     * @param other
     * @return 
     */
    public Vec add(Vec other)
    {
        return new Vec(x + other.x, y + other.y, z + other.z);
    }

    /**
     * Get the length of this vector
     * 
     * @return 
     */
    public float magnitude()
    {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    /**
     * Get the squared length of this vector
     * 
     * @return 
     */
    public float squaredMagnitude()
    {
        return (x * x) + (y * y) + (z * z);
    }

    /**
     * Normalise this vector
     * 
     * @return 
     */
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
    
    /**
     * Subtract another vector from this vector
     * 
     * @param other
     * @return 
     */
    public Vec subtract(Vec other)
    {
        return new Vec(x - other.x, y - other.y, z - other.z);
    }

    /**
     * Get the dot product of two vectors
     * 
     * @param other
     * @return 
     */
    public float dotProd(Vec other)
    {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    /**
     * Multiply this vector by another
     * 
     * @param other
     * @return 
     */
    public Vec multiply(Vec other)
    {
        return new Vec(x * other.x, y * other.y, z * other.z);
    }

    /**
     * Get the cross product of two vectors
     * 
     * @param other
     * @return 
     */
    public Vec crossProd(Vec other)
    {
        return new Vec((y * other.z) - (z * other.y), (z * other.x) - (x * other.z), (x * other.y) - (y * other.x));
    }
    
    /**
     * Get inverse of this vector
     * 
     * @return 
     */
    public Vec invert()
    {
        return new Vec(x * -1, y * -1, z * -1);
    }

    /**
     * Check if this vector's co-ordinates equals another's
     * 
     * @param other
     * @return 
     */
    public boolean equals(Vec other)
    {
        boolean results = false;

        if ((x == other.x) && (y == other.y) && (z == other.z))
        {
            results = true;
        }

        return results;
    }

    /**
     * Convert this vector to a string
     * 
     * @return 
     */
    @Override
    public String toString()
    {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}

