
package test.mzj.com.appstructureproject.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

public class SDCardUtil {

    public synchronized static void mkdirs(String dir) {
        File filedir = new File(dir);
        if (filedir != null && !filedir.exists()) {
            filedir.mkdirs();
        }
    }

    public static boolean isExsit() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public static double[] getSDCardCapacityInfo(String path) {
        double[] capacitys = new double[]{
                0.0, 0.0, 0.0
        };
        String state = Environment.getExternalStorageState();
        try {
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                StatFs stat = new StatFs(path);

                long blockSize = stat.getBlockSize();
                long availableBlocks = stat.getBlockCount();
                long availaBlock = stat.getAvailableBlocks();

                double totalCapacity = availableBlocks * blockSize;
                double vailaleCapacity = availaBlock * blockSize;
                LogHelper.d("SDUtil", "--------------------------------------totalCapacity :"
                        + totalCapacity + ",vailaleCapacity:" + vailaleCapacity);
                capacitys[0] = totalCapacity;
                capacitys[1] = vailaleCapacity;
                capacitys[2] = totalCapacity - vailaleCapacity;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

        return capacitys;
    }

}
