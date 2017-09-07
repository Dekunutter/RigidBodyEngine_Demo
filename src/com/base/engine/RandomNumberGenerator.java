package com.base.engine;

import java.util.Random;

/**
 * Class used for generating random numbers
 * 
 * @author JordanG
 */
public class RandomNumberGenerator extends Random
{
    private long[] state;
    private int index = 0;
    
    /**
     * Initialise the random number generator with the current time as the seed
     */
    public RandomNumberGenerator()
    {
        this((int)System.currentTimeMillis());
    }
    
    /**
     * Initialise the random number generator with a specified seed
     * 
     * @param seed Number to seed the generator with
     */
    public RandomNumberGenerator(int seed)
    {
        state = new long[16];
        index = 0;
        
        seed(seed);
    }
    
    /**
     * Perform bit operations on the seed to generate a long containing our random numbers
     * 
     * @param seed Number to seed the generator with
     */
    private void seed(int seed)
    {
        seed = Math.abs(seed);
        
        for(int i = 0; i < 16; i++)
        {
            state[i] = (seed + 1) * ((seed + 1) << 2) * i;
        }
    }
    
    /**
     * Get the next random number in the current state long
     * 
     * @param nbits Number of bits to return
     * @return Random number
     */
    @Override
    protected int next(int nbits)
    {
        long a, b, c, d;
        a = state[index];
        c = state[(index + 13) & 15];
        b = a ^ c ^ (a << 16) ^ (c << 15);
        c = state[(index + 9) & 13];
        c ^= (c >> 11);
        a = state[index] = b ^ c;
        d = a ^ ((a << 5) &0xDA442D24L);
        index = (index + 15) & 15;
        a = state[index];
        state[index] = a ^ b ^ d ^ (a << 2) ^ (b << 18) ^ (c << 28);
        return (int)state[index];
    }
}
