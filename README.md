# @getcello/cello-react-native

> React Native wrapper to bridge our iOS and Android SDK

### 🏠 [Website](https://www.cello.so/)

### 📚 [Developer Docs](https://docs.cello.so/docs/cello-for-react-native)

---

- 📂 [Homepage](https://github.com/getcello/cello-react-native#readme)
  - [Installation](#installation)
    - [Android](#android)
      - [Android: Automatic linking with React Native v0.59 and below](#android-automatic-linking-with-react-native-v059-and-below)
      - [Android: Manual linking with React Native v0.59 and below](#android-manual-linking-with-react-native-v059-and-below)
      - [Android: Setup](#android-setup)
      - [Android: Permissions](#android-permissions)
    - [IOS](#ios)
      - [iOS: Manual linking with React Native v0.59 and below](#ios-manual-linking-with-react-native-v059-and-below)
      - [iOS: Setup](#ios-setup)
    - [Expo](#expo)
  - [Common Methods](#methods)
  - [Usage](#usage)
  - [Troubleshooting](#troubleshooting)
  - [Author](#author)
  - [Show your support](#show-your-support)
  - [License](#-license)

---

## Installation

```sh
npm install @getcello/cello-react-native
```

or

```sh
yarn add @getcello/cello-react-native
```

---

### Android

If you're using React Native v0.60 or above, the library will be linked automatically without any steps being taken.

#### Android: Automatic linking with React Native v0.59 and below

```sh
react-native link @getcello/cello-react-native
```

#### Android: Manual linking with React Native v0.59 and below

- Add below code to `android/settings.gradle`

```Gradle
include ':cello-react-native'
project(':cello-react-native').projectDir = new File(rootProject.projectDir, '../node_modules/@getcello/cello-react-native/android')
```

- Then edit `android/app/build.gradle`, inside `dependencies` at very bottom add

```Gradle
implementation project(':cello-react-native')
```

#### Android: Setup

- Add below lines to `android/app/src/main/java/com/YOUR_APP/app/MainApplication.java` inside `onCreate` method, replacing `productId` and `token` which can be found in your [settings page](https://portal.cello.so/).

```java
import com.celloreactnative.CelloReactNativeModule; //  <-- Add this line

// ...

@Override
public void onCreate() {
  super.onCreate();
  SoLoader.init(this, /* native exopackage */ false);

  // ...

  CelloReactNativeModule.initialize("productId", "token"); // <-- Add this line

  // ...
}
```

- Open `android/build.gradle` and change `minSdkVersion` to **21**, `compileSdkVersion` to at least **34** and `targetSdkVersion` to at least **34**

```Gradle
buildscript {
    // ...
    ext {
        buildToolsVersion = "29.0.2"
        minSdkVersion = 21 // <-- Here
        compileSdkVersion = 34 // <-- Here
        targetSdkVersion = 34 // <-- Here
    }
    // ...
}
```

- In `android/build.gradle` make sure that `com.android.tools.build:gradle` version is greater than `8.1.1`

```Gradle
dependencies {
    classpath("com.android.tools.build:gradle:8.1.1")
    // ...
}
```

#### Android: Permissions

You will need to include the [INTERNET](http://developer.android.com/reference/android/Manifest.permission.html#INTERNET) permission in `android/app/src/main/AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

### IOS

Cello for iOS requires a **minimum iOS version of 15.**

```sh
cd ios
pod install
cd ..
```

If you're using React Native v0.60 or above, the library will be linked automatically without any steps being taken.

#### iOS: Manual linking with React Native v0.59 and below

See [How to manually link IOS Cello SDK](https://docs.cello.so/docs/cello-for-ios#option-3-install-cello-manually)

#### iOS: Setup

- Open `ios/AppDelegate.m` then add below code:

- At the top of file add the following:

```Objective-C
#import "AppDelegate.h"
#import <React/RCTBridge.h>
#import <React/RCTBundleURLProvider.h>
#import <React/RCTRootView.h>
// ...
#import <CelloReactNative.h> // <-- Add This
```

- Inside `didFinishLaunchingWithOptions` before `return YES` add, remember to replace `productId` and `token` which can be found in your [Cello Portal](https://portal.cello.so/):

```Objective-C
  // ...
  self.window.rootViewController = rootViewController;

  [CelloReactNative initialize:for@"productId" with:@"token"]; // <-- Add this (Remember to replace strings with your product id and token)

  return YES;
  }
```

---

### Expo

If you are using Expo, you can use the built-in plugin.

After installing this package, add the [config plugin](https://docs.expo.io/guides/config-plugins/) to the [`plugins`](https://docs.expo.io/versions/latest/config/app/#plugins) array of your `app.json` or `app.config.js`:

```json
{
  "expo": {
    "plugins": ["@getcello/cello-react-native"]
  }
}
```

The plugin provides props to set environment. Every time you change the props or plugins, you'll need to rebuild (and `prebuild`) the native app. If no extra properties are added, defaults will be used.

- `env` (_string_): Set to your desired environment, such as `prod`, `sandbox`. Optional. Defaults to `prod`.

```json
{
  "expo": {
    "plugins": [
      [
        "@getcello/cello-react-native",
        {
          "env": "sandbox"
        }
      ]
    ]
  }
}
```

Next, rebuild your app as described in the ["Adding custom native code"](https://docs.expo.io/workflow/customizing/) guide.

---

## Methods

## Import

### `import Cello from '@getcello/cello-react-native';`

---

### `Cello.initialize(productId, token)`

Initializes the Cello Referral Component in your product

### Options

| Type      | Type   | Required |
| --------- | ------ | -------- |
| productId | string | yes      |
| token     | string | yes      |

### Returns

`Promise<configuration>`

---

### `Cello.showFab()`

Shows the Floating action button or bookmark that launches the Referral Component

---

### `Cello.hideFab()`

Hides the Floating action button or bookmark that launches the Referral Component

---

### `Cello.openWidget()`

Opens Referral Component

---

### `Cello.hideWidget()`

Closes Referral Component

---

### `Cello.shutdown()`

Closes Referral Component

---

### `Cello.getActiveUcc()`

A method to get an active ucc and invite link for the currently logged in user.

### Returns

`{ ucc, link }`

---

### `Cello.updateToken(token)`

Updates the token and also verifies it.

### Options

| Type  | Type   | Required |
| ----- | ------ | -------- |
| token | string | yes      |

### Returns

`Promise<configuration>`

---

### `Cello.changeLanguage(language)`

A method to change the language of the Referral component at runtime without re-initialising it.

### Options

| Type     | Type   | Required |
| -------- | ------ | -------- |
| language | string | yes      |

---

## Usage

[Check example app](https://github.com/getcello/cello-react-native/blob/main/example/src/App.tsx)

---

## Troubleshooting

- #### This project uses AndroidX dependencies, but the 'android.useAndroidX' property is not enabled.

  - To enable `jetifier`, add those two lines to your `gradle.properties` file:
    ```
    android.useAndroidX=true
    android.enableJetifier=true
    ```

- #### When Android app keeps stopping (E/AndroidRuntime: FATAL EXCEPTION: mqt_native_modules)

  - Add those lines to `dependencies` in `./android/app/build.gradle`:
    ```
    implementation 'androidx.appcompat:appcompat:1.6.1'
    ```

- #### When tests with Jest fail mentioning "Cannot read property 'TOKEN_ABOUT_TO_EXPIRE' of undefined"
  - Make a `jest.mock` function with the library:
    ```
    // jest/setup.ts
    jest.mock('@getcello/cello-react-native', () => jest.fn());
    ```

---

## Author

👤 **Cello (https://www.cello.so/)**

## Show your support

Give a ⭐️ if this project helped you!

## 📝 License

This project is [MIT](LICENSE) licensed.

---

Created with ❤️ by [Cello](https://cello.so/)
