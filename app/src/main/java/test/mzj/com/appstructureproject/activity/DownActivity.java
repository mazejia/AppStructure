package test.mzj.com.appstructureproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daimajia.numberprogressbar.NumberProgressBar;

import test.mzj.com.appstructureproject.R;
import test.mzj.com.appstructureproject.download.DownDBHelper;
import test.mzj.com.appstructureproject.download.DownDao;
import test.mzj.com.appstructureproject.download.DownloadManager;
import test.mzj.com.appstructureproject.download.FileInfo;
import test.mzj.com.appstructureproject.download.OnProgressListener;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class DownActivity extends Activity implements OnProgressListener {
    private NumberProgressBar pb;//进度条
    private DownloadManager downLoader = null;
    private FileInfo info;
    private DownDao downDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down);
        initData();
    }

    private void initData(){
        downDao = DownDao.Instance(this);
        pb = (NumberProgressBar) findViewById(R.id.pb);
        final Button start = (Button) findViewById(R.id.start);//开始下载
        final Button restart = (Button) findViewById(R.id.restart);//重新下载
        downLoader = DownloadManager.getInstance(downDao,this);
        info = new FileInfo("Kuaiya482.apk", "http://downloadz.dewmobile.net/Official/Kuaiya482.apk");
        downLoader.addTask(info);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downLoader.getCurrentState(info.getUrl())) {
                    downLoader.stop(info.getUrl());
                    start.setText("开始下载");
                } else {
                    downLoader.start(info.getUrl());
                    start.setText("暂停下载");
                }
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoader.restart(info.getUrl());
                start.setText("暂停下载");
            }
        });
    }

    @Override
    public void updateProgress(final int max, final int progress) {
        pb.setMax(max);
        pb.setProgress(progress);
    }
}
