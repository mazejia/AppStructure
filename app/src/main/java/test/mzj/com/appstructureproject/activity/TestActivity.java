package test.mzj.com.appstructureproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import test.mzj.com.appstructureproject.R;

/**
 * Created by Administrator on 2018/1/31 0031.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
