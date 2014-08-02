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
            benchmarkGson();
            benchmarkParcist();
            benchmarkGson();
            benchmarkParcist();
        }

        private void benchmarkGson() {
            Gson gson = new Gson();
            List<User> users = new ArrayList<User>();
            for(int x = 0; x < 10000; x++) {
                users.add(new User("Trevor", 23));
            }
            long startTime = System.currentTimeMillis();
            String json = gson.toJson(users);
            Type listType = new TypeToken<ArrayList<User>>() {}.getType();
            List<User> users2 = gson.fromJson(json, listType);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            int size = json.getBytes().length;
            Log.d("benchmark", "Gson total time: " + totalTime + "(ms)");
            Log.d("benchmark", "Gson total size: " + size + " bytes");
        }

        private void benchmarkParcist() {
            List<User> users = new ArrayList<User>();
            for(int x = 0; x < 10000; x++) {
                users.add(new User("Trevor", 23));
            }
            long startTime = System.currentTimeMillis();
            byte[] bytes = Parcist.write(users);
            try {
                List<User> users2 = new ArrayList<User>();
                Parcist.readTypedList(bytes, users2, User.CREATOR);
            } catch (ParcistInvalidatedException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;

            Log.d("benchmark", "Parcist total time: " + totalTime + "(ms)");
            Log.d("benchmark", "Parcist total size: " + bytes.length + " bytes");
        }
    }
}
