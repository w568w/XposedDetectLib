# XposedDetectLib
A powerful detection for any kind of xposed installation.
# Support Version
- rovo89's Xposed
- Any xposed variation based on Riru(eg. [Edxposed](https://github.com/ElderDrivers/EdXposed))
# Feature
- Lightweight
- Anti-anti-cloak (Test on MagiskHide,Zuper,RootCloak and so on)git 
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
implementation 'com.github.w568w:XposedDetectLib:1.1'
```
Call this method to do an inspection:
```java
XposedDetect.getInstance(getPackageManager()).detectXposed()
```
Return `true` if detecting xposed.
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
