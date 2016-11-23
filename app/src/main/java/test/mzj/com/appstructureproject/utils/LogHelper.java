/**
 * Copyright (c) 2011 France Telecom R&D Beijing
 * Advanced Software Center
 * Raycom InfoTech Park C, Science Institute South Road Beijing China
 * All rights reserved.
 * <p>
 * Orange DoSomeGood Project
 * File: LogHelper.java
 * Version 1.0 created Aug 24, 2011
 */

package test.mzj.com.appstructureproject.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Daquan.Jing Revision History: <<Date>> <<Who>> <<What>> 4 21, 2011
 *          Daquan.jing initial version
 */
public class LogHelper {

    private static final int RETURN_NOLOG = 99;
    private static final String RETURN_NOLOG_STRING = "RETURN_NOLOG";
    private static FileLog logToFile;
    private static boolean enableDefaultLog = false;
    private static boolean enableFileLog = false;

    public static void setLogEnable(boolean printLog, boolean saveLogToFile) {
        enableDefaultLog = printLog;
        enableFileLog = saveLogToFile;
        if (enableFileLog) {
            try {
                if (logToFile == null)
                    logToFile = new FileLog();
            } catch (RuntimeException e) {
                Log.e("LogHelper", e.toString());
            } catch (IOException e) {
                Log.e("LogHelper", e.toString());
            }
        } else {
            if (logToFile != null) {
                logToFile.closeFile();
            }
        }
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (msg == null) {
            return 0;
        }
        msg = System.currentTimeMillis() + " " + msg;

        dfile(tag, msg);
        return enableDefaultLog ? Log.d(tag, msg, tr) : RETURN_NOLOG;
    }

    public static int d(String tag, String msg) {
        if (msg == null) {
            return 0;
        }
        msg = System.currentTimeMillis() + " " + msg;

        dfile(tag, msg);
        return enableDefaultLog ? Log.d(tag, msg) : RETURN_NOLOG;
    }

    public static int e(String tag, String msg) {
        if (msg == null) {
            return 0;
        }
        msg = System.currentTimeMillis() + " " + msg;
        efile(tag, msg);
        return enableDefaultLog ? Log.e(tag, msg) : RETURN_NOLOG;
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (msg == null) {
            return 0;
        }
        msg = System.currentTimeMillis() + " " + msg;

        efile(tag, msg);
        return enableDefaultLog ? Log.e(tag, msg, tr) : RETURN_NOLOG;
    }

    public static String getStackTraceString(Throwable tr) {
        return enableDefaultLog ? Log.getStackTraceString(tr) : RETURN_NOLOG_STRING;
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (msg == null) {
            return 0;
        }
        msg = System.currentTimeMillis() + " " + msg;
        efile(tag, msg);
        return enableDefaultLog ? Log.i(tag, msg, tr) : RETURN_NOLOG;
    }

    public static int i(String tag, String msg) {
        if (msg == null) {
            return 0;
        }
        msg = System.currentTimeMillis() + " " + msg;
        efile(tag, msg);
        return enableDefaultLog ? Log.i(tag, msg) : RETURN_NOLOG;
    }

    public static boolean isLoggable(String tag, int level) {
        return enableDefaultLog ? Log.isLoggable(tag, level) : false;
    }

    public static int println(int priority, String tag, String msg) {
        return enableDefaultLog ? Log.println(priority, tag, msg) : RETURN_NOLOG;
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (msg == null) {
            return 0;
        }
        msg = System.currentTimeMillis() + " " + msg;
        dfile(tag, msg);
        return enableDefaultLog ? Log.v(tag, msg, tr) : RETURN_NOLOG;
    }

    public static int v(String tag, String msg) {
        if (msg == null) {
            return 0;
        }
        msg = System.currentTimeMillis() + " " + msg;
        dfile(tag, msg);
        return enableDefaultLog ? Log.v(tag, msg) : RETURN_NOLOG;
    }

    public static int w(String tag, String msg) {
        if (msg == null) {
            return 0;
        }
        msg = System.currentTimeMillis() + " " + msg;
        efile(tag, msg);
        return enableDefaultLog ? Log.w(tag, msg) : RETURN_NOLOG;
    }

    public static int w(String tag, Throwable tr) {
        return enableDefaultLog ? Log.w(tag, tr) : RETURN_NOLOG;
    }

    public static int w(String tag, String msg, Throwable tr) {
        return enableDefaultLog ? Log.w(tag, msg, tr) : RETURN_NOLOG;
    }

    public static void efile(String tag, String msg) {

        if (logToFile != null && enableFileLog) {
            logToFile.writeFileLog(Log.ERROR, tag, msg);
        }
    }

    public static void dfile(String tag, String msg) {

        if (logToFile != null && enableFileLog) {
            logToFile.writeFileLog(Log.DEBUG, tag, msg);
        }
    }

    public static void toast(Context context, String s) {
        if(enableDefaultLog){
            ToastUtils.toast(context,s);
        }

    }

    // public static void writeLogToFile(String tag, String msg) {
    //
    // if (logToFile != null) {
    // logToFile.writeFileLog(Log.DEBUG, tag, msg);
    // }
    // }

}

class FileLog {

    public static final String SD_CARD = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    public final static String APPROOTNAME = "SportsLog";
    public final static String MEDIAPATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + "/" + APPROOTNAME + "/";
    public static final String FILE1 = MEDIAPATH + APPROOTNAME + ".log";
    // public final static String APPROOTNAME = "DoSomeGood";
    // public final static String MEDIAPATH =
    // Environment.getExternalStorageDirectory().getAbsolutePath()
    // + "/" + APPROOTNAME + "/";
    public static final int MAX_FILE_SIZE = 20; // M
    private static final String TAG = "LogHelper";
    private static String strPriority[];

    static {
        strPriority = new String[8];
        strPriority[0] = "";
        strPriority[1] = "";
        strPriority[2] = "verbose";
        strPriority[3] = "debug";
        strPriority[4] = "info";
        strPriority[5] = "warn";
        strPriority[6] = "error";
        strPriority[7] = "ASSERT";
    }

    // bytes
    private FileWriter fileWriter;

    public FileLog() throws RuntimeException, IOException {
        File sdcard = new File(SD_CARD);


        if (!sdcard.exists()) {
            throw new RuntimeException("SD card not exists!");
        } else {
            SDCardUtil.mkdirs(MEDIAPATH);
            File file1 = new File(FILE1);
            if (!file1.exists()) {

                if (!file1.createNewFile()) {
                    Log.e(TAG, "Create new file failed.");
                }

            } else {
//                long fileSize = (file1.length() >>> 20);// convert to M bytes
//                if (fileSize > MAX_FILE_SIZE) {
//                    try {
//                        file1.delete();
//                        file1.createNewFile();
//                    } catch (IOException e) {
//                        LogHelper.e(TAG, e.getMessage());
//                    }
//                }
            }
            fileWriter = new FileWriter(file1, true);
        }
    }

    public void closeFile() {
        if (fileWriter != null) {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkFile() {
        File file1 = new File(FILE1);
        if (!file1.exists()) {
            try {
                if (!file1.createNewFile()) {
                    Log.e(TAG, "Create new file failed.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // we use one space to separate elements
    public void writeFileLog(int priority, String tag, String message) {
        checkFile();
        Date date = new Date();
        SimpleDateFormat simpleDateFormate = new SimpleDateFormat("yyyy:MM:dd kk:mm:ss.SSS");
        String strLog = simpleDateFormate.format(date);

        StringBuffer sb = new StringBuffer(strLog);
        sb.append(' ');
        sb.append(strPriority[priority]);
        sb.append(' ');
        sb.append(tag);
        sb.append(' ');
        sb.append(message);
        sb.append('\n');
        strLog = sb.toString();

        try {
            fileWriter.write(strLog);
            fileWriter.flush();
        } catch (Exception e) {
            Log.e("FileLog", "", e);
        }
    }
}
