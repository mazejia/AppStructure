package test.mzj.com.appstructureproject.download;

/**
 * Created by Administrator on 2018/1/30 0030.
 */

public interface DaoConstant {

    interface Download {

        String TABLE_NAME = "down_file";

        String FILE_NAME = "file_name";
        String URL = "url";
        String LENGTH = "length";
        String FINISHED = "finished";

        String tableCreate = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                "("
                + URL + " VARCHAR PRIMARY KEY, "
                + FILE_NAME + " VARCHAR, "
                + LENGTH + " INTEGER, "
                + FINISHED + " INTEGER"
                + ")";


        String tableInsert = "INSERT INTO " + TABLE_NAME
                + "("
                + URL +  ","
                + FILE_NAME  + ","
                + LENGTH + ","
                + FINISHED
                + ") VALUES (?,?,?,?)";

        String tableQuery = "select * from " + TABLE_NAME
                + " where "
                + URL
                + "=?";

        String tableUpdate = "update " + TABLE_NAME
                + " set "
                + LENGTH + " =?,"
                + FINISHED + " =?"
                + " where "
                + URL + "=?";
    }
}
