package com.danielkim.expensemanager.Models;

import android.database.Cursor;
import com.danielkim.expensemanager.Databases.DBHelper;

/**
 * Created by Daniel 657-454-7555 on 9/21/2016.
 */
public class ExpenseCategory {
    private int id;
    private String name;
    private String colour;

    public ExpenseCategory(){
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getId() {return id;}

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    public static ExpenseCategory fromCursor(Cursor c) {
        ExpenseCategory category = new ExpenseCategory();
        category.setId(c.getInt(c.getColumnIndex(DBHelper.CategoriesTable._ID)));
        category.setName(c.getString(c.getColumnIndex(DBHelper.CategoriesTable.COL_CATEGORY)));
        category.setColour(c.getString(c.getColumnIndex(DBHelper.CategoriesTable.COL_COLOUR)));
        return category;
    }
}
