## AdGate Rewards Android SDK by AdGate Media
### Introduction
Thank you for choosing AdGate Media to monetize your Android app! We look forward to working with you and your users in a way that is beneficial to everyone involved. If you haven't done so already, please sign up for an affiliate account at http://www.adgatemedia.com.

Our SDK will allow you to easily present an offer wall in your native Android app, as well as display native video ads.

1. [Install the SDK](#1-install-sdk)
2. [Load an offer wall](#2-load-the-offer-wall)
3. [Display the offer wall](#3-display-the-offer-wall)
4. [Get a list of the latest offer wall conversions](#3-get-a-list-of-the-latest-offer-wall-conversions)
5. [Load video ads](#4-load-video-ads)
6. [Display fullscreen video ads](#5-display-video-ads)
7. [Enable console messages](#6-display-console-messages)
8. [Check our Demo app for an example](#7-demo-app)

### Requirements

The following are the minimum requirements for using our SDK

- Android SDK minimum version 14 (Ice Cream Sandwich 4.0)
- Minimum hardware requirement is ARMV7
- Java JDK 1.6 or greater

### 1. Install SDK

> Instructions provided here are for Android Studio. aar libraries are not yet supported on
> Eclipse, though some solutions are available online.

1. Download the latest aar file from https://github.com/adgatemedia/adgate-rewards-android-sdk/releases
2. Add the aar file to your project, by copying it into your project's libs folder.

 ![image1](https://cloud.githubusercontent.com/assets/12953988/11656906/54b19416-9dde-11e5-8c65-49dcd16c9fd2.png)

3. Add the following lines of code to your app's `build.gradle` file, so that Android Studio
recognizes and builds the library along with your app.

``` xml
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    compile 'com.google.android.gms:play-services-iid:10.2.1'
    compile 'com.google.android.gms:play-services-ads:10.2.1'
    compile 'com.google.code.gson:gson:2.8.0'
    compile 'com.squareup.okio:okio:1.12.0'
    compile 'com.squareup.okhttp3:okhttp:3.7.0'
    compile 'com.android.support:support-v4:25.3.1'
    compile 'com.facebook.network.connectionclass:connectionclass:1.0.1'
    compile(name:'adgatemediasdk', ext:'aar')
}
```
At this point Android Studio shouldn't throw any errors related to the library.

### 2. Load the offer wall

1. In your activity, where you intend to show the offer wall, add the following import statement:

```java
import com.adgatemedia.sdk.classes.AdGateMedia;
```

2. To load the offer wall, from any place in your running activity add the following code:

```java
   final HashMap<String, String> subids = new HashMap<String, String>();
   subids.put("s2", "my sub id");
   AdGateMedia adGateMedia = AdGateMedia.getInstance();
   
   adGateMedia.loadOfferWall(YourActivity.this,
                   wallCode,
                   userId,
                   subids,
                   new OnOfferWallLoadSuccess() {
                       @Override
                       public void onOfferWallLoadSuccess() {
                           // Here you can call adGateMedia.showOfferWall();
                       }
                   },
                   new OnOfferWallLoadFailed() {
                       @Override
                       public void onOfferWallLoadFailed(String reason) {
                           // Here you handle the errors with provided reason
                       }
                   });
```

Remember to set `wallCode` and `userId` to the appropriate values. You can get your AdGate Rewards wall code from the [Dashboard](https://panel.adgatemedia.com/affiliate/vc-walls). The `userId` values can be any alphanumeric string. You may add up to 4 subid strings to the HashMap: s2, s3, s4, and s5.

### 3. Display the offer wall
Once offer wall is loaded you can display it by calling the showOfferWall method.
```java
    AdGateMedia.getInstance().showOfferWall(YourActivity.this,
                                new AdGateMedia.OnOfferWallClosed() {
                                    @Override
                                    public void onOfferWallClosed() {
                                        // Here you handle the 'Offer wall has just been closed' event
                                    }
                                });

```


### 4. Get a list of the latest offer wall conversions

To get a list of latest offer wall conversions for a particular user run the following code in your activity:

```java
   final HashMap<String, String> subids = new HashMap<String, String>();
   subids.put("s2", "my sub id");
   
   AdGateMedia.getInstance().getConversions(this, wallCode, userId, subids, new OnConversionsReceived() {
      @Override
      public void onSuccess(List<Conversion> conversions) {
          // List of conversions
      }

      @Override
      public void onError(String message) {
          // Fired when any error occurs
      }
   });
```

The `wallCode` for your AdGate Rewards wall can be found on the [AdGate Rewards panel page](https://panel.adgatemedia.com/affiliate/vc-walls). The `userId` value is your app's internal user id for whom you'd like to check for conversions. `subids` is a hashmap of the subid's that was used when loading the offer wall.

If the call was successful, a list of conversions is passed to the
`onSuccess` method. Each Conversion model has the following class definition:

```java
public class Conversion implements Serializable {
    public int offerId;
    public String title;
    public String txId;
    public double points;
    public int payout; // in cents USD
    public String subid2;
    public String subid3;
    public String subid4;
    public String subid5;
}
```

### 5. Load video ads.

The first step of showing a video is to load it in the background. To load a video ad use the following:

```java

   final HashMap<String, String> subids = new HashMap<String, String>();
   subids.put("s2", "my sub id");

   AdGateMedia adGateMedia = AdGateMedia.getInstance();
   adGateMedia.loadVideo(YourActivity.this,
                         subids, // optional, can be null
                         toolId,
                         userId, // optional, can be null
                         new OnVideoLoadFailed() { // optional, can be null
                             @Override
                             public void onVideoLoadFailed(String reason) {
                                 // Here you can handle loading errors
                             }
                         },
                         new OnVideoLoadSuccess() { // optional, can be null
                             @Override
                             public void OnVideoLoadSuccess() {
                                 // Here you can call the showVideo method
                             }
                         });
```

You can get `toolId` from the [Videos](https://panel.adgatemedia.com/affiliate/video) page.

Here `userId`, `subids`, `onVideoLoadFailed` and `OnVideoLoadSuccess` can be null. You may pass up to 4 sub IDs, s2-s5. These values will be returned to you via postbacks when a user converts.

### 6. Display fullscreen video ads

Once video is loaded you can display it by calling the `showVideo` method.

```java

   final long videoCanBeClosedAfterMs = 5000L;
   AdGateMedia adGateMedia = AdGateMedia.getInstance();
   adGateMedia.showVideo(YourActivity.this,
                         new OnVideoClosed() { // optional, can be null
                             @Override
                             public void onVideoClosed() {
                                 // Here user finishes watching a video
                             }
                         },
                         new OnUserLeftApplication() { // optional, can be null
                             @Override
                             public void onUserLeftApplication() {
                                 // Here user leaves your app and goes to a
                                 // Google Play page
                             }
                         }, videoCanBeClosedAfterMs);
```

The `videoCanBeClosedAfterMs` parameter is used to determine when to display the X close button in milliseconds. The video cannot be closed before this button displays. Use `0` to display it immediately. If you don't want to let
your users close the video you can use an overloaded method without this parameter.

```java

   AdGateMedia adGateMedia = AdGateMedia.getInstance();
   adGateMedia.showVideo(YourActivity.this,
                         new OnVideoClosed() { // optional, can be null
                             @Override
                                 public void onVideoClosed() {
                                     // Here user finishes watching a video
                                 }     
                         },
                         new OnUserLeftApplication() { // optional, can be null
                             @Override
                             public void onUserLeftApplication() {
                                 // Here user leaves your app and goes to a
                                 // Google Play page
                             }
                         });
```

If you call showVideo from outside the `onVideoLoadSuccess` method you can check if video was successfully downloaded before
by calling the `hasDownloadedVideo` method.

```java

   AdGateMedia adGateMedia = AdGateMedia.getInstance();
   boolean canShowVideo = adGateMedia.hasDownloadedVideo(YourActivity.this);
   if (canShowVideo) {
       // Here you can call showVideo
   }

```

### 7. Enable console messages
To enable debugging, warning, and error messages run the following line of code:

```java
   AdGateMedia.getInstance().setDebugMode(true);
```

This will log messages to the console as well as to a log file. Make sure you disable debug mode before publishing your app to the Google Play Store.

### 8. Demo app

This repository contains a demo Android app that shows how to implement our SDK.
