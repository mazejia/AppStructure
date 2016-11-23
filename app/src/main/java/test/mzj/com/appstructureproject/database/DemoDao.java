package test.mzj.com.appstructureproject.database;

import android.content.Context;

/**
 *  Demo DAO
 */
public class DemoDao extends DaoHelper {
    private static final String TAG = "DemoDao";
    private static volatile DemoDao instance = null;

    public DemoDao(Context context) {
        super(context);
    }

    public static DemoDao getInstance(Context context) {
        if (instance == null) {
            synchronized (DemoDao.class) {
                if (instance == null) {
                    instance = new DemoDao(context.getApplicationContext());
                }
            }
        }
        return instance;
    }


//    public void insertTopic(long topicId, int posts, String userID) {
//        synchronized (MyOpenHelper.DB_LOCK) {
//            SQLiteDatabase db = null;
//            try {
//                db = helper.getWritableDatabase();
//                db.execSQL(DatabaseConst.LikedTeam.TABLE_INSERT, new Object[]{topicId, posts, userID});
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                closeDB(db);
//            }
//        }
//    }
}
