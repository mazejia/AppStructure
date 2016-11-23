package test.mzj.com.appstructureproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mazejia on 2016/11/23.
 */

public class MyOpenHelper extends SQLiteOpenHelper {
    public static String DB_LOCK = "db_lock";

    public MyOpenHelper(Context context){
        super(context, DatabaseConst.DB_NAME, null, DatabaseConst.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onCreateAllTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1:
            case 2:
            case 3:
                break;
            default:
                dropAllTables(db);
                onCreateAllTables(db);
        }
    }

    private void onCreateAllTables(SQLiteDatabase db) {
//        db.execSQL(DatabaseConst.LikedTeam.tableCreate);
    }

    private void dropAllTables(SQLiteDatabase db) {
//        db.execSQL(DatabaseConst.LikedTeam.deleteTable);
    }
}
