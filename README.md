# XposedDetectLib
A powerful detection for any kind of xposed installation.
# Support Version
- rovo89's Xposed
- Any xposed variation based on Riru(eg. [Edxposed](https://github.com/ElderDrivers/EdXposed))
# Feature
- Lightweight
- Anti-anti-cloak (Test on MagiskHide,Zuper,RootCloak and so on)
# Usage
**NDK needed to compile the library.**   
Call this method to do an inspection:
```java
XposedDetect.getInstance(getPackageManager()).detectXposed()
```
Return `true` if detecting xposed.
# License