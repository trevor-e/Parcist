package com.trevore.parcist.sample;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trevore.parcist.Parcist;
import com.trevore.parcist.ParcistInvalidatedException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private static final int TEST_AMOUNT = 10;

        private Gson gson = new Gson();

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d("benchmark", "Starting benchmark.");

            //Perform tests
            long gsonTotalTime = 0;
            long parcistTotalTime = 0;
            int gsonSize = 0;
            int parcistSize = 0;

            for(int x = 0; x < TEST_AMOUNT; x++) {
                TestResult gsonResult = benchmarkGson();
                TestResult parcistResult = benchmarkParcist();
                gsonTotalTime += gsonResult.getTotalTime();
                gsonSize = gsonResult.getSizeInBytes();
                parcistTotalTime += parcistResult.getTotalTime();
                parcistSize = parcistResult.getSizeInBytes();
            }

            //Calculate averages
            Log.d("benchmark", "Average Gson time (ms): " + (gsonTotalTime / (double) TEST_AMOUNT));
            Log.d("benchmark", "Average Parcist time (ms): " + (parcistTotalTime / (double) TEST_AMOUNT));
            Log.d("benchmark", "Gson space (b): " + (gsonSize / (double) TEST_AMOUNT));
            Log.d("benchmark", "Parcist space (b): " + (parcistSize / (double) TEST_AMOUNT));
            Log.d("benchmark", "Parcist is " + String.format("%.2f",((double) gsonTotalTime / (double) parcistTotalTime)) + " times faster than Gson.");
            Log.d("benchmark", "Parcist takes " + String.format("%.2f",((double) parcistSize / (double) gsonSize)) + " times more space than Gson.");

            Log.d("benchmark", "Benchmark complete.");
        }

        private TestResult benchmarkGson() {
            //Prepare data
            List<User> users = new ArrayList<User>();
            for(int x = 0; x < 10000; x++) {
                users.add(new User("Trevor", 23));
            }

            //Begin benchmark
            long startTime = System.currentTimeMillis();
            String json = gson.toJson(users);
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> users2 = gson.fromJson(json, listType);
            Log.d("trevor", "Gson result size: " + users2.size());
            long endTime = System.currentTimeMillis();

            //Calculate results
            long totalTime = endTime - startTime;
            int size = json.getBytes().length;
            return new TestResult(totalTime, size);
        }

        private TestResult benchmarkParcist() {
            //Prepare data
            List<User> users = new ArrayList<User>();
            for(int x = 0; x < 10000; x++) {
                users.add(new User("Trevor", 23));
            }

            //Begin benchmark
            long startTime = System.currentTimeMillis();
            byte[] bytes = Parcist.writeTypedList(users);
            try {
                List<User> users2 = new ArrayList<User>();
                Parcist.readTypedList(bytes, users2, User.CREATOR);
                Log.d("trevor", "Parcist result size: " + users2.size());
            } catch (ParcistInvalidatedException e) {
                e.printStackTrace();
            }

            //Calculate results
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            return new TestResult(totalTime, bytes.length);
        }
    }

    private static class TestResult {
        private long totalTime;
        private int sizeInBytes;

        public TestResult(long totalTime, int sizeInBytes) {
            this.totalTime = totalTime;
            this.sizeInBytes = sizeInBytes;
        }

        public long getTotalTime() {
            return totalTime;
        }

        public int getSizeInBytes() {
            return sizeInBytes;
        }
    }
}
