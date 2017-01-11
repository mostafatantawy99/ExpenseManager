package com.danielkim.expensemanager.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.danielkim.expensemanager.Adapters.HistoryAdapter;
import com.danielkim.expensemanager.Adapters.HistoryFilterAdapter;
import com.danielkim.expensemanager.Databases.DBContentProvider;
import com.danielkim.expensemanager.Databases.DBHelper;
import com.danielkim.expensemanager.Models.ExpenseItem;
import com.danielkim.expensemanager.R;
import com.danielkim.expensemanager.Utils.Utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Daniel on 11/7/2016.
 */
public class HistoryFilterFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final int LOADER_ID = 0;
    private RecyclerView mRecyclerView;
    //private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;
    private HistoryFilterAdapter adapter;
    private HashMap<Date, Integer> items;

    private static final String[] PRO2JECTION =
        new String[]
                {
                    DBHelper.ExpensesTable._ID,
                    DBHelper.ExpensesTable.COL_DATE,
                    DBHelper.ExpensesTable.COL_AMOUNT,
                };

    private static final String[] PROJECTION =
            new String[]
                    {
                            DBHelper.ExpensesTable._ID,
                            "strftime('%m %Y'," + DBHelper.ExpensesTable.COL_DATE + "/1000,'unixepoch')",
                            "sum(" + DBHelper.ExpensesTable.COL_AMOUNT + ")",
                    };

    public HistoryFilterFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_filter, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.historyFilterRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new HistoryFilterAdapter(this.getContext(), null);
        mRecyclerView.setAdapter(adapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
/*
        DBHelper db = new DBHelper(this.getContext());
        Cursor c = db.getExpenses();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            Date d = Utilities.convertDateToNearestMonthYear(new Date(c.getColumnIndex(DBHelper.ExpensesTable.COL_DATE)));
            Integer amount = c.getColumnIndex(DBHelper.ExpensesTable.COL_AMOUNT);
            Integer prev = items.get(d);

            if (prev != null){
                amount += prev;
            }

            items.put(d, amount);
        }*/



        //mCallbacks = this;
        //getLoaderManager().initLoader(LOADER_ID, null, mCallbacks);
        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this.getContext(), DBContentProvider.CONTENT_URI_FILTER, PROJECTION, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case LOADER_ID:
                // The asynchronous load is complete and the data
                // is now available for use. Only now can we associate
                // the queried Cursor with the Adapter.
                adapter.swapCursor(cursor);
                break;
            default:
                return;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
    // grid of buttons, choose Year/Month/Quarter etc.
    // button loads up recyclerview with that daterange's expenses.

}
