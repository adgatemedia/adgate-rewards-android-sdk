package com.adgatereward.adgaterewardexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.adgatemedia.sdk.classes.AdGateMedia;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String vcCode = "nqeX";
        String userName = "username";

        final HashMap<String, String> subids = new HashMap<String, String>();

        subids.put("s2", "subid2");
        subids.put("s3", "another value");
        subids.put("s4", "sub4");
        subids.put("s5", "testing");

        AdGateMedia adGateMedia = new AdGateMedia(vcCode,userName);
        adGateMedia.showOfferWall(subids, MainActivity.this);

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
