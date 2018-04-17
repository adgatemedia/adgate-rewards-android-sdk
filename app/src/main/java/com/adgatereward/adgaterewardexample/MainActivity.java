package com.adgatereward.adgaterewardexample;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.adgatemedia.sdk.classes.AdGateMedia;
import com.adgatemedia.sdk.model.Conversion;
import com.adgatemedia.sdk.network.OnConversionsReceived;
import com.adgatemedia.sdk.network.OnOfferWallLoadFailed;
import com.adgatemedia.sdk.network.OnOfferWallLoadSuccess;
import com.adgatemedia.sdk.network.OnVideoLoadFailed;
import com.adgatemedia.sdk.network.OnVideoLoadSuccess;

import java.util.HashMap;
import java.util.List;

/**
 * Showcase activity for {@link AdGateMedia}.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Replace this with your personal wall's code
     * @see String https://panel.adgatemedia.com/affiliate/vc-walls
     */
    public static String wallId = "nqeX";

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
        showVideoLoadProgress(false);
        showOfferWallLoadProgress(false);
    }

    /**
     * Clicked listener for 'Launch offer wall' button.
     * Don't call it manually.
     */
    public void launchOfferwall(View view) {
        showOfferWallLoadProgress(true);
        AdGateMedia.getInstance().setDebugMode(true);
        AdGateMedia.getInstance().loadOfferWall(MainActivity.this,
                MainActivity.wallId,
                textOf(R.id.user_id),
                getSubids(),
                new OnOfferWallLoadSuccess() {
                    @Override
                    public void onOfferWallLoadSuccess() {
                        showOfferWallLoadProgress(false);
                        AdGateMedia.getInstance().showOfferWall(MainActivity.this,
                                new AdGateMedia.OnOfferWallClosed() {
                                    @Override
                                    public void onOfferWallClosed() {
                                        Toast.makeText(MainActivity.this,
                                                "Offer wall closed", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                },
                new OnOfferWallLoadFailed() {
                    @Override
                    public void onOfferWallLoadFailed(String reason) {
                        showOfferWallLoadProgress(false);
                        Toast.makeText(MainActivity.this, "Error: " + reason, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Clicked listener for 'Launch video ad' button.
     * Don't call it manually.
     */
    public void launchVideoAd(View view) {
        loadVideo();
    }

    /**
     * Clicked listener for the 'See latest conversions' button.
     * Don't call it manually.
     */
    public void seeLatestConversions(View view) {
        AdGateMedia adGateMedia = AdGateMedia.getInstance();
        adGateMedia.setDebugMode(true);
        final View activityIndicator = findViewById(R.id.activity_indicator);
        activityIndicator.setVisibility(View.VISIBLE);
        adGateMedia.getConversions(this,
                MainActivity.wallId,
                textOf(R.id.user_id),
                getSubids(),
                new OnConversionsReceived() {
                    @Override
                    public void onSuccess(List<Conversion> conversions) {
                        Toast.makeText(MainActivity.this,
                                "Total conversions: " + conversions.size(),
                                Toast.LENGTH_SHORT).show();

                        for (Conversion conversion : conversions)  {
                            // Here you can loop through every conversion and process it.
                            Log.i("AdGateRewards", "Received new conversion: " +
                            "offer ID: " + String.valueOf(conversion.offerId) +
                            " offer title: " + conversion.title +
                            " transaction ID: " + conversion.txId +
                            " points earned: " + String.valueOf(conversion.points) +
                            " payout in cents:" + String.valueOf(conversion.payout) +
                            " subid 2: "+conversion.subid2 +
                            " subid 3: "+conversion.subid3 +
                            " subid 4: "+conversion.subid4 +
                            " subid 5: "+conversion.subid5
                            );
                        }

                        activityIndicator.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        activityIndicator.setVisibility(View.GONE);
                    }
                });
    }

    @NonNull
    private String textOf(int editTextResId) {
        EditText editText = (EditText) findViewById(editTextResId);
        if (editText == null) {
            throw new RuntimeException("No edit text with given ID");
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

    private void loadVideo() {
        final long videoCanBeClosedAfterMillis = 5000L;

        final AdGateMedia adGateMedia = AdGateMedia.getInstance();
        adGateMedia.setDebugMode(true);

        showVideoLoadProgress(true);
        adGateMedia.loadVideo(this,
                getSubids(),
                textOf(R.id.tool_id),
                textOf(R.id.user_id),
                new OnVideoLoadFailed() {
                    @Override
                    public void onVideoLoadFailed(String reason) {
                        Toast.makeText(MainActivity.this,
                                "Error: " + reason, Toast.LENGTH_SHORT).show();
                        showVideoLoadProgress(false);
                    }
                },
                new OnVideoLoadSuccess() {
                    @Override
                    public void onVideoLoadSuccess() {
                        adGateMedia.showVideo(MainActivity.this, new AdGateMedia.OnVideoClosed() {
                            @Override
                            public void onVideoWatchedAndClosed() {
                                Toast.makeText(MainActivity.this,
                                        "Video closed", Toast.LENGTH_SHORT).show();
                            }
                        }, new AdGateMedia.OnUserLeftApplication() {
                            @Override
                            public void onUserLeftApplication() {
                                Toast.makeText(MainActivity.this, "User left the app", Toast.LENGTH_SHORT).show();
                            }
                        }, videoCanBeClosedAfterMillis);
                        showVideoLoadProgress(false);
                    }
                }
        );
    }

    private void showVideoLoadProgress(boolean showProgress) {
        View progressView = findViewById(R.id.load_video_activity_indicator);
        View loadVideoButton = findViewById(R.id.load_video_button);

        showLoadProgrees(loadVideoButton, progressView, showProgress);
    }

    private void showOfferWallLoadProgress(boolean showProgress) {
        View progressView = findViewById(R.id.load_offer_wall_activity_indicator);
        View loadOfferwallButton = findViewById(R.id.load_offer_wall_button);

        showLoadProgrees(loadOfferwallButton, progressView, showProgress);
    }

    private void showLoadProgrees(View mainView, View progressView, boolean showProgress) {
        progressView.setVisibility(showProgress ? View.VISIBLE : View.INVISIBLE);
        mainView.setVisibility(showProgress ? View.INVISIBLE : View.VISIBLE);
    }
}
