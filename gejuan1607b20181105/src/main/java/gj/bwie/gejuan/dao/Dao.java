package gj.bwie.gejuan.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import gj.bwie.gejuan.sql.MySQLite;

public class Dao {
    Context context;
    private final MySQLite mySQLite;
    private final SQLiteDatabase db;

    public Dao(Context context) {
        //调用数据库对象
        mySQLite = new MySQLite(context);
        db = mySQLite.getWritableDatabase();
    }

    //增
    public long insert(String table, String nullColumnHack, ContentValues values) {
        return db.insert(table, nullColumnHack, values);
    }

    //删
    public long delete(String table, String whereClause, String[] whereArgs) {
        return db.delete(table, whereClause, whereArgs);
    }

    //查
    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having,
                        String orderBy) {
        return db.query(table, null, null, null, null, null, null);
    }
}
