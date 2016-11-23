
package test.mzj.com.appstructureproject.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class CommonUtils {

    /**
     * android6.0 动态申请权限
     */
    public final static int REQUEST_PERMISSION_CODE = 125;//不大于256

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        if (context == null) {
            return 0;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        if (context == null) {
            return 0;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        if (context == null) {
            return 0;
        }
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取MAC地址
     *
     * @param context
     * @return
     */
    public static String getMACAddr(Context context) {

        String mac = "00:00:00:00:00:00";
        if (context == null) {
            return mac;
        }
        final WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(
                Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        } else {
            mac = "00:00:00:00:00:00";
        }
        LogHelper.d("ids", "mac is " + mac);
        return mac;
    }

    private static String getMAC(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        final WifiManager wifi = (WifiManager) context.getApplicationContext().getSystemService(
                Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (info == null) {
            return mac;
        }
        mac = info.getMacAddress();
        return mac;
    }

    private static String getMid(Context mContext) {
        TelephonyManager tm = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
        String mid = tm.getDeviceId();
        if (mid == null || mid.trim().length() <= 0) {
            mid = "";
        }
        return mid;
    }

    public static String getMacInfo(Context mContext) {
        String macString = CommonUtils.getMAC(mContext);
        if (macString != null) {
            macString = macString.replace(":", "");
        }
        String mac = macString == null ? getMid(mContext) : macString;
        return mac;
    }

    public static String getApkVersionName(Context context, String mPackageName) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mPackageName, 0);
            String name = info.versionName;
            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getApkVersionCode(Context context) {
        int currentVersionCode = 0;
        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            currentVersionCode = info.versionCode; // 版本号
            System.out.println("currentVersionCode " + currentVersionCode);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch blockd
            e.printStackTrace();
        }
        return currentVersionCode;
    }

    /**
     * 根据包名判断当前apk是否已经安装
     */
    public static boolean isAppInstalled(Context context, String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        // 如果不为空则已经安装
        if (pi != null) {
            return true;
        }
        return false;
    }

    /**
     * 算百分比，输出格式“70%”
     *
     * @param i
     * @param j
     * @return
     */
    public static String calculatePercent(int i, int j) {
        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(2);
        if (j == 0) {
            return ""+nt.format(0);
        }
        double percent = (double) i / (double) j;
        //最后格式化并输出
        LogHelper.v("xq", "百分数： " + nt.format(percent));
        return "" + nt.format(percent);
    }


    /**
     * 获取base64加密的字符串,当前调用腾讯mm的加密算法
     *
     * @param origin
     * @return
     */
    public static String getStringinBase64(String origin) {
        if (origin == null)
            return null;
        String base = Base64.encodeToString(origin.getBytes(), Base64.DEFAULT);
        base = base.replace("\n", "");
        return base;
    }

    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    private static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }

    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * 获取设备imei号
     */
    public static String getImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String result = telephonyManager.getDeviceId();
        if (needGetAnotherId(result)) {
            result = telephonyManager.getSubscriberId();
            if (needGetAnotherId(result)) {
                result = android.provider.Settings.Secure.getString(context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
                if (needGetAnotherId(result)) {
                    result = "000000000000000";
                } else {
                    result = "androidId" + result;
                }
            } else {
                result = "imsi" + result;
            }
        }
        return result;
    }

    private static boolean needGetAnotherId(String id) {
        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(id.replaceAll("0", ""))) {
            return true;
        }
        return false;
    }

    /**
     * map转字符串
     *
     * @param map 需要输出内容的map
     */
    public static String mapToString(Map<String, String> map) {
        String result = new String("");
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            String key = (String) obj;
            String value = (String) map.get(key);
            result = result + (key + "=" + value + ",");
        }
        if (result.length() > 0) {
            result = result.substring(0, result.lastIndexOf(","));
        }
        return result;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenDisplayHeight(Context context) {
        try {
            WindowManager manager = ((WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE));

            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            int h = dm.heightPixels;
            int w = dm.widthPixels;

            if (w > h) {
                return w;
            } else {
                return h;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean hasPermission(Activity mContext, String permission) {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        int checkCallPhonePermission = ContextCompat.checkSelfPermission(mContext, permission);
        if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{permission}, REQUEST_PERMISSION_CODE);
            return false;
        }
        return true;
    }

    /**
     * 检测相机是否存在 s
     */
    public static boolean isExistCamera(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    /**
     * 是否是插件模式
     *
     * @return
     */
    public static boolean isPluginMode(Context context) {
        String path = "/data/data/" + context.getPackageName();
        LogHelper.d("isPluginMode", "path=" + path);
        String codePath = context.getPackageCodePath();
        String codePathSDcard = codePath;
        LogHelper.d("isPluginMode", "path=" + codePath);
        codePath = codePath.replace("data/app", "data/data");
        codePathSDcard = codePath.replace("mnt/asec", "data/data");//有些手机是装在sd卡上的
        LogHelper.d("isPluginMode", "replace path=" + codePath);
        LogHelper.d("isPluginMode", "codePathSDcard path=" + codePathSDcard);
        if (codePath.contains(path)|| codePathSDcard.contains(path)) {
            LogHelper.v("isPluginMode",""+codePath.contains(path));
            LogHelper.v("isPluginMode",""+codePathSDcard.contains(path));
            return false;
        }
        return true;
    }

    /**
     * 字节转long
     *
     * @param b
     * @return
     */
    public static long byte2Long(byte[] b) {
        long l = 0;
        if (b == null) {
            return l;
        }

        int length = b.length;

        for (int i = 0; i < length; i++) {
            if (i > 7) {
                break;
            }
            l |= b[i] << (8 * i);
        }
        return l;
    }

    /**
     * long转字节
     *
     * @param x
     * @return
     */
    public static byte[] long2Byte(long x) {
        byte[] bb = new byte[8];
        bb[0] = (byte) (x >> 56);
        bb[1] = (byte) (x >> 48);
        bb[2] = (byte) (x >> 40);
        bb[3] = (byte) (x >> 32);
        bb[4] = (byte) (x >> 24);
        bb[5] = (byte) (x >> 16);
        bb[6] = (byte) (x >> 8);
        bb[7] = (byte) (x);
        return bb;
    }

    /**
     * 获取友盟渠道名
     *
     * @param ctx 此处习惯性的设置为activity，实际上context就可以
     * @return 如果没有获取成功，那么返回值为空
     */
    public static String getChannelName(Context ctx) {
        if (ctx == null) {
            return null;
        }
        String channelName = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        //此处这样写的目的是为了在debug模式下也能获取到渠道号，如果用getString的话只能在Release下获取到。
                        channelName = applicationInfo.metaData.get("UMENG_CHANNEL") + "";
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }


    /**
     * 获取application中指定的meta-data
     *
     * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
     */
    public static String getAppMetaData(Context ctx, String key) {
        if (ctx == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.get(key) + "";
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }


}
