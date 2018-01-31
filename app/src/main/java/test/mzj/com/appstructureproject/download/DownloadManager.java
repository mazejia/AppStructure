package test.mzj.com.appstructureproject.download;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class DownloadManager {
    public static final String FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mzj";
    private DownDao helper;
    private SQLiteDatabase db;
    private OnProgressListener listener;//进度回调监听
    private Map<String, FileInfo> map = new HashMap<>();//保存正在下载的任务信息
    private static DownloadManager manger;


    private DownloadManager(DownDao helper, OnProgressListener listener) {
        this.helper = helper;
        this.listener = listener;
    }

    /**
     * 单例模式
     *
     * @param helper   数据库帮助类
     * @param listener 下载进度回调接口
     * @return
     */
    public static DownloadManager getInstance(DownDao helper, OnProgressListener listener) {
        if (manger == null) {
            synchronized (DownloadManager.class) {
                if (manger == null) {
                    manger = new DownloadManager(helper, listener);
                }
            }
        }
        return manger;
    }

    /**
     * 开始下载任务
     */
    public void start(String url) {
        FileInfo info = helper.query(url);
        map.put(url, info);
        //开始任务下载
        new DownLoadTask(map.get(url), helper, listener).start();
    }

    /**
     * 停止下载任务
     */
    public void stop(String url) {
        map.get(url).setStop(true);
    }

    /**
     * 重新下载任务
     */
    public void restart(String url) {
        stop(url);
        try {
            File file = new File(FILE_PATH, map.get(url).getFileName());
            if (file.exists()) {
                file.delete();
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        helper.update(0,0,url);
        start(url);
    }

    /**
     * 获取当前任务状态
     */
    public boolean getCurrentState(String url) {
        return map.get(url).isDownLoading();
    }

    /**
     * 添加下载任务
     *
     * @param info 下载文件信息
     */
    public void addTask(FileInfo info) {
        //判断数据库是否已经存在这条下载信息
        if (!helper.isExist(info.getUrl())) {
            helper.insert(info.getUrl(),info.getFileName(),info.getLength(),info.getFinished());
            map.put(info.getUrl(), info);
        } else {
            //从数据库获取最新的下载信息
            FileInfo o = helper.query(info.getUrl());
            if (!map.containsKey(info.getUrl())) {
                map.put(info.getUrl(), o);
            }
        }
    }
}
