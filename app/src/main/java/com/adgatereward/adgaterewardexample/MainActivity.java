package com.adgatereward.adgaterewardexample;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.adgatemedia.sdk.classes.AdGateMedia;
import com.adgatemedia.sdk.model.Conversion;
import com.adgatemedia.sdk.network.OnConversionsReceived;

import java.util.HashMap;
import java.util.List;

/**
 * Showcase activity for {@link AdGateMedia}.
 */
public class MainActivity extends AppCompatActivity {

    private HashMap<String, String> getSubids() {
        HashMap<String, String> subids = new HashMap<>();

        String s2 = textOf(R.id.s2);
        String s3 = textOf(R.id.s3);
        String s4 = textOf(R.id.s4);
        String s5 = textOf(R.id.s5);

        if (!TextUtils.isEmpty(s2)) subids.put("s2", textOf(R.id.s2));
        if (!TextUtils.isEmpty(s3)) subids.put("s3", textOf(R.id.s3));
        if (!TextUtils.isEmpty(s4)) subids.put("s4", textOf(R.id.s4));
        if (!TextUtils.isEmpty(s5)) subids.put("s5", textOf(R.id.s5));

        return subids;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Clicked listener for 'Launch offer wall' button.
     * Don't call it manually.
     */
    public void launchOfferwall(View view) {
        AdGateMedia.getInstance().setDebugMode(true);
        AdGateMedia.getInstance().showOfferWall(MainActivity.this,
                "nqeX",
                textOf(R.id.user_id),
                getSubids());
    }

    /**
     * Clicked listener for 'Launch video ad' button.
     * Don't call it manually.
     */
    public void launchVideoAd(View view) {
        showVideo();
    }

    /**
     * Clicked listener for the 'See latest conversions' button.
     * Don't call it manually.
     */
    public void seeLatestConversions(View view) {
        AdGateMedia.getInstance().setDebugMode(true);
        final View activityIndicator = findViewById(R.id.activity_indicator);
        activityIndicator.setVisibility(View.VISIBLE);
        AdGateMedia.getConversions(this,
                "nqeX",
                textOf(R.id.user_id),
                getSubids(),
                new OnConversionsReceived() {
                    @Override
                    public void onSuccess(List<Conversion> conversions) {
                        Toast.makeText(MainActivity.this,
                                "Total conversions: " + conversions.size(),
                                Toast.LENGTH_SHORT).show();
                        activityIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        activityIndicator.setVisibility(View.GONE);
                    }
                });
    }

    private String textOf(int editTextResId) {
        EditText editText = (EditText) findViewById(editTextResId);
        if (editText == null) {
            return null;
        }

        return editText.getText().toString();
    }

    private void setTextById(String text, int editTextResId) {
        EditText editText = (EditText) findViewById(editTextResId);
        if (editText == null) {
            return;
        }

        editText.setText(text);
    }


    public void reset(View view) {
        setTextById("", R.id.tool_id);
        setTextById("Jane Doe", R.id.user_id);
        setTextById("Subid 2", R.id.s2);
        setTextById("Subid 3", R.id.s3);
        setTextById("Subid 4", R.id.s4);
        setTextById("Subid 5", R.id.s5);
    }

    private void showVideo() {
        final long videoCanBeClosedAfterMillis = 5000L;

        AdGateMedia adGateMedia = AdGateMedia.getInstance();
        adGateMedia.setDebugMode(true);
        adGateMedia.showVideo(this,
                textOf(R.id.tool_id),
                textOf(R.id.user_id),
                getSubids(),
                new AdGateMedia.OnVideoWatchedListener() {
                    @Override
                    public void onVideoWatched() {
                        Toast.makeText(MainActivity.this,
                                "Callback from test app fired",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                },
                videoCanBeClosedAfterMillis);
    }
}
