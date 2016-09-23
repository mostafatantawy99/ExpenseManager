package com.danielkim.expensemanager.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.danielkim.expensemanager.Adapters.HistoryAdapter;
import com.danielkim.expensemanager.Databases.DBContentProvider;
import com.danielkim.expensemanager.Databases.DBHelper;
import com.danielkim.expensemanager.R;

/**
 * Created by Daniel on 4/21/2016.
 */
public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    ListView mListView;
    HistoryAdapter adapter;

    private static final int LOADER_ID = 0;
    private static final String[] PROJECTION =
            new String[]
                {
                        "t1." + DBHelper.ExpensesTable._ID,
                        "t1." + DBHelper.ExpensesTable.COL_DATE,
                        "t1." + DBHelper.ExpensesTable.COL_AMOUNT,
                        "t2." + DBHelper.CategoriesTable.COL_CATEGORY,
                        "t2." + DBHelper.CategoriesTable.COL_COLOUR,
                        "t1." + DBHelper.ExpensesTable.COL_PAYMENT_METHOD_ID,
                        "t1." + DBHelper.ExpensesTable.COL_NOTES
                };
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    public HistoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        mListView = (ListView) v.findViewById(R.id.historyListView);

        adapter = new HistoryAdapter(this.getContext(), null, 0);
        mListView.setAdapter(adapter);
        mCallbacks = this;
        getLoaderManager().initLoader(LOADER_ID, null, mCallbacks);

        return v;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this.getContext(), DBContentProvider.CONTENT_URI, PROJECTION, null, null, null);
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
        // The list view now displays the queried data.
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // For whatever reason, the Loader's data is now unavailable.
        // Remove any references to the old data by replacing it with
        // a null Cursor.
        adapter.swapCursor(null);
    }
}
