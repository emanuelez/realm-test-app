package io.realm.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.testapp.tasks.Deleter;
import io.realm.testapp.tasks.Inserter;
import io.realm.testapp.tasks.Updater;


public class MyActivity extends Activity {

    public static final int RECORDS = 10000;
    public static final int THREADS = 1;

    private static final String TAG = MyActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // Cleanup
        Realm.deleteRealmFile(this);

        // Inserts
        Log.i(TAG, "Start Inserts");
        ExecutorService InsertTasksExecutor = Executors.newFixedThreadPool(THREADS);
        long tic = System.currentTimeMillis();
        for (int i = 0; i < THREADS; i++) {
            InsertTasksExecutor.execute(new Inserter(this));
        }
        InsertTasksExecutor.shutdown();
        try {
            InsertTasksExecutor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long toc = System.currentTimeMillis();
        Log.i(TAG, "Insert time: " + (toc - tic));

        // Updates
        Log.i(TAG, "Start Updates");
        ExecutorService UpdateTasksExecutor = Executors.newFixedThreadPool(THREADS);
        tic = System.currentTimeMillis();
        for (int i = 0; i < THREADS; i++) {
            UpdateTasksExecutor.execute(new Updater(this));
        }
        UpdateTasksExecutor.shutdown();
        try {
            UpdateTasksExecutor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        toc = System.currentTimeMillis();
        Log.i(TAG, "Update time: " + (toc - tic));

        // Deletes
        Log.i(TAG, "Start Deletes");
        ExecutorService DeleteTasksExecutor = Executors.newFixedThreadPool(THREADS);
        tic = System.currentTimeMillis();
        for (int i = 0; i < THREADS; i++) {
            DeleteTasksExecutor.execute(new Deleter(this));
        }
        DeleteTasksExecutor.shutdown();
        try {
            DeleteTasksExecutor.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        toc = System.currentTimeMillis();
        Log.i(TAG, "Delete time: " + (toc - tic));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
