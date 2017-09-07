package com.base.engine.math;

/**
 * Object class for storing quaternion data and performing operations on quaternions
 * 
 * @author JordanG
 */
public class Quaternion
{
    public float r;
    public float i;
    public float j;
    public float k;

    /**
     * Initialise quaternion with base values
     */
    public Quaternion()
    {
        r = 1.0f;
        i = 0.0f;
        j = 0.0f;
        k = 0.0f;
    }

    /**
     * Initialise quaternion from another
     * 
     * @param other 
     */
    public Quaternion(Quaternion other)
    {
        r = other.r;
        i = other.i;
        j = other.j;
        k = other.k;
    }

    /**
     * Initialise quaterion with individual values
     * 
     * @param r
     * @param i
     * @param j
     * @param k 
     */
    public Quaternion(float r, float i, float j, float k)
    {
        this.r = r;
        this.i = i;
        this.j = j;
        this.k = k;
    }

    /**
     * Normalise this quaternion
     */
    public void normalise()
    {
        float d = r * r + i * i + j * j + k * k;

        if (d == 0)
        {
            r = 1;
            return;
        }

        d = 1.0f / (float)Math.sqrt(d);

        r *= d;
        i *= d;
        j *= d;
        k *= d;
    }

    /**
     * Multiply this quaternion by another
     * 
     * @param multiplier
     * @return Multiplied quaternion 
     */
    public Quaternion multiply(Quaternion multiplier)
    {
        Quaternion copy = new Quaternion(this);

        float r = copy.r * multiplier.r - copy.i * multiplier.i - copy.j * multiplier.j - copy.k * multiplier.k;
        float i = copy.r * multiplier.i + copy.i * multiplier.r + copy.j * multiplier.k - copy.k * multiplier.j;
        float j = copy.r * multiplier.j + copy.j * multiplier.r + copy.k * multiplier.i - copy.i * multiplier.k;
        float k = copy.r * multiplier.k + copy.k * multiplier.r + copy.i * multiplier.j - copy.j * multiplier.i;

        return new Quaternion(r, i, j, k);
    }

    /**
     * Multiply this quaternion by a new scale
     * 
     * @param scale
     * @return 
     */
    public Quaternion scale(float scale)
    {
        return new Quaternion(this.r * scale, this.i * scale, this.j * scale, this.k * scale);
    }
    
    /**
     * Get the inner product of this quaternion by multiplying it by another
     * 
     * @param multiplier
     * @return 
     */
    public float innerProduct(Quaternion multiplier)
    {
        Quaternion copy = new Quaternion(this);

        return copy.r * multiplier.r + copy.i * multiplier.i + copy.j * multiplier.j + copy.k * multiplier.k;
    }

    /**
     * Conjugate this quaternion by inversing most of its values
     * @return 
     */
    public Quaternion conjugate()
    {
            return new Quaternion(this.r, -this.i, -this.j, -this.k);
    }
    
    /**
     * Add a scaled vector to this quaternion
     * 
     * @param vector
     * @param scale
     * @return 
     */
    public Quaternion addScaledVector(Vec vector, float scale)
    {
            Quaternion q = new Quaternion(0.0f, vector.x * scale, vector.y * scale, vector.z * scale);

            Quaternion results = q.multiply(this);

            results.r = r + results.r * 0.5f;
            results.i = i + results.i * 0.5f;
            results.j = j + results.j * 0.5f;
            results.k = k + results.k * 0.5f;
            
            return results;
    }
}
