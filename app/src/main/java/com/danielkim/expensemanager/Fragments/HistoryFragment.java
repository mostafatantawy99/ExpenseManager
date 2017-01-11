package com.danielkim.expensemanager.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;

import com.danielkim.expensemanager.Adapters.HistoryAdapter;
import com.danielkim.expensemanager.Databases.DBContentProvider;
import com.danielkim.expensemanager.Databases.DBHelper;
import com.danielkim.expensemanager.R;

/**
 * Created by Daniel on 4/21/2016.
 */
public class HistoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mRecyclerView;
    private HistoryAdapter adapter;

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
    private static final String SORT_BY = "t1." + DBHelper.ExpensesTable.COL_DATE + " DESC";
    private LoaderManager.LoaderCallbacks<Cursor> mCallbacks;

    public HistoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.historyRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new HistoryAdapter(this.getContext(), null);
        mRecyclerView.setAdapter(adapter);

        mCallbacks = this;
        getLoaderManager().initLoader(LOADER_ID, null, mCallbacks);

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_history, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_filter_history:
                HistoryFilterFragment fragment = new HistoryFilterFragment();
                this.getFragmentManager().beginTransaction()
                        .replace(R.id.container, fragment)
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this.getContext(), DBContentProvider.CONTENT_URI, PROJECTION, null, null, SORT_BY);
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