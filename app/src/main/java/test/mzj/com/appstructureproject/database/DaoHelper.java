
package test.mzj.com.appstructureproject.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DaoHelper {
    protected Context mContext = null;

    protected MyOpenHelper helper = null;

    protected String SELECT_FROM = "SELECT * FROM ";

    protected String ORDER_BY = " ORDER BY ";

    protected String DESC = " DESC";

    protected String ASC = " ASC";

    protected String WHERE = " WHERE ";

    protected String SELECT = "SELECT ";

    protected String FROM = " FROM ";

    protected String AND = " AND ";

    protected String DELETE_FROM = "DELETE FROM ";

    protected DaoHelper(Context context) {
        helper = new MyOpenHelper(context);
        mContext = context;
    }

    public void closeDB() {
        if (helper != null) {
            helper.close();
        }
    }

    protected void closeDB(SQLiteDatabase db) {
        if (db != null) {
            db.close();
        }
    }

    public void closeCursor(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    protected void closeCursorAndDB(Cursor cursor, SQLiteDatabase db) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
    }
}
