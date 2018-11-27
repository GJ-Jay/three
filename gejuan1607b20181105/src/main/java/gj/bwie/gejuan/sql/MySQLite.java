package gj.bwie.gejuan.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLite extends SQLiteOpenHelper {
    public MySQLite(Context context) {
        super(context, "news.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建表
        db.execSQL("create table new_table(id integer primary key autoincrement," +
                "title varchar(50)," +
                "summary varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
