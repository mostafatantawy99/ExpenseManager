package com.danielkim.expensemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Daniel on 2/5/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "expenses.db";
    private static final int DATABASE_VERSION = 1;

    public static abstract class DBHelperItem implements BaseColumns {
        public static final String TABLE_NAME = "expenses";

        public static final String COL_DATE = "date";
        public static final String COL_AMOUNT = "amount";
        public static final String COL_CATEGORY = "category";
        public static final String COL_PAYMENT_METHOD = "payment_method";
        public static final String COL_NOTES = "notes";
    }

    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBHelperItem.TABLE_NAME + " (" +
                    DBHelperItem._ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                    DBHelperItem.COL_DATE + " TEXT " + COMMA_SEP +
                    DBHelperItem.COL_AMOUNT + " DOUBLE " + COMMA_SEP +
                    DBHelperItem.COL_CATEGORY + " TEXT " + COMMA_SEP +
                    DBHelperItem.COL_PAYMENT_METHOD + " TEXT " + COMMA_SEP +
                    DBHelperItem.COL_NOTES + " TEXT " + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBHelperItem.TABLE_NAME);
        onCreate(db);
    }
}
