package test.mzj.com.appstructureproject.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;

import test.mzj.com.appstructureproject.R;
import test.mzj.com.appstructureproject.okhttp.OkHttpManager;
import test.mzj.com.appstructureproject.valueanimator.ValueAnimatorUtil;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private TextView mTextView;
    private Button mResetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mTextView = (TextView) findViewById(R.id.textview_test);
        mResetBtn = (Button) findViewById(R.id.button_reset);
        mResetBtn.setOnClickListener(this);
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

    private void intoFunction(){
//        ValueAnimatorUtil.setAlphaAnimation(mTextView);
//        ValueAnimatorUtil.setRotateAnimation(mTextView);
//        ValueAnimatorUtil.setScaleYAnimation(mTextView);
//        ValueAnimatorUtil.setTranslationXAnimation(mTextView);

        ValueAnimatorUtil.setSetAnimation(mTextView);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_reset:
                intoFunction();
            break;
        }
    }
}
