package com.base.engine.physics;

import com.base.engine.math.Matrix4;
import com.base.engine.math.Vec;

/**
 * Detects collisions between objects
 * 
 * @author JordanG
 */
public class CollisionDetector
{
    public float smallestPenetration;
    public int smallestCase;
	
    public static float transformToAxis(CollisionBox box, Vec axis)
    {
        return box.halfSize.x * Math.abs(axis.dotProd(box.getAxis(0))) + box.halfSize.y * Math.abs(axis.dotProd(box.getAxis(1))) + box.halfSize.z * Math.abs(axis.dotProd(box.getAxis(2)));
    }

    /**
     * Check for penetration on the specified axis, between two boxes
     * 
     * @param one
     * @param two
     * @param axis
     * @param toCenter
     * @return 
     */
    private static float penetrationOnAxis(CollisionBox one, CollisionBox two, Vec axis, Vec toCenter)
    {
        float oneProject = transformToAxis(one, axis);
        float twoProject = transformToAxis(two, axis);

        float distance = Math.abs(toCenter.dotProd(axis));

        return oneProject + twoProject - distance;
    }

    /**
     * Check for penetration on the specified axis between two boxes
     * 
     * @param one
     * @param two
     * @param axis
     * @param toCenter
     * @param index
     * @return 
     */
    private boolean tryAxis(CollisionBox one, CollisionBox two, Vec axis, Vec toCenter, int index)
    {
        if(axis.squaredMagnitude() <= 0.0001)
        {
            return true;
        }
        axis = axis.normalize();

        float penetration = penetrationOnAxis(one, two, axis, toCenter);

        if (penetration < 0.0f)
        {
            return false;
        }
        
        if (penetration < smallestPenetration)
        {
                smallestPenetration = penetration;
                smallestCase = index;
        }

        return true;
    }

    /**
     * Check for collisions between two boxes via SAT
     * 
     * @param one
     * @param two
     * @param data
     * @return 
     */
    public int boxAndBox(CollisionBox one, CollisionBox two, CollisionData data)
    {
        Vec toCenter = new Vec(two.getAxis(3));
        toCenter = toCenter.subtract(one.getAxis(3));

        smallestPenetration = Float.MAX_VALUE;
        smallestCase = 0xffffff;

        if(!tryAxis(one, two, one.getAxis(0), toCenter, 0))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(1), toCenter, 1))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(2), toCenter, 2))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(0), toCenter, 3))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(1), toCenter, 4))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(2), toCenter, 5))
        {
            return 0;
        }

        int bestSingleAxis = smallestCase;

        if(!tryAxis(one, two, one.getAxis(0).crossProd(two.getAxis(0)), toCenter, 6))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(0).crossProd(two.getAxis(1)), toCenter, 7))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(0).crossProd(two.getAxis(3)), toCenter, 8))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(1).crossProd(two.getAxis(0)), toCenter, 9))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(1).crossProd(two.getAxis(1)), toCenter, 10))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(1).crossProd(two.getAxis(2)), toCenter, 11))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(2).crossProd(two.getAxis(0)), toCenter, 12))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(2).crossProd(two.getAxis(1)), toCenter, 13))
        {
            return 0;
        }
        if(!tryAxis(one, two, one.getAxis(2).crossProd(two.getAxis(2)), toCenter, 14))
        {
            return 0;
        }

        if(smallestCase != 0xffffff)
        {
            if(smallestCase < 3)
            {
                fillPointFaceBoxBox(one, two, toCenter, data, smallestCase, smallestPenetration);
                data.contactArrayIndex++;
                return 1;
            }
            else if(smallestCase < 6)
            {
                fillPointFaceBoxBox(two, one, toCenter.multiply(-1.0f), data, smallestCase - 3, smallestPenetration);
                data.contactArrayIndex++;
                return 1;
            }
            else
            {
                smallestCase -= 6;
                int oneAxisIndex = (int) (smallestCase / 3);
                int twoAxisIndex = smallestCase % 3;

                Vec oneAxis = new Vec(one.getAxis(oneAxisIndex));
                Vec twoAxis = new Vec(two.getAxis(twoAxisIndex));
                Vec axis = oneAxis.crossProd(twoAxis);
                axis = axis.normalize();

                if(axis.dotProd(toCenter) > 0.0f)
                {
                    axis = axis.multiply(-1.0f);
                }

                Vec ptOnOneEdge = new Vec(one.halfSize);
                Vec ptOnTwoEdge = new Vec(two.halfSize);


                if(0 == oneAxisIndex)
                {
                    ptOnOneEdge.x = 0;
                }
                else if(one.getAxis(0).dotProd(axis) > 0.0f)
                {
                    ptOnOneEdge.x = -ptOnOneEdge.x;
                }

                if(0 == twoAxisIndex)
                {
                    ptOnTwoEdge.x = 0;
                }
                else if(two.getAxis(0).dotProd(axis) < 0.0f)
                {
                    ptOnTwoEdge.x = -ptOnTwoEdge.x;
                }

                if(1 == oneAxisIndex)
                {
                    ptOnOneEdge.y = 0;
                }
                else if(one.getAxis(1).dotProd(axis) > 0.0f)
                {
                    ptOnOneEdge.y = -ptOnOneEdge.y;
                }

                if(1 == twoAxisIndex)
                {
                    ptOnTwoEdge.y = 0;
                }
                else if(two.getAxis(1).dotProd(axis) < 0.0f)
                {
                    ptOnTwoEdge.y = -ptOnTwoEdge.y;
                }

                if(2 == oneAxisIndex)
                {
                    ptOnOneEdge.z = 0;
                }
                else if(one.getAxis(2).dotProd(axis) > 0.0f)
                {
                    ptOnOneEdge.z = -ptOnOneEdge.z;
                }

                if(2 == twoAxisIndex)
                {
                    ptOnTwoEdge.z = 0;
                }
                else if(two.getAxis(2).dotProd(axis) < 0.0f)
                {
                    ptOnTwoEdge.z = -ptOnTwoEdge.z;
                }

                Matrix4 tmp = new Matrix4(one.transform);
                ptOnOneEdge = tmp.multiply(ptOnOneEdge);

                tmp = new Matrix4(two.transform);
                ptOnTwoEdge = tmp.multiply(ptOnTwoEdge);

                Vec vertex = new Vec();

                if(oneAxisIndex == 0 && twoAxisIndex == 0)
                {
                    vertex = new Vec(getContactPoint(ptOnOneEdge, oneAxis, one.halfSize.x, ptOnTwoEdge, twoAxis, two.halfSize.x, bestSingleAxis > 2));
                }
                else if(oneAxisIndex == 0 && twoAxisIndex == 1)
                {
                    vertex = new Vec(getContactPoint(ptOnOneEdge, oneAxis, one.halfSize.x, ptOnTwoEdge, twoAxis, two.halfSize.y, bestSingleAxis > 2));
                }
                else if(oneAxisIndex == 0 && twoAxisIndex == 2)
                {
                    vertex = new Vec(getContactPoint(ptOnOneEdge, oneAxis, one.halfSize.x, ptOnTwoEdge, twoAxis, two.halfSize.z, bestSingleAxis > 2));
                }
                else if(oneAxisIndex == 1 && twoAxisIndex == 0)
                {
                    vertex = new Vec(getContactPoint(ptOnOneEdge, oneAxis, one.halfSize.y, ptOnTwoEdge, twoAxis, two.halfSize.x, bestSingleAxis > 2));
                }
                else if(oneAxisIndex == 1 && twoAxisIndex == 1)
                {
                    vertex = new Vec(getContactPoint(ptOnOneEdge, oneAxis, one.halfSize.y, ptOnTwoEdge, twoAxis, two.halfSize.y, bestSingleAxis > 2));
                }
                else if(oneAxisIndex == 1 && twoAxisIndex == 2)
                {
                    vertex = new Vec(getContactPoint(ptOnOneEdge, oneAxis, one.halfSize.y, ptOnTwoEdge, twoAxis, two.halfSize.z, bestSingleAxis > 2));
                }
                else if(oneAxisIndex == 2 && twoAxisIndex == 0)
                {
                    vertex = new Vec(getContactPoint(ptOnOneEdge, oneAxis, one.halfSize.z, ptOnTwoEdge, twoAxis, two.halfSize.x, bestSingleAxis > 2));
                }
                else if(oneAxisIndex == 2 && twoAxisIndex == 1)
                {
                    vertex = new Vec(getContactPoint(ptOnOneEdge, oneAxis, one.halfSize.z, ptOnTwoEdge, twoAxis, two.halfSize.y, bestSingleAxis > 2));
                }
                else if(oneAxisIndex == 2 && twoAxisIndex == 2)
                {
                    vertex = new Vec(getContactPoint(ptOnOneEdge, oneAxis, one.halfSize.z, ptOnTwoEdge, twoAxis, two.halfSize.z, bestSingleAxis > 2));
                }

                Contact contact = new Contact();
                contact.penetration = smallestPenetration;
                contact.contactNormal = axis;
                contact.contactPoint = vertex;
                contact.setBodyData(one.body, two.body, data.friction, data.restitution);

                data.contacts.add(contact);
                data.contactArrayIndex++;
                return 1;
            }
        }
        return 0;
    }

    public void fillPointFaceBoxBox(CollisionBox one, CollisionBox two, Vec toCenter, CollisionData data, int best, float penetration)
    {
        Contact contact = new Contact();

        Vec normal = one.getAxis(best);
        if(one.getAxis(best).dotProd(toCenter) > 0.0f)
        {
            normal = normal.multiply(-1.0f);
        }

        Vec vertex = new Vec(two.halfSize);

        if(two.getAxis(0).dotProd(normal) < 0.0f)
        {
            vertex.x = -vertex.x;
        }
        if(two.getAxis(1).dotProd(normal) < 0.0f)
        {
            vertex.y = -vertex.y;
        }
        if(two.getAxis(2).dotProd(normal) < 0.0f)
        {
            vertex.z = -vertex.z;
        }

        contact.contactNormal = normal;
        contact.penetration  = penetration;

        Matrix4 tmp = new Matrix4(two.getTransform());
        contact.contactPoint = new Vec(tmp.multiply(vertex));
        contact.setBodyData(one.body, two.body, data.friction, data.restitution);
        data.contacts.add(contact);
    }

    /**
     * Get the contact point of a collision
     * 
     * @param pointA
     * @param axisA
     * @param sizeA
     * @param pointB
     * @param axisB
     * @param sizeB
     * @param useA
     * @return 
     */
    public Vec getContactPoint(Vec pointA, Vec axisA, float sizeA, Vec pointB, Vec axisB, float sizeB, boolean useA)
    {
        Vec toCenter = new Vec();
        Vec contactA = new Vec();
        Vec contactB = new Vec();

        float dotCenterA, dotCenterB, dotAB, lengthA, lengthB;
        float denominator, unitA, unitB;

        lengthA = axisA.squaredMagnitude();
        lengthB = axisB.squaredMagnitude();
        dotAB = axisB.dotProd(axisA);

        toCenter = new Vec(pointA);
        toCenter = toCenter.subtract(pointB);
        dotCenterA = axisA.dotProd(toCenter);
        dotCenterB = axisB.dotProd(toCenter);

        denominator = lengthA * lengthB - dotAB * dotAB;

        if(Math.abs(denominator) < 0.0001d)
        {
            return useA ? pointA : pointB;
        }

        unitA = (dotAB * dotCenterB - lengthB * dotCenterA) / denominator;
        unitB = (lengthA * dotCenterB - dotAB * dotCenterA) / denominator;

        if(unitA > sizeA || unitA < -sizeA || unitB > sizeB || unitB < -sizeB)
        {
            return useA ? pointA : pointB;
        }
        else
        {
            contactA = new Vec(pointA);
            contactA = contactA.addScaledVector(axisA, unitA);

            contactB = new Vec(pointB);
            contactB = contactB.addScaledVector(axisB, unitB);

            contactA = contactA.multiply(0.5f);
            contactA = contactA.addScaledVector(contactB, 0.5f);

            return contactA;
        }
    }
}
