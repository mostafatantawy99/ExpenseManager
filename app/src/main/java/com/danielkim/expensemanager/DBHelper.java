package com.danielkim.expensemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Daniel on 2/5/2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "expenses.db";
    private static final int DATABASE_VERSION = 1;

    // Used to insert default values into table. Prevent recursive call to getDatabase()
    boolean isCreating = false;
    SQLiteDatabase currentDb = null;

    //Databases for tracking expenses, categories, payment methods
    public static abstract class ExpensesTable implements BaseColumns {
        public static final String TABLE_EXPENSES_NAME = "expenses";
        public static final String COL_DATE = "date";
        public static final String COL_AMOUNT = "amount";
        public static final String COL_CATEGORY_ID = "category";
        public static final String COL_PAYMENT_METHOD_ID = "payment_method";
        public static final String COL_NOTES = "notes";
    }

    public static abstract class CategoriesTable implements BaseColumns{
        public static final String TABLE_CATEGORIES_NAME = "categories";
        public static final String COL_CATEGORY = "category";
        public static final String COL_COLOUR = "colour";
        public static final String COL_DRAWABLE_NAME = "icon";
    }

    public static abstract class PaymentMethodsTable implements BaseColumns{
        public static final String TABLE_PAYMENT_METHODS_NAME = "payment_methods";
        public static final String COL_PAYMENT_METHOD = "payment_method";
        public static final String COL_COLOUR = "colour";
    }

    public static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_EXPENSES_TB =
            "CREATE TABLE " + ExpensesTable.TABLE_EXPENSES_NAME + " (" +
                    ExpensesTable._ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
                    ExpensesTable.COL_DATE + " TEXT " + COMMA_SEP +
                    ExpensesTable.COL_AMOUNT + " DOUBLE " + COMMA_SEP +
                    ExpensesTable.COL_CATEGORY_ID + " INTEGER " + COMMA_SEP +
                    ExpensesTable.COL_PAYMENT_METHOD_ID + " INTEGER " + COMMA_SEP +
                    ExpensesTable.COL_NOTES + " TEXT " + ")";

    public static final String SQL_CREATE_CATEGORIES_TB =
            "CREATE TABLE " + CategoriesTable.TABLE_CATEGORIES_NAME + " (" +
                    CategoriesTable._ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
                    CategoriesTable.COL_CATEGORY + " TEXT " + COMMA_SEP +
                    CategoriesTable.COL_COLOUR + " TEXT " + COMMA_SEP +
                    CategoriesTable.COL_DRAWABLE_NAME + " TEXT " + ")";

    public static final String SQL_CREATE_PAYMENT_METHODS_TB =
            "CREATE TABLE " + PaymentMethodsTable.TABLE_PAYMENT_METHODS_NAME + " (" +
                    PaymentMethodsTable._ID + " INTEGER PRIMARY KEY " + COMMA_SEP +
                    PaymentMethodsTable.COL_PAYMENT_METHOD + " TEXT " + COMMA_SEP +
                    PaymentMethodsTable.COL_COLOUR + " TEXT " + ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_EXPENSES_TB);
        db.execSQL(SQL_CREATE_CATEGORIES_TB);
        db.execSQL(SQL_CREATE_PAYMENT_METHODS_TB);

        currentDb = db;
        isCreating = true;
        // Add default entries into the tables
        setDefaultCategories();
        setDefaultPaymentMethods();
        currentDb = null;
        isCreating = false;
    }

    public void setDefaultCategories(){
        insertNewCategory("Food", "#8bc34a", "drawable");
        insertNewCategory("Leisure", "#f44336", "drawable");
        insertNewCategory("School", "#00bcd4", "drawable");
        insertNewCategory("Groceries", "#e91e63", "drawable");
        insertNewCategory("Transport", "#ffc107", "drawable");
        insertNewCategory("Utilities", "#3f51b5", "drawable");
        insertNewCategory("Other", "#9e9e9e", "drawable");
    }

    public void setDefaultPaymentMethods(){
        insertNewPaymentMethod("Debit", "#4caf50");
        insertNewPaymentMethod("Credit", "#f44336");
        insertNewPaymentMethod("Cash", "#ff9800");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ExpensesTable.TABLE_EXPENSES_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PaymentMethodsTable.TABLE_PAYMENT_METHODS_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_CATEGORIES_NAME);
        onCreate(db);
    }

    //insert new transaction into the Expenses table
    public long insertNewExpense(String date, double amt, int categoryId, int paymentMethodId, String notes){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ExpensesTable.COL_DATE, date);
        cv.put(ExpensesTable.COL_AMOUNT, amt);
        cv.put(ExpensesTable.COL_CATEGORY_ID, categoryId);
        cv.put(ExpensesTable.COL_PAYMENT_METHOD_ID, paymentMethodId);
        cv.put(ExpensesTable.COL_NOTES, notes);

        long rowId = db.insert(ExpensesTable.TABLE_EXPENSES_NAME, null, cv);
        return rowId;
    }

    // returns the rows inserted between date1 and date2
    public Cursor getExpenseTableDataDateDescending(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " +
                ExpensesTable.TABLE_EXPENSES_NAME + " ORDER BY " +
                ExpensesTable.COL_DATE + " DESC", null);

        return c;
    }

    public long insertNewCategory(String category, String colour, String drawable){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COL_CATEGORY, category);
        cv.put(CategoriesTable.COL_COLOUR, colour);
        cv.put(CategoriesTable.COL_DRAWABLE_NAME, drawable);

        long rowId = db.insert(CategoriesTable.TABLE_CATEGORIES_NAME, null, cv);
        return rowId;
    }

    public long insertNewPaymentMethod(String paymentMethod, String colour){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PaymentMethodsTable.COL_PAYMENT_METHOD, paymentMethod);
        cv.put(PaymentMethodsTable.COL_COLOUR, colour);

        long rowId = db.insert(PaymentMethodsTable.TABLE_PAYMENT_METHODS_NAME, null, cv);
        return rowId;
    }

    public Cursor getCategories(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " +
                CategoriesTable.TABLE_CATEGORIES_NAME, null);

        return c;
    }

    public Cursor getPaymentMethods(){
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " +
                PaymentMethodsTable.TABLE_PAYMENT_METHODS_NAME, null);

        return c;
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        if(isCreating && currentDb != null){
            return currentDb;
        }
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        if(isCreating && currentDb != null){
            return currentDb;
        }
        return super.getReadableDatabase();
    }
}
