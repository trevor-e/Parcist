package com.trevore.parcist.sample;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trevor on 8/14/2014.
 */
public class GsonBenchmark implements Benchmark {
    private Gson gson = new Gson();

    @Override
    public int run(List<User> testObject) {
        String json = gson.toJson(testObject);
        Type listType = new TypeToken<ArrayList<User>>() {}.getType();
        List<User> users2 = gson.fromJson(json, listType);
        return json.getBytes().length;
    }
}
