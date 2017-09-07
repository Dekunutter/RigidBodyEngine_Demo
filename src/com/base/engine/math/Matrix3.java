package com.base.engine.math;

/**
 * Stores data in 3-column matrices and has all related operations
 * Often used as inertia tensors
 * 
 * @author JordanG
 */
public class Matrix3
{
    public float[] data;

    /**
     * Initialise the matrix with 0 values
     */
    public Matrix3()
    {
        data = new float[9];
        data[0] = data[1] = data[2] = data[3] = data[4]  = data[5] = data[6] = data[7] = data[8] = 0.0f; 
    }

    /**
     * Initialise the matrix with data from another
     * 
     * @param other Other matrix we are copying over
     */
    public Matrix3(Matrix3 other)
    {
        data = new float[9];
        data[0] = other.data[0];
        data[1] = other.data[1];
        data[2] = other.data[2];
        data[3] = other.data[3];
        data[4] = other.data[4];
        data[5] = other.data[5];
        data[6] = other.data[6];
        data[7] = other.data[7];
        data[8] = other.data[8];
    }

    /**
     * Initialise the matrix using three component vectors
     * 
     * @param compOne First vector
     * @param compTwo Second vector
     * @param compThree Third vector
     */
    public Matrix3(Vec compOne, Vec compTwo, Vec compThree)
    {
        data = new float[9];
        setComponents(compOne, compTwo, compThree);
    }

    /**
     * Initialise the matrix with individual data values
     * 
     * @param data0
     * @param data1
     * @param data2
     * @param data3
     * @param data4
     * @param data5
     * @param data6
     * @param data7
     * @param data8 
     */
    public Matrix3(float data0, float data1, float data2, float data3, float data4, float data5, float data6, float data7, float data8)
    {
        data = new float[9];
        data[0] = data0;
        data[1] = data1;
        data[2] = data2;
        data[3] = data3;
        data[4] = data4;
        data[5] = data5;
        data[6] = data6;
        data[7] = data7;
        data[8] = data8;

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
        setInertiaTensorCoeffs(a, b, c);
    }

    /**
     * Set the inertia tensor coefficients of this matrix and empty the values of any other data values
     * 
     * @param ix Tensor coefficient along X
     * @param iy Tensor coefficient along Y
     * @param iz Tensor coefficient along Z
     */
    public void setInertiaTensorCoeffs(float ix, float iy, float iz)
    {
        data[0] = ix;
        data[1] = 0.0f;
        data[2] = 0.0f;
        data[3] = 0.0f;
        data[4] = iy;
        data[5] = 0.0f;
        data[6] = 0.0f;
        data[7] = 0.0f;
        data[8] = iz;
    }

    /**
     * Set the inertia tensor coefficients of this matrix with inversed coefficients in the other data values
     * 
     * @param ix
     * @param iy
     * @param iz
     * @param ixy
     * @param ixz
     * @param iyz 
     */
    public void setInertiaTensorCoeffs(float ix, float iy, float iz, float ixy, float ixz, float iyz)
    {
        data[0] = ix;
        data[1] = -ixy;
        data[2] = -ixz;
        data[3] = -ixy;
        data[4] = iy;
        data[5] = -iyz;
        data[6] = -ixz;
        data[7] = -iyz;
        data[8] = iz;
    }

    /**
     * Set the inertia tensor of a block
     * 
     * @param halfSizes The half sizes of the 3D block we are making the inertia tensor for
     * @param mass Mass of the block object
     */
    public void setBlockInertiaTensor(Vec halfSizes, float mass)
    {
        Vec squares = new Vec(halfSizes.multiply(halfSizes));
        setInertiaTensorCoeffs(0.3f * mass * (squares.y + squares.z), 0.3f * mass * (squares.x + squares.z), 0.3f * mass * (squares.x + squares.y));
    }

    /**
     * Setup this matrix as a skew symmetric of the given vector
     * 
     * @param vector 
     */
    public void setSkewSymmetric(Vec vector)
    {
        data[0] = 0.0f;
        data[1] = -vector.z;
        data[2] = vector.y;
        data[3] = vector.z;
        data[4] = 0.0f;
        data[5] = -vector.x;
        data[6] = -vector.y;
        data[7] = vector.x;
        data[8] = 0.0f;
    }

    /**
     * Setup this matrix using three component vectors
     * 
     * @param compOne
     * @param compTwo
     * @param compThree 
     */
    public final void setComponents(Vec compOne, Vec compTwo, Vec compThree)
    {
        data[0] = compOne.x;
        data[1] = compTwo.x;
        data[2] = compThree.x;
        data[3] = compOne.y;
        data[4] = compTwo.y;
        data[5] = compThree.y;
        data[6] = compOne.z;
        data[7] = compTwo.z;
        data[8] = compThree.z;
    }

    /**
     * Multiply this matrix by a vector
     * 
     * @param vector
     * @return Multiplied vector
     */
    public Vec multiply(Vec vector)
    {
        float x = vector.x * data[0] + vector.y * data[1] + vector.z * data[2];
        float y = vector.x * data[3] + vector.y * data[4] + vector.z * data[5];
        float z = vector.x * data[6] + vector.y * data[7] + vector.z * data[8];
        return new Vec(x, y, z);
    }
    
    /**
     * Transform the vector using this matrix, same as multiplying
     * 
     * @param vector
     * @return Multiplied vector
     */
    public Vec transform(Vec vector)
    {
        return multiply(vector);
    }

    /**
     * Transform transpose the vector using this matrix, which is a different order to a basic transform
     * 
     * @param vector
     * @return Multiplied vector 
     */
    public Vec transformTranspose(Vec vector)
    {
        float x = vector.x * data[0] + vector.y * data[3] + vector.z * data[6];
        float y = vector.x * data[1] + vector.y * data[4] + vector.z * data[7];
        float z = vector.x * data[2] + vector.y * data[5] + vector.z * data[8];
        return new Vec(x, y, z);
    }

    /**
     * Get vector from the specified row of the matrix
     * 
     * @param i Row to extract from
     * @return Extracted vector
     */
    public Vec getRowVector(int i)
    {
        return new Vec(data[i * 3], data[i * 3 + 1], data[i * 3 + 2]);
    }

    /**
     * Get axis vector from the matrix
     * 
     * @param i Position to extract from
     * @return Extracted vector
     */
    public Vec getAxisVector(int i)
    {
        return new Vec(data[i], data[i + 3], data[i + 6]);
    }

    /**
     * Setup this matrix to be the inverse of the specified one
     * 
     * @param other Other matrix
     */
    public void setInverse(Matrix3 other)
    {
        float t4 = other.data[0] * other.data[4];
        float t6 = other.data[0] * other.data[5];
        float t8 = other.data[1] * other.data[3];
        float t10 = other.data[2] * other.data[3];
        float t12 = other.data[1] * other.data[6];
        float t14= other.data[2] * other.data[6];

        float t16 = (t4 * other.data[8] - t6 * other.data[7] - t8 * other.data[8] + t10 * other.data[7] + t12 * other.data[5] - t14 * other.data[4]);
        if (t16 == 0.0f)
        {
            return;
        }

        float t17 = 1 / t16;

        data[0] = (other.data[4] * other.data[8] - other.data[5] * other.data[7]) * t17;
        data[1] = -(other.data[1] * other.data[8] - other.data[2] * other.data[7]) * t17;
        data[2] = (other.data[1] * other.data[5] - other.data[2] * other.data[4]) * t17;
        data[3] = -(other.data[3] * other.data[8] - other.data[5] * other.data[6]) * t17;
        data[4] = (other.data[0] * other.data[8] - t14) * t17;
        data[5] = -(t6 - t10) * t17;
        data[6] = (other.data[3] * other.data[7] - other.data[4] * other.data[6]) * t17;
        data[7] = -(other.data[0] * other.data[7] - t12) * t17;
        data[8] = (t4 - t8) * t17;
    }

    /**
     * Get inverse of this matrix
     * 
     * @return Inversed matrix 
     */
    public Matrix3 inverse()
    {
        Matrix3 result = new Matrix3();
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
     * Setup matrix as transpose of specified matrix
     * 
     * @param other
     */
    public void setTranspose(Matrix3 other)
    {
        data[0] = other.data[0];
        data[1] = other.data[3];
        data[2] = other.data[6];
        data[3] = other.data[1];
        data[4] = other.data[4];
        data[5] = other.data[7];
        data[6] = other.data[2];
        data[7] = other.data[5];
        data[8] = other.data[8];
    }

    /**
     * Get transpose of this matrix
     * @return Transposed matrix
     */
    public Matrix3 transpose()
    {
        Matrix3 result = new Matrix3();
        result.setTranspose(this);
        return result;
    }

    /**
     * Multiply this matrix by another
     * 
     * @param other
     * @return Multiplied matrix
     */
    public Matrix3 multiply(Matrix3 other)
    {
        float data0 = data[0] * other.data[0] + data[1] * other.data[3] + data[2] * other.data[6];
        float data1 = data[0] * other.data[1] + data[1] * other.data[4] + data[2] * other.data[7];
        float data2 = data[0] * other.data[2] + data[1] * other.data[5] + data[2] * other.data[8];
        float data3 = data[3] * other.data[0] + data[4] * other.data[3] + data[5] * other.data[6];
        float data4 = data[3] * other.data[1] + data[4] * other.data[4] + data[5] * other.data[7];
        float data5 = data[3] * other.data[2] + data[4] * other.data[5] + data[5] * other.data[8];
        float data6 = data[6] * other.data[0] + data[7] * other.data[3] + data[8] * other.data[6];
        float data7 = data[6] * other.data[1] + data[7] * other.data[4] + data[8] * other.data[7];
        float data8 = data[6] * other.data[2] + data[7] * other.data[5] + data[8] * other.data[8];
        return new Matrix3(data0, data1, data2, data3, data4, data5, data6, data7, data8);
    }

    /**
     * Multiply this matrix by a float
     * 
     * @param scalar Float to multiply the matrix by
     */
    public void multiply(float scalar)
    {
        data[0] *= scalar;
        data[1] *= scalar;
        data[2] *= scalar;
        data[3] *= scalar;
        data[4] *= scalar;
        data[5] *= scalar;
        data[6] *= scalar;
        data[7] *= scalar;
        data[8] *= scalar;
    }

    /**
     * Add this matrix to another
     * 
     * @param other
     * @return 
     */
    public Matrix3 add(Matrix3 other)
    {
        float data0 = data[0] + other.data[0];
        float data1 = data[1] + other.data[1];
        float data2 = data[2] + other.data[2];
        float data3 = data[3] + other.data[3];
        float data4 = data[4] + other.data[4];
        float data5 = data[5] + other.data[5];
        float data6 = data[6] + other.data[6];
        float data7 = data[7] + other.data[7];
        float data8 = data[8] + other.data[8];

        return new Matrix3(data0, data1, data2, data3, data4, data5, data6, data7, data8);
    }

    /**
     * Set orientation of this matrix with the specified quaternion
     * 
     * @param quat 
     */
    public void setOrientation(Quaternion quat)
    {
        data[0] = 1 - (2 * quat.j * quat.j + 2 * quat.k * quat.k);
        data[1] = 2 * quat.i * quat.j + 2 * quat.k * quat.r;
        data[2] = 2 * quat.i * quat.k - 2 * quat.j * quat.r;
        data[3] = 2 * quat.i * quat.j - 2 * quat.k * quat.r;
        data[4] = 1 - (2 * quat.i * quat.i + 2 * quat.k * quat.k);
        data[5] = 2 * quat.j * quat.k + 2 * quat.i * quat.r;
        data[6] = 2 * quat.i * quat.k + 2 * quat.j * quat.r;
        data[7] = 2 * quat.j * quat.k - 2 * quat.i * quat.r;
        data[8] = 1 - (2 * quat.i*quat.i + 2 * quat.j * quat.j);
    }

    /**
     * Get determinant of this matrix
     * 
     * @return 
     */
    public double determinant()
    {
        return data[0] * data[4] * data[8] - data[0] * data[5] * data[7] - data[1] * data[3] * data[8] + data[1] * data[4] * data[6] + data[2] * data[3] * data[7] - data[2] * data[4] * data[6];
    }

    /**
     * Interpolate two matrices together linearly
     * 
     * @param a First matrix
     * @param b Second matrix
     * @param prop Scalar multiplier
     * @return Interpolated matrix
     */
    public static Matrix3 linearInterpolate(Matrix3 a, Matrix3 b, float prop)
    {
        Matrix3 result = new Matrix3();

        for (int i = 0; i < 9; i++)
        {
                result.data[i] = a.data[i] * (1 - prop) + b.data[i] * prop;
        }

        return result;
    }
}
