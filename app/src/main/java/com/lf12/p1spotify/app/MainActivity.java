package com.lf12.p1spotify.app;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


    private static final String TAG_TASK_ARTISTFRAGMENT = "artist_fragment";

    private MainActivityFragment artistFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        artistFragment = (MainActivityFragment) fm.findFragmentByTag(TAG_TASK_ARTISTFRAGMENT);

        if (artistFragment == null){
            artistFragment = new MainActivityFragment();
            fm.beginTransaction().add(artistFragment, TAG_TASK_ARTISTFRAGMENT).commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //TODO: Criar um Menu de Settings?
        if (id == R.id.fetch_task_test) {
            Toast toaster = Toast.makeText(getBaseContext(), "You just hit the settings button!", Toast.LENGTH_LONG);
            toaster.show();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
