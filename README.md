## AdGate Rewards Android SDK by AdGate Media
### Introduction
Thank you for choosing AdGate Media to monetize your Android app! We look forward to working with you and your users in a way that is beneficial to everyone involved. If you haven't done so already, please sign up for an affiliate account at http://www.adgatemedia.com.

Our SDK will allow you to easily present an offer wall in your native Android app, as well as display native video ads.

1. [Install the SDK](#1-install-sdk)
2. [Initialize the SDK](#2-initialize-sdk)
3. [Load an offer wall](#3-load-the-offer-wall)
4. [Display the offer wall](#4-display-the-offer-wall)
5. [Get a list of the latest offer wall conversions](#5-get-a-list-of-the-latest-offer-wall-conversions)
6. [Enable console messages](#6-display-console-messages)
   OR
7. [Upgrade from existing aar file to latest version](#7-upgrade-from-aar-file)
8. [Check our Demo app for an example](#8-demo-app)
9. [Troubleshooting](#9-trouble-shooting)


### Requirements

The following are the minimum requirements for using our SDK

- Android SDK minimum version 19 (Kitkat 4.4)
- Minimum hardware requirement is ARMV7
- Java JDK 1.8 or greater

### 1. Install SDK

##### Gradle Installation
1. Add following code in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```
2. Add the following code to your application’s build.gradle

```
dependencies {
    implementation 'com.gitlab.adgate-media:androidsdk:2.1.1'
}
```

##### Maven installation
1. Integrate the Android SDK into your Android Studio Project with Maven by adding the following code to your application’s build.maven
```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

Add the dependency:
  ```
  <dependency>
  	    <groupId>com.gitlab.adgate-media</groupId>
	    <artifactId>androidsdk</artifactId>
	    <version>2.1.1</version>
  	</dependency>
```

Sync your Android project and at this point Android Studio shouldn't throw any errors related to the library.
### 2. Initialize the SDK
Initialise the SDK in your Application class, add the following line in `onCreate()` after `super.onCreate()`
```
AdGateMedia.initializeSdk(this);
```

### 3. Load the offer wall

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

Remember to set `wallCode` and `userId` to the appropriate values. You can get your AdGate Rewards wall `Code` from the [Dashboard](https://panel.adgatemedia.com/affiliate/vc-walls). The `userId` values can be any alphanumeric string. You may add up to 4 subid strings to the HashMap: s2, s3, s4, and s5.

### 4. Display the offer wall
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


### 5. Get a list of the latest offer wall conversions

To get a list of latest offer wall conversions for a particular user run the following code in your activity:

```java
   final HashMap<String, String> subids = new HashMap<String, String>();
        subids.put("s2", "my sub id");

        AdGateMedia.getInstance().getConversions(wallCode, userId, subids, new OnConversionsReceived() {
@Override
public void onSuccess(List<Conversion> conversions) {
        // Here you can loop through every conversion and process it.
        // conversions.size() holds the amount of new conversions to process.
        for (Conversion conversion : conversions)  {
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

### 6. Enable console messages
To enable debugging, warning, and error messages run the following line of code:

```java
   AdGateMedia.getInstance().setDebugMode(true);
```

This will log messages to the console as well as to a log file. Make sure you disable debug mode before publishing your app to the Google Play Store.

### 7. Upgrade from existing aar file to latest version
1. Remove aar file from your libs directory.
2. Remove following lines of code and dependencies from your applications's build.gradle
```
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    implementation 'com.google.code.gson:gson:2.8.0'
    implementation 'com.squareup.okio:okio:1.12.0'
    implementation 'com.squareup.okhttp3:okhttp:3.7.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'com.facebook.network.connectionclass:connectionclass:1.0.1'
    implementation(name:'adgatemediasdk', ext:'aar')
}
```
3. Follow [Install the SDK](#1-install-sdk) and  [Initialize the SDK](#2-initialize-sdk)
4. If you are showing a list of the latest offer wall conversions, follow step [Get a list of the latest offer wall conversions](#5-get-a-list-of-the-latest-offer-wall-conversions) to change your code accordingly.

### 8. Demo app

This repository contains a demo Android app that shows how to implement our SDK.

### 9. Troubleshooting

If you face
```
## AdGate Rewards Android SDK by AdGate Media
### Introduction
Thank you for choosing AdGate Media to monetize your Android app! We look forward to working with you and your users in a way that is beneficial to everyone involved. If you haven't done so already, please sign up for an affiliate account at http://www.adgatemedia.com.

Our SDK will allow you to easily present an offer wall in your native Android app, as well as display native video ads.

1. [Install the SDK](#1-install-sdk)
2. [Initialize the SDK](#2-initialize-sdk)
3. [Load an offer wall](#3-load-the-offer-wall)
4. [Display the offer wall](#4-display-the-offer-wall)
5. [Get a list of the latest offer wall conversions](#5-get-a-list-of-the-latest-offer-wall-conversions)
6. [Enable console messages](#6-display-console-messages)
   OR
7. [Upgrade from existing aar file to latest version](#7-upgrade-from-aar-file)
8. [Check our Demo app for an example](#8-demo-app)
9. [Troubleshooting](#9-trouble-shooting)


### Requirements

The following are the minimum requirements for using our SDK

- Android SDK minimum version 21 (Lollipop 5.0)
- Minimum hardware requirement is ARMV7
- Java JDK 1.8 or greater

### 1. Install SDK

##### Gradle Installation
1. Add following code in your root build.gradle at the end of repositories:
```
allprojects {
repositories {
maven { url 'https://jitpack.io' }
}
}
```
2. Add the following code to your application’s build.gradle

```
dependencies {
implementation 'com.gitlab.adgate-media:androidsdk:2.1.1'
}
```

##### Maven installation
1. Integrate the Android SDK into your Android Studio Project with Maven by adding the following code to your application’s build.maven
```
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

Add the dependency:
  ```
  <dependency>
  	    <groupId>com.gitlab.adgate-media</groupId>
	    <artifactId>androidsdk</artifactId>
	    <version>2.1.1</version>
  	</dependency>
```

Sync your Android project and at this point Android Studio shouldn't throw any errors related to the library.
### 2. Initialize the SDK
Initialise the SDK in your Application class, add the following line in `onCreate()` after `super.onCreate()`
```
AdGateMedia.initializeSdk(this);
```

### 3. Load the offer wall

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

Remember to set `wallCode` and `userId` to the appropriate values. You can get your AdGate Rewards wall `Code` from the [Dashboard](https://panel.adgatemedia.com/affiliate/vc-walls). The `userId` values can be any alphanumeric string. You may add up to 4 subid strings to the HashMap: s2, s3, s4, and s5.

### 4. Display the offer wall
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


### 5. Get a list of the latest offer wall conversions

To get a list of latest offer wall conversions for a particular user run the following code in your activity:

```java
   final HashMap<String, String> subids = new HashMap<String, String>();
   subids.put("s2", "my sub id");
   
   AdGateMedia.getInstance().getConversions(wallCode, userId, subids, new OnConversionsReceived() {
      @Override
      public void onSuccess(List<Conversion> conversions) {
          // Here you can loop through every conversion and process it.
          // conversions.size() holds the amount of new conversions to process.
          for (Conversion conversion : conversions)  {
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

### 6. Enable console messages
To enable debugging, warning, and error messages run the following line of code:

```java
   AdGateMedia.getInstance().setDebugMode(true);
```

This will log messages to the console as well as to a log file. Make sure you disable debug mode before publishing your app to the Google Play Store.

### 7. Upgrade from existing aar file to latest version
1. Remove aar file from your libs directory.
2. Remove following lines of code and dependencies from your applications's build.gradle
```
repositories {
    flatDir {
        dirs 'libs'
    }
}

dependencies {
    implementation 'com.google.code.gson:gson:2.8.0'
    implementation 'com.squareup.okio:okio:1.12.0'
    implementation 'com.squareup.okhttp3:okhttp:3.7.0'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'com.facebook.network.connectionclass:connectionclass:1.0.1'
    implementation(name:'adgatemediasdk', ext:'aar')
}
```
3. Follow [Install the SDK](#1-install-sdk) and  [Initialize the SDK](#2-initialize-sdk)
4. If you are showing a list of the latest offer wall conversions, follow step [Get a list of the latest offer wall conversions](#5-get-a-list-of-the-latest-offer-wall-conversions) to change your code accordingly.

### 8. Demo app

This repository contains a demo Android app that shows how to implement our SDK.

### 9. Troubleshooting

If you face following error,

```
Default interface methods are only supported starting with Android N (--min-api 24): androidx.lifecycle.Lifecycle androidx.lifecycle.LifecycleRegistryOwner.getLifecycle()
```
add compile options in app.gradle file as below
```
compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
```

