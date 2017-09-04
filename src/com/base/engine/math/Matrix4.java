package com.base.engine.math;

public class Matrix4
{
    public float[] data;

    public Matrix4()
    {
        data = new float[12];

        data[1] = data[2] = data[3] = data[4] = data[6] = data[7] = data[8] = data[9] = data[11] = 0.0f;
        data[0] = data[5] = data[10] = 1.0f;
    }

    public Matrix4(Matrix4 other)
    {
        data = new float[12];

        for(int i = 0; i < 12; i++)
        {
            data[i] = other.data[i];
        }
    }
    
    public void setDiagonal(float a, float b, float c)
    {
        data[0] = a;
        data[5] = b;
        data[10] = c;
    }

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

    public Vec multiply(Vec vector)
    {
        float x = vector.x * data[0] + vector.y * data[1] + vector.z * data[2] + data[3];
        float y = vector.x * data[4] + vector.y * data[5] + vector.z * data[6] + data[7];
        float z = vector.x * data[8] + vector.y * data[9] + vector.z * data[10] + data[11];
        return new Vec(x, y, z);
    }

    public Vec transform(Vec vector)
    {
        return this.multiply(vector);
    }

    public float getDeterminant()
    {
        return data[8] * data[5] * data[2] + data[4] * data[9] * data[2] + data[8] * data[1] * data[6] - data[0] * data[9] * data[6] - data[4] * data[1] * data[10] + data[0] * data[5] * data[10];
    }

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

    public Matrix4 inverse()
    {
        Matrix4 result = new Matrix4();
        result.setInverse(this);
        return result;
    }

    public void invert()
    {
        setInverse(this);
    }

    public Vec transformDirection(Vec vector)
    {
        float x = vector.x * data[0] + vector.y * data[1] + vector.z * data[2];
        float y = vector.x * data[4] + vector.y * data[5] + vector.z * data[6];
        float z = vector.x * data[8] + vector.y * data[9] + vector.z * data[10];
        return new Vec(x, y, z);
    }

    public Vec transformInverseDirection(Vec vector)
    {
        float x = vector.x * data[0] + vector.y * data[4] + vector.z * data[8];
        float y = vector.x * data[1] + vector.y * data[5] + vector.z * data[9];
        float z = vector.x * data[2] + vector.y * data[6] + vector.z * data[10];
        return new Vec(x, y, z);
    }

    public Vec transformInverse(Vec vector)
    {
        Vec tmp = new Vec(vector);
        tmp.x -= data[3];
        tmp.y -= data[7];
        tmp.z -= data[11];

        return new Vec(tmp.x * data[0] + tmp.y * data[4] + tmp.z * data[8], tmp.x * data[1] + tmp.y * data[5] + tmp.z * data[9], tmp.x * data[2] + tmp.y * data[6] + tmp.z * data[10]);
    }

    public Vec getAxisVector(int index)
    {
        return new Vec(data[index], data[index + 4], data[index + 8]);
    }

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

