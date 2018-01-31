package test.mzj.com.appstructureproject.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zeJiaMa on 2018/1/30 0030.
 */

public class DownDBHelper extends SQLiteOpenHelper{
    public static final Object DB_LOCK = new Object();

    public DownDBHelper(Context context) {
        super(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DaoConstant.Download.tableCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
