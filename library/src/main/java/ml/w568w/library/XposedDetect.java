package ml.w568w.library;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.util.Log;

import androidx.annotation.Keep;

import com.jrummyapps.android.shell.CommandResult;
import com.jrummyapps.android.shell.Shell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import de.robv.android.xposed.XposedBridge;
import ml.w568w.checkxposed.util.NativeDetect;

public class XposedDetect {
    private static final String[] XPOSED_APPS_LIST = new String[]{"de.robv.android.xposed.installer", "io.va.exposed", "org.meowcat.edxposed.manager", "com.topjohnwu.magisk", "com.doubee.ig", "com.soft.apk008v", "com.soft.controllers", "biz.bokhorst.xprivacy"};
    private static final File ZUPER_HOOK_PATH = new File("/system/xbin/ZUPERFAKEFILE");
    private static XposedDetect instance;
    private static String[] sXposedModules = Contast.APP_LIST.split("\n");
    private PackageManager mManager;
    private int status;

    public static XposedDetect getInstance(PackageManager packageManager) {
        if (instance == null) {
            instance = new XposedDetect(packageManager);
        }
        return instance;
    }

    private XposedDetect(PackageManager packageManager) {
        mManager = packageManager;
        if (mManager == null) {
            throw new IllegalArgumentException("PackageManager cannot be null");
        }
    }

    public boolean detectXposed() {
        try {
            FutureTask<Void> futureTask = new FutureTask<>(new CheckThread());
            new Thread(futureTask).start();
            futureTask.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status > 0;
    }

    @Keep
    private int check1() {
        return testClassLoader("de.robv.android.xposed.XposedHelpers") || testUseClassDirectly() ? 1 : 0;
    }

    private boolean testClassLoader(String clazz) {
        try {
            Class.forName(clazz);
            ClassLoader.getSystemClassLoader()
                    .loadClass(clazz);

            return true;
        } catch (ClassNotFoundException e) {
            return e.getMessage() == null;
        }
    }

    private boolean testUseClassDirectly() {
        try {
            XposedBridge.log("fuck");
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    @Keep
    private int check2() {
        return checkContains("XposedBridge") ? 1 : 0;
    }

    @Keep
    private int check3() {
        try {
            throw new Exception();
        } catch (Exception e) {
            StackTraceElement[] arrayOfStackTraceElement = e.getStackTrace();
            for (StackTraceElement s : arrayOfStackTraceElement) {
                if ("de.robv.android.xposed.XposedBridge".equals(s.getClassName())) {
                    return 1;
                }
            }
            return 0;
        }
    }

    @Keep
    private int check4() {
        try {
            List<PackageInfo> list = mManager.getInstalledPackages(0);
            for (PackageInfo info : list) {
                for (String pkg : XPOSED_APPS_LIST) {
                    if (pkg.equals(info.packageName)) {
                        return 1;
                    }
                }

            }
        } catch (Throwable ignored) {

        }
        return 0;
    }

    @Keep
    private int check5() {
        try {
            Method method = Throwable.class.getDeclaredMethod("getStackTrace");
            return Modifier.isNative(method.getModifiers()) ? 1 : 0;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Keep
    private int check6() {
        return System.getProperty("vxp") != null ? 1 : 0;
    }


    /**
     * @param paramString check string
     * @return whether check string is found in maps
     */
    public static boolean checkContains(String paramString) {
        try {
            HashSet<String> localObject = new HashSet<>();
            // 读取maps文件信息
            BufferedReader localBufferedReader =
                    new BufferedReader(new FileReader("/proc/" + Process.myPid() + "/maps"));
            while (true) {
                String str = localBufferedReader.readLine();
                if (str == null) {
                    break;
                }
                localObject.add(str.substring(str.lastIndexOf(" ") + 1));
            }
            if (localObject.isEmpty() && Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                return true;
            }
            localBufferedReader.close();
            for (String aLocalObject : localObject) {
                if (aLocalObject.contains(paramString)) {
                    return true;
                }
            }
        } catch (Throwable ignored) {
        }
        return false;
    }

    @Keep
    private int check7() {
        CommandResult commandResult = Shell.run("ls /system/lib");
        String out = commandResult.getStdout();
        boolean result = out.contains("xposed") || out.contains("Xposed");
        return commandResult.isSuccessful() ? (result ? 1 : 0) : 0;
    }

    @Keep
    private int check8() {
        try {
            return NativeDetect.detectXposed(Process.myPid()) ? 1 : 0;
        } catch (Throwable t) {
            return 0;
        }
    }

    @Keep
    private int check9() {
        boolean result;
        try {
            result = System.getenv("CLASSPATH").contains("XposedBridge");
        } catch (NullPointerException e) {
            result = false;
        }

        return result ? 1 : 0;
    }

    @Keep
    private int check10() {
        boolean result = testClassLoader("com.elderdrivers.riru.edxp.config.EdXpConfigGlobal");
        return result ? 1 : 0;
    }

    @Keep
    private int check11() {
        try {
            List<PackageInfo> list = mManager.getInstalledPackages(0);
            for (PackageInfo info : list) {
                for (String pkg : sXposedModules) {
                    if (pkg.equals(info.packageName)) {
                        return 1;
                    }
                }

            }
        } catch (Throwable ignored) {

        }
        if (ZUPER_HOOK_PATH.equals(new File("su"))) {
            return 1;
        }
        return 0;
    }

    private class CheckThread implements Callable<Void> {

        @Override
        public Void call() throws Exception {
            for (int i = 0; i < 11; i++) {
                Method method = XposedDetect.class.getDeclaredMethod("check" + (i + 1));
                method.setAccessible(true);
                try {
                    status += (int) method.invoke(XposedDetect.this);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
