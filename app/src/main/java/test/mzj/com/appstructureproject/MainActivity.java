package test.mzj.com.appstructureproject;

import android.os.Bundle;
import android.os.Environment;

import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;

import test.mzj.com.appstructureproject.activity.BaseActivity;
import test.mzj.com.appstructureproject.okhttp.OkHttpManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void doGet() {
        String url = "https://www.baidu.com";
        OkHttpManager.getAsyn(url, new OkHttpManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(String u) {
                //UI线程
            }
        });
    }

    private void postFile(String url, File file) {
        try {
            OkHttpManager.postAsyn(url, new OkHttpManager.ResultCallback<String>() {
                        @Override
                        public void onError(Request request, Exception e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(String result) {
                        }
                    },
                    file, "mFileKey",
                    new OkHttpManager.Param[]{
                            new OkHttpManager.Param("username", "xxx"),
                            new OkHttpManager.Param("password", "xxx")}
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downLoadFile(String downLoadUrl) {
        OkHttpManager.downloadAsyn(downLoadUrl,
                Environment.getExternalStorageDirectory().getAbsolutePath(),
                new OkHttpManager.ResultCallback<String>() {
                    @Override
                    public void onError(Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                        //文件下载成功，这里回调的reponse为文件的absolutePath
                    }
                });
    }
}
