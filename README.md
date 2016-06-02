##AdGate Rewards Android SDK by AdGate Media
### Introduction
Thank you for choosing AdGate Media to monetize your Android app! We look forward to working with you and your users in a way that is beneficial to everyone involved. If you haven't done so already, please sign up for an affiliate account at http://www.adgatemedia.com.

Our SDK will allow you to easily present an offer wall in your native Android app. To display it, please follow these 3 steps:

1. [Install the SDK](#1-install-sdk)
2. [Display offer wall to user](#2-display-the-offer-wall)
3. [Check our Demo app for an example](#3-demo-app)

###Requirements

The following are the minimum requirements for using our SDK

- Android SDK minimum version 9 (Gingerbread 2.3.6)
- Minimum hardware requirement is ARMV7
- Java JDK 1.6 or greater
- The following permission added to your manifest:

``` xml
<uses-permission android:name="android.permission.INTERNET"/>

```

###1. Install SDK

> Instructions provided here are for Android Studio. aar libraries are not yet supported on
> Eclipse, though some solutions are available online.

1. Download the aar file from the downloads directory
2. Add the aar file to your project, by copying it into your project’s libs folder.

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
    compile(name:'adgatemediasdk', ext:'aar')
}

```
At this point Android Studio shouldn't throw any errors related to the library.

###2. Display the offer wall

1. In your activity, where you intend to show the offer wall, add the following import statement:

```java

import com.adgatemedia.sdk.classes.AdGateMedia;

```

2. To show the offer wall, from any place in your activity add the following code:

```java
   final HashMap<String, String> subids = new HashMap<String, String>();
   subids.put("s2", "my sub id");

   AdGateMedia adGateMedia = new AdGateMedia(wallCode,userName);
   adGateMedia.showOfferWall(subids, YourActivity.this);

```

Remember to set `wallCode` and `userName` to the appropriate values. You can get your AdGate Rewards wall code from the [Dashboard](https://panel.adgatemedia.com/affiliate/vc-walls). The `userName` values can be any alphanumeric string. You may add up to 4 subid strings to the HashMap: s2, s3, s4, and s5.

###3. Demo app

This repository contains a demo Android app that shows how to implement our SDK.
