package com.trevore.parcist;

import android.os.Bundle;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by trevor on 1/15/16.
 */
@RunWith(AndroidJUnit4.class)
public class ParcistTest {

    @Test
    public void testBundle() throws Exception {
        Bundle bundle = new Bundle();
        bundle.putString("testing1", "testValue");
        bundle.putInt("testing2", 100);

        byte[] bytes = Parcist.writeBundle(bundle);
        Bundle bundle2 = Parcist.readBundle(bytes);

        assertEquals("testValue", bundle2.getString("testing1"));
        assertEquals(100, bundle2.getInt("testing2"));
    }

    @Test
    public void testParcelable() throws Exception {
        Car bmw = new Car("BMW", "328xi");

        byte[] bytes = Parcist.writeParcelable(bmw);
        Car bmw2 = Parcist.readParcelable(bytes, Car.CREATOR);

        assertEquals(bmw, bmw2);
    }

    @Test
    public void testTypedList() throws Exception {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("BMW", "328xi"));
        cars.add(new Car("BMW", "335xi"));
        cars.add(new Car("BMW", "z4"));

        byte[] bytes = Parcist.writeTypedList(cars);
        List<Car> cars2 = Parcist.readTypedList(bytes, Car.CREATOR);

        assertEquals(cars, cars2);
    }

    @Test
    public void testTypedArray() throws Exception {
        Car[] cars = new Car[3];
        cars[0] = new Car("BMW", "328xi");
        cars[1] = new Car("BMW", "335xi");
        cars[2] = new Car("BMW", "z4");

        byte[] bytes = Parcist.writeTypedArray(cars);
        Car[] cars2 = Parcist.readTypedArray(bytes, Car.CREATOR);

        assertTrue(Arrays.deepEquals(cars, cars2));
    }

    @Test
    public void testRead() throws Exception {

    }

    @Test
    public void testWrite() throws Exception {

    }
}