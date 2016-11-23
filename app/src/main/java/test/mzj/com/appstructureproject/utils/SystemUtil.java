
package test.mzj.com.appstructureproject.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public final class SystemUtil {

    /**
     * 获取屏幕最小值
     *
     * @param context
     * @return
     */
    public static int getScreenDisplayMinWidth(Context context) {
        try {
            WindowManager manager = ((WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE));

            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            int h = dm.heightPixels;
            int w = dm.widthPixels;

            if (w < h) {
                return w;
            } else {
                return h;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取屏幕像素密度
     *
     * @param context
     * @return
     */
    public static int getDensityDpi(Context context) {
        try {
            WindowManager manager = ((WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE));

            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            int ppi = dm.densityDpi;
            return ppi;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String getNetState(Context context) {
        // A=WIFI;B=3G/GPRS
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo(); // WIFI
        if (activeNetInfo == null) {
            // LogHelper.i("Test", "没有联网状态");
            return "null";
        }

        if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            if (activeNetInfo.isConnected()) {
                // LogHelper.i("Test", "WIFI 状态");
                return "A";
            }
        }

        if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            if (activeNetInfo.isConnected()) {
                // LogHelper.i("Test", "3G 状态。");
                return "B";
            }
        }
        return "null";
    }

    /**
     * 判断是否开启了自动亮度调节
     */
    public static boolean isAutoBrightness(Activity activity) {
        try {
            return Settings.System.getInt(activity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static float getScreenBrightness(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        return lp.screenBrightness;
    }

    /**
     * 获取屏幕的亮度
     *
     * @throws SettingNotFoundException
     */
    public static int getSettingBrightness(Activity activity) {
        try {
            return Settings.System.getInt(activity.getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (SettingNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 设置当前屏幕亮度（并非保存）
     *
     * @param progress
     */
    public static void setScreenBrightness(Activity activity, float brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        // lp.screenBrightness = (float) brightness / 255f; // 0-1
        lp.screenBrightness = brightness;
        activity.getWindow().setAttributes(lp);
    }
}
