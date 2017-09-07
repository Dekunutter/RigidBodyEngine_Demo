package com.base.engine.math;

/**
 * Stores data in 4-column matrices and has all the related operations
 * 
 * @author JordanG
 */
public class Matrix4
{
    public float[] data;

    /**
     * Initialise the matrix with 0 values
     */
    public Matrix4()
    {
        data = new float[12];

        data[1] = data[2] = data[3] = data[4] = data[6] = data[7] = data[8] = data[9] = data[11] = 0.0f;
        data[0] = data[5] = data[10] = 1.0f;
    }

    /**
     * Initialise the matrix with data from another
     * 
     * @param other Other matrix we are copying over
     */
    public Matrix4(Matrix4 other)
    {
        data = new float[12];

        for(int i = 0; i < 12; i++)
        {
            data[i] = other.data[i];
        }
    }
    
    /**
     * Set the diagonal of the matrix by setting the coefficients
     * 
     * @param a
     * @param b
     * @param c 
     */
    public void setDiagonal(float a, float b, float c)
    {
        data[0] = a;
        data[5] = b;
        data[10] = c;
    }

    /**
     * Multiply this matrix by another
     * 
     * @param other
     * @return 
     */
    public Matrix4 multiply(Matrix4 other)
    {
        Matrix4 result = new Matrix4();

        result.data[0] = (other.data[0] * data[0]) + (other.data[4] * data[1]) + (other.data[8] * data[2]);
        result.data[4] = (other.data[0] * data[4]) + (other.data[4] * data[5]) + (other.data[8] * data[6]);
        result.data[8] = (other.data[0] * data[8]) + (other.data[4] * data[9]) + (other.data[8] * data[10]);

        result.data[1] = (other.data[1] * data[0]) + (other.data[5] * data[1]) + (other.data[9] * data[2]);
        result.data[5] = (other.data[1] * data[4]) + (other.data[5] * data[5]) + (other.data[9] * data[6]);
        result.data[9] = (other.data[1] * data[8]) + (other.data[5] * data[9]) + (other.data[9] * data[10]);

        result.data[2] = (other.data[2] * data[0]) + (other.data[6] * data[1]) + (other.data[10] * data[2]);
        result.data[6] = (other.data[2] * data[4]) + (other.data[6] * data[5]) + (other.data[10] * data[6]);
        result.data[10] = (other.data[2] * data[8]) + (other.data[6] * data[9]) + (other.data[10] * data[10]);

        result.data[3] = (other.data[3] * data[0]) + (other.data[7] * data[1]) + (other.data[11] * data[2]) + data[3];
        result.data[7] = (other.data[3] * data[4]) + (other.data[7] * data[5]) + (other.data[11] * data[6]) + data[7];
        result.data[11] = (other.data[3] * data[8]) + (other.data[7] * data[9]) + (other.data[11] * data[10]) + data[11];

        return result;
    }

    /**
     * Multiply this matrix by a vector, transforming the vector to a new position
     * 
     * @param vector
     * @return 
     */
    public Vec multiply(Vec vector)
    {
        float x = vector.x * data[0] + vector.y * data[1] + vector.z * data[2] + data[3];
        float y = vector.x * data[4] + vector.y * data[5] + vector.z * data[6] + data[7];
        float z = vector.x * data[8] + vector.y * data[9] + vector.z * data[10] + data[11];
        return new Vec(x, y, z);
    }

    /**
     * Transform the specified vector by this matrix
     * 
     * @param vector
     * @return 
     */
    public Vec transform(Vec vector)
    {
        return this.multiply(vector);
    }

    /**
     * Get determinant of this matrix
     * 
     * @return 
     */
    public float getDeterminant()
    {
        return data[8] * data[5] * data[2] + data[4] * data[9] * data[2] + data[8] * data[1] * data[6] - data[0] * data[9] * data[6] - data[4] * data[1] * data[10] + data[0] * data[5] * data[10];
    }

    /**
     * Setup this matrix to be the inverse of the specified one
     * 
     * @param other 
     */
    public void setInverse(Matrix4 other)
    {
        float det = getDeterminant();

        if (det == 0.0f)
        {
            return;
        }
        det = 1.0f / det;

        data[0] = (-other.data[9] * other.data[6] + other.data[5] * other.data[10]) * det;
        data[4] = (other.data[8] * other.data[6] - other.data[4] * other.data[10]) * det;
        data[8] = (-other.data[8] * other.data[5] + other.data[4] * other.data[9] * other.data[15]) * det;

        data[1] = (other.data[9] * other.data[2] - other.data[1] * other.data[10]) * det;
        data[5] = (-other.data[8] * other.data[2] + other.data[0] * other.data[10]) * det;
        data[9] = (other.data[8] * other.data[1] - other.data[0] * other.data[9] * other.data[15]) * det;

        data[2] = (-other.data[5] * other.data[2] + other.data[1] * other.data[6] * other.data[15]) * det;
        data[6] = (other.data[4] * other.data[2] - other.data[0] * other.data[6] * other.data[15]) * det;
        data[10] = (-other.data[4] * other.data[1] + other.data[0] * other.data[5] * other.data[15]) * det;

        data[3] = (other.data[9] * other.data[6] * other.data[3] - other.data[5] * other.data[10] * other.data[3] - other.data[9] * other.data[2] * other.data[7] + other.data[1] * other.data[10] * other.data[7] + other.data[5] * other.data[2] * other.data[11] - other.data[1] * other.data[6] * other.data[11]) * det;
        data[7] = (-other.data[8] * other.data[6] * other.data[3] + other.data[4] * other.data[10] * other.data[3] + other.data[8] * other.data[2] * other.data[7] - other.data[0] * other.data[10] * other.data[7] - other.data[4] * other.data[2] * other.data[11] + other.data[0] * other.data[6] * other.data[11]) * det;
        data[11] = (other.data[8] * other.data[5] * other.data[3] - other.data[4] * other.data[9] * other.data[3] - other.data[8] * other.data[1] * other.data[7] + other.data[0] * other.data[9] * other.data[7] + other.data[4] * other.data[1] * other.data[11] - other.data[0] * other.data[5] * other.data[11]) * det;
    }

    /**
     * Get inverse of this matrix
     * 
     * @return Inversed matrix
     */
    public Matrix4 inverse()
    {
        Matrix4 result = new Matrix4();
        result.setInverse(this);
        return result;
    }

    /**
     * Invert this matrix
     */
    public void invert()
    {
        setInverse(this);
    }

    /**
     * Transform a direction vector using this matrix
     * 
     * @param vector
     * @return 
     */
    public Vec transformDirection(Vec vector)
    {
        float x = vector.x * data[0] + vector.y * data[1] + vector.z * data[2];
        float y = vector.x * data[4] + vector.y * data[5] + vector.z * data[6];
        float z = vector.x * data[8] + vector.y * data[9] + vector.z * data[10];
        return new Vec(x, y, z);
    }

    /**
     * Transform a direction vector in the inverse direction using this matrix
     * 
     * @param vector
     * @return 
     */
    public Vec transformInverseDirection(Vec vector)
    {
        float x = vector.x * data[0] + vector.y * data[4] + vector.z * data[8];
        float y = vector.x * data[1] + vector.y * data[5] + vector.z * data[9];
        float z = vector.x * data[2] + vector.y * data[6] + vector.z * data[10];
        return new Vec(x, y, z);
    }

    /**
     * Transform the specified vector to a inverse point using this matrix
     * 
     * @param vector
     * @return 
     */
    public Vec transformInverse(Vec vector)
    {
        Vec tmp = new Vec(vector);
        tmp.x -= data[3];
        tmp.y -= data[7];
        tmp.z -= data[11];

        return new Vec(tmp.x * data[0] + tmp.y * data[4] + tmp.z * data[8], tmp.x * data[1] + tmp.y * data[5] + tmp.z * data[9], tmp.x * data[2] + tmp.y * data[6] + tmp.z * data[10]);
    }

    /**
     * Get axis vector at the specified row
     * 
     * @param index Row to extract from
     * @return Extracted vector
     */
    public Vec getAxisVector(int index)
    {
        return new Vec(data[index], data[index + 4], data[index + 8]);
    }

    /**
     * Set orientation and position of this matrix
     * 
     * @param quat Orientaion
     * @param pos Position
     */
    public void setOrientationAndPos(Quaternion quat, Vec pos)
    {
        data[0] = 1 - (2 * quat.j * quat.j + 2 * quat.k * quat.k);
        data[1] = 2 * quat.i * quat.j + 2 * quat.k * quat.r;
        data[2] = 2 * quat.i * quat.k - 2 * quat.j * quat.r;
        data[3] = pos.x;
        data[4] = 2 * quat.i * quat.j - 2 * quat.k * quat.r;
        data[5] = 1 - (2 * quat.i * quat.i  + 2 * quat.k * quat.k);
        data[6] = 2 * quat.j * quat.k + 2 * quat.i * quat.r;
        data[7] = pos.y;
        data[8] = 2 * quat.i * quat.k + 2 * quat.j * quat.r;
        data[9] = 2 * quat.j * quat.k - 2 * quat.i * quat.r;
        data[10] = 1 - (2 * quat.i * quat.i  + 2 * quat.j * quat.j);
        data[11] = pos.z;
    }

    /**
     * Fill an array with data values for OpenGL rendering of the data
     * 
     * @param array 
     */
    public void fillGLArray(float[] array)
    {
        array[0] = data[0];
        array[1] = data[4];
        array[2] = data[8];
        array[3] = 0.0f;
        array[4] = data[1];
        array[5] = data[5];
        array[6] = data[9];
        array[7] = 0.0f;
        array[8] = data[2];
        array[9] = data[6];
        array[10] = data[10];
        array[11] = 0.0f;
        array[12] = data[3];
        array[13] = data[7];
        array[14] = data[11];
        array[15] = 1.0f;
    }	
}

