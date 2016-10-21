package live.xhf.asus.clivetelecast.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asus on 2016/10/19.
 */
public class MySqlDataBaseHelper extends SQLiteOpenHelper {

    public MySqlDataBaseHelper(Context context) {
        super(context, "1409K", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table student(_id integer primary key autoincrement,midheadimg varchar(100),name varchar(50),place varchar(50),video varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
