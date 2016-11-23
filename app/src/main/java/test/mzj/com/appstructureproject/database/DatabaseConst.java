package test.mzj.com.appstructureproject.database;

/**
 * Created by mazejia on 2016/11/23.
 */

public interface DatabaseConst {

    /**
     * 数据库名称
     */
    String DB_NAME = "app.db";

    /**
     * 数据库版本号
     */
    int DB_VERSION = 1;

    interface LikedTeam {
        //数据表名称
        String TABLE_NAME = "LikedTeam";
        //用户的token
        String TOKEN = "token";
        //赛事id
        String MATCH_ID = "matchId";
        //支持的球队ID
        String TEAM_ID = "teamId";

//        String tableCreate = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME // 创建表
//                + "("
//                // + "_id integer primary key autoincrement," // id
//                + MATCH_ID + " INTEGER PRIMARY KEY, " //
//                + EVENT_ID + " INTEGER, " //
//                + MATCH_INFO + " VARCHAR, " //
//                + Match_TYPE + " VARCHAR"//
//                + ")";
//
//        String tableInsert = "INSERT INTO " + TABLE_NAME // 插入表
//                + "(" //
//                + MATCH_ID + "," //
//                + EVENT_ID + "," //
//                + MATCH_INFO + "," //
//                + Match_TYPE//
//                + ") VALUES(?,?,?,?)";
//
//        String tableSelectByType = "select * from " + TABLE_NAME //
//                + " where "//
//                + Match_TYPE//
//                + "=?"//
//                + " AND "//
//                + EVENT_ID//
//                + "=?";
//
//        String tableSelectByID = "select * from " + TABLE_NAME //
//                + " where "//
//                + MATCH_ID//
//                + "=?";
//
//
//        String tableSelectAll = "select * from " + TABLE_NAME;
//
//
//        String tableDeleteById = "DELETE FROM " + TABLE_NAME
//                + " WHERE " //
//                + MATCH_ID//
//                + " =?";
//        //清空表
//        String tableClear = "delete from " + TABLE_NAME;
//
//        // 删除表
//        String deleteTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
//
//        //分页获取数据，每页10条
//        String SELECT_10_BY_PAGE = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + MATCH_ID + " LIMIT ?,?";
//        //获取预约比赛的数量
//        String GET_TABLE_COUNT = "SELECT count(*) FROM " + TABLE_NAME;
    }
}
