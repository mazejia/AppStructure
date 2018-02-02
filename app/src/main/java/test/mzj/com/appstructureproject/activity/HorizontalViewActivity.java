package test.mzj.com.appstructureproject.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.daimajia.numberprogressbar.NumberProgressBar;

import test.mzj.com.appstructureproject.R;
import test.mzj.com.appstructureproject.download.DownDao;
import test.mzj.com.appstructureproject.download.DownloadManager;
import test.mzj.com.appstructureproject.download.FileInfo;
import test.mzj.com.appstructureproject.download.OnProgressListener;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class HorizontalViewActivity extends Activity{
    private ListView lv_one;
    private ListView lv_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_view);
        initData();
    }

    private void initData(){
        lv_one = (ListView) findViewById(R.id.lv_one);
        lv_two = (ListView) findViewById(R.id.lv_two);
        String[] strs1 = {"1","2","3","4","5","6","7","8","9","10"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,strs1);

        String[] str2 ={"A","B","C","D","E","F","G","H","I","J"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,str2);

        lv_one.setAdapter(adapter1);
        lv_two.setAdapter(adapter2);
    }
}
