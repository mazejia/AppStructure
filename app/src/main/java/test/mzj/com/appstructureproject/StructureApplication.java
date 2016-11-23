package test.mzj.com.appstructureproject;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

import test.mzj.com.appstructureproject.utils.LogHelper;
import test.mzj.com.appstructureproject.utils.ToastUtils;

public class StructureApplication extends Application {
    private static final String TAG = "StructureApplication";

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // 初始化配置
        initConfig();
    }

    public static Context getAppContext() {
        if (context != null) {
            return context.getApplicationContext();
        }
        return context;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    // 初始化
    private void initConfig() {
        // 率先设置Debug开关
        initDebug();
    }

    /**
     * 设置debug开关
     */
    private void initDebug() {
        LogHelper.setLogEnable(true,false);
        ToastUtils.setLogEnable(true);
    }

    /*开启严苛模式*/
    public void initDebugModel() {
        // check if android:debuggable is set to true
        StrictMode.VmPolicy.Builder vmBuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.ThreadPolicy.Builder threadBuilder = new StrictMode.ThreadPolicy.Builder();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            threadBuilder.detectAll();
            StrictMode.setThreadPolicy(threadBuilder.build());
            vmBuilder.detectAll();
            StrictMode.setVmPolicy(vmBuilder.build());
        }
    }


    /**
     * 获取CPU核心数
     *
     * @return
     */
    private int getNumCores() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
