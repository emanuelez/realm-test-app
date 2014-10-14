package io.realm.testapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import io.realm.Realm;
import io.realm.testapp.models.Dog;
import io.realm.testapp.models.Person;


public class MyActivity extends Activity {

    private static final String TAG = "RealmTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Realm.deleteRealmFile(this);
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        Dog dog = realm.createObject(Dog.class);
        dog.setName("White Fang");
        Person person = realm.createObject(Person.class);
        person.setName("Gray Beaver");
        person.getDogs().add(dog);
        realm.commitTransaction();

        Log.i(TAG, person.toString());
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
