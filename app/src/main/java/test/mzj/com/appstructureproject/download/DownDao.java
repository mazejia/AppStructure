package test.mzj.com.appstructureproject.download;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;



/**
 * Created by Administrator on 2018/1/30 0030.
 */

public class DownDao extends DAOHelper {

    private static volatile DownDao mInstance;

    public static DownDao Instance(Context context){
        if(mInstance == null){
            synchronized (DownDao.class){
                if(mInstance == null){
                    mInstance = new DownDao(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private DownDao(Context context){
        super(context);
    }

    public boolean isExist(String url) {
        synchronized (DownDBHelper.DB_LOCK) {
            if(TextUtils.isEmpty(url)){
                return false;
            }
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = helper.getReadableDatabase();
                cursor = db.rawQuery(DaoConstant.Download.tableQuery, new String[]{url});
                if (cursor.moveToNext()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                closeCursorAndDB(cursor, db);
            }
            return false;
        }
    }

    public FileInfo query(String url){
        synchronized (DownDBHelper.DB_LOCK){
            FileInfo info = new FileInfo();
            if(TextUtils.isEmpty(url)){
                return info;
            }
            SQLiteDatabase db = null;
            Cursor cursor = null;
            try {
                db = helper.getReadableDatabase();
                cursor = db.rawQuery(DaoConstant.Download.tableQuery,new String[]{url});
                while (cursor.moveToNext()){
                    info.setUrl(cursor.getString(cursor.getColumnIndex(DaoConstant.Download.URL)));
                    info.setFileName(cursor.getString(cursor.getColumnIndex(DaoConstant.Download.FILE_NAME)));
                    info.setFinished(cursor.getInt(cursor.getColumnIndex(DaoConstant.Download.FINISHED)));
                    info.setLength(cursor.getInt(cursor.getColumnIndex(DaoConstant.Download.LENGTH)));
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                closeCursorAndDB(cursor,db);
            }
            return info;
        }
    }

    public void update(int length,int finished,String url){
        synchronized(DownDBHelper.DB_LOCK){
            if(TextUtils.isEmpty(url)){
                return;
            }
            SQLiteDatabase db = null;
            try{
                db = helper.getWritableDatabase();
                db.execSQL(DaoConstant.Download.tableUpdate,new Object[]{
                        length,finished,url});
            }catch (Exception e){
                e.printStackTrace();
            } finally {
                closeDB(db);
            }
        }
    }

    public void insert(String url,String name,int length,int finished){
        synchronized(DownDBHelper.DB_LOCK){
            SQLiteDatabase db = null;
            try{
                db = helper.getWritableDatabase();
                db.execSQL(DaoConstant.Download.tableInsert,new Object[]{url,name,length,finished});
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                closeDB(db);
            }
        }
    }

}
