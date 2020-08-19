# XposedDetectLib
A powerful detection for any kind of xposed installation.
# Support Version
- rovo89's Xposed
- Any xposed variation based on Riru(eg. [Edxposed](https://github.com/ElderDrivers/EdXposed))
# Feature
- Lightweight
- Anti-cloak (Test on `MagiskHide`,`Zuper`,`RootCloak` and so on) 
# Usage
**NDK is needed to compile the library.**   
1. Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```

2. Add the dependency
```groovy
implementation 'com.github.w568w:XposedDetectLib:1.2'
```

3. Invoke this method to do a total inspection(allowed to run on main thread directly):
```java
XposedDetect.getInstance(getPackageManager()).detectXposed()
```
Or to prevent the module itself being hooked,invoke this one to do a native inspection:
```java
NativeDetect.detectXposed(Process.myPid())
```
Return `true` if detecting xposed.

4. Proguard settings:  
(The settings below are default in `${sdk.dir}\tools\proguard\proguard-android-optimize.txt`.If not, add these manually to your project level `proguard-rules.pro`)
```proguard
# For native methods, see http://proguard.sourceforge.net/manual/examples.html#native
-keepclasseswithmembernames class * {
    native <methods>;
}

# Understand the @Keep support annotation.
-keep class android.support.annotation.Keep

-keep @android.support.annotation.Keep class * {*;}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}

-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}
```
# License
```text
Copyright (C) 2020 w568w

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
