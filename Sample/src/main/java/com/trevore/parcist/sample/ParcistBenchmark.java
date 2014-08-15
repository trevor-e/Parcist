package com.trevore.parcist.sample;

import android.util.Log;

import com.trevore.parcist.Parcist;
import com.trevore.parcist.ParcistInvalidatedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trevor on 8/14/2014.
 */
public class ParcistBenchmark implements Benchmark {
    @Override
    public int run(List<User> testObjects) {
        byte[] bytes = Parcist.writeTypedList(testObjects);
        try {
            List<User> users2 = new ArrayList<User>();
            Parcist.readTypedList(bytes, users2, User.CREATOR);
        } catch (ParcistInvalidatedException e) {
            e.printStackTrace();
        }
        return bytes.length;
    }
}
