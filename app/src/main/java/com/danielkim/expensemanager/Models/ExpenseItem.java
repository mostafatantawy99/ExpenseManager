package com.danielkim.expensemanager.Models;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import com.danielkim.expensemanager.Databases.DBHelper;

/**
 * Created by Daniel on 2/17/2016.
 */
public class ExpenseItem{
    private int id; // id of item in database
    private double amount; // amount of expense
    private long dateMillis; // date of expense in milliseconds since epoch time
    private ExpenseCategory category; // category of expense
    private String paymentMethod; // payment method of expense
    private String note; // custom note for the expense

    public ExpenseItem() {
    }

    public ExpenseItem(Parcel in){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getDateMillis() {
        return dateMillis;
    }

    public void setDateMillis(long dateMillis) {
        this.dateMillis = dateMillis;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public static final Parcelable.Creator<ExpenseItem> CREATOR = new Parcelable.Creator<ExpenseItem>() {
        public ExpenseItem createFromParcel(Parcel in) {
            return new ExpenseItem(in);
        }

        public ExpenseItem[] newArray(int size) {
            return new ExpenseItem[size];
        }
    };

    /*
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeDouble(amount);
        dest.writeLong(dateMillis);
        //dest.writeParcelable(category);
        dest.writeString(paymentMethod);
        dest.writeString(note);
    }*/

    public static ExpenseItem fromCursor(Cursor c) {
        ExpenseItem item = new ExpenseItem();

        item.setId(c.getInt(c.getColumnIndex(DBHelper.ExpensesTable._ID)));
        item.setAmount(c.getDouble(c.getColumnIndex(DBHelper.ExpensesTable.COL_AMOUNT)));
        ExpenseCategory category = new ExpenseCategory();
        category.setName(c.getString(c.getColumnIndex(DBHelper.CategoriesTable.COL_CATEGORY)));
        category.setColour(c.getString(c.getColumnIndex(DBHelper.CategoriesTable.COL_COLOUR)));
        item.setCategory(category);
        item.setNote(c.getString(c.getColumnIndex(DBHelper.ExpensesTable.COL_NOTES)));
        item.setPaymentMethod(c.getString(c.getColumnIndex(DBHelper.ExpensesTable.COL_PAYMENT_METHOD_ID)));
        item.setDateMillis(c.getLong(c.getColumnIndex(DBHelper.ExpensesTable.COL_DATE)));
        return item;
    }
}
