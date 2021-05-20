package com.series;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AppTest 
{
    private final Serie serie = new Serie();
    private ArrayList<Double> results = new ArrayList<Double>();
    
    private final float MIN = -Float.MAX_VALUE;
    private final float MAX = Float.MAX_VALUE;
    private final float STEP = 1E38F;
    
    private float antecessorMin = MIN - STEP;
    private float sucessorMin = MIN + STEP;
    private float antecessorMax = MAX - STEP;
    private float sucessorMax = MAX + STEP;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void t1() {
        exceptionRule.expect(OutOfMemoryError.class);
        serie.compute_serie(antecessorMin, MIN, STEP);
    }

    @Test
    public void t2 () {
        results = serie.compute_serie(MIN, sucessorMin, STEP);
        assertEquals(2, results.size());
    }

    @Test
    public void t3 () {
        results = serie.compute_serie(sucessorMin, MIN, STEP);
        assertEquals(2, results.size());
    }

    @Test
    public void t4 () {
        results = serie.compute_serie(antecessorMax, MAX, STEP);
        assertEquals(2, results.size());
    }

    @Test
    public void t5 () {
        results = serie.compute_serie(MAX, antecessorMax, STEP);
        assertEquals(2, results.size());
    }

    @Test
    public void t6() {
        exceptionRule.expect(OutOfMemoryError.class);
        serie.compute_serie(sucessorMax, MAX, STEP);
    }

    @Test
    public void t7() {
        exceptionRule.expect(OutOfMemoryError.class);
        serie.compute_serie(MIN, antecessorMin, STEP);
    }

    @Test
    public void t8() {
        exceptionRule.expect(OutOfMemoryError.class);
        serie.compute_serie(MAX, sucessorMax, STEP);
    }

    @Test
    public void t9 () {
        exceptionRule.expect(OutOfMemoryError.class);
        serie.compute_serie(0F,1F, antecessorMin);
    }

    @Test
    public void t10 () {
        results = serie.compute_serie(0F, 1F, MIN);
        assertTrue(results.isEmpty());
    }
    
    @Test
    public void t11 () {
        results = serie.compute_serie(0F, 1F, sucessorMin);
        assertTrue(results.isEmpty());
    }

    @Test
    public void t12 () {
        results = serie.compute_serie(0F, 1F, -STEP);
        assertTrue(results.isEmpty());
    }


    @Test
    public void t13 () {
        results = serie.compute_serie(0F, 1F, 0F);
        assertTrue(results.isEmpty());
    }

    @Test
    public void t14 () {
        results = serie.compute_serie(0F, STEP, STEP);
        assertEquals(2, results.size());
    }

    @Test
    public void t15 () {
        results = serie.compute_serie(0F, 1F, antecessorMax);
        assertEquals(1, results.size());
    }


    @Test
    public void t16 () {
        results = serie.compute_serie(0F, 1F, MAX);
     
    }
    
    @Test
    public void t17 () {
        exceptionRule.expect(OutOfMemoryError.class);
        serie.compute_serie(0F, 1F, sucessorMax);
    }

    @Test
    public void t18 () {
        results = serie.compute_serie(1F, -1F, -1F);
        assertEquals(3, results.size());
    }

    @Test
    public void t19 () {
        results = serie.compute_serie(0F, 0F, 1F);
        assertEquals(1, results.size());
        
    }

    @Test
    public void t20 () {
        exceptionRule.expect(OutOfMemoryError.class);
        serie.compute_serie(MIN, MAX, 5);
    }
}
