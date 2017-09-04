package com.base.engine.physics;

import com.base.engine.Sprite;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import com.base.engine.math.Matrix4;
import com.base.engine.math.Vec;
import org.lwjgl.opengl.GL11;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;

public class CollisionPrimitive
{	
    public Body body;
    public Matrix4 offset;
    protected Matrix4 transform;
    
    public Sprite spr;

    public void calculateInternals()
    {
        transform = new Matrix4();
        transform = body.getTransform();
    }

    public void render()
    {  
        glPushMatrix();
        {
            float[] mat = new float[16];
            body.getTransform().fillGLArray(mat);

            ByteBuffer bb = ByteBuffer.allocateDirect(16 * 4);
            bb.order(ByteOrder.nativeOrder());
            FloatBuffer fb = bb.asFloatBuffer();
            fb.put(mat);
            fb.position(0);

            GL11.glMultMatrix(fb);
            spr.render3D();
        }
        glPopMatrix();
    }

    public void update(float duration)
    {
        body.integrate(duration);
        calculateInternals();
    }

    public Vec getAxis(int index)
    {
        return transform.getAxisVector(index);
    }

    public Matrix4 getTransform()
    {
        return transform;
    }

    public void init(float positionX, float positionY, float red, float blue, float green, float sizeX, float sizeY, float sizeZ)
    {
        spr = new Sprite(red, blue, green, sizeX, sizeY, sizeZ);
    }
}
