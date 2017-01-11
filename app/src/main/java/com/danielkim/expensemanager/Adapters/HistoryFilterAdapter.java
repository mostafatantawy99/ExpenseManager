package com.danielkim.expensemanager.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.danielkim.expensemanager.R;
import com.danielkim.expensemanager.Utils.Utilities;

/**
 * Created by Daniel on 11/7/2016.
 */
public class HistoryFilterAdapter extends CursorRecyclerViewAdapter<HistoryFilterAdapter.ViewHolder> {
    private static Context mContext;
    private LayoutInflater layoutInflater;

    public HistoryFilterAdapter(Context context, Cursor c) {
        super(context, c);
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mAmount;
        private TextView mDate;

        ViewHolder(View itemView) {
            super(itemView);
            mAmount = (TextView)itemView.findViewById(R.id.txt_hist_filter_amount);
            mDate = (TextView)itemView.findViewById(R.id.txt_hist_filter_month);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater
                .inflate(R.layout.li_history_filter_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, Cursor c) {
        String date = c.getString(1); // month
        String month = Utilities.convertMonthIntToLongName(Integer.parseInt(date.substring(0, 2)));
        String year = date.substring(3);
        double amount = c.getDouble(2); // amount
        holder.mAmount.setText("$" + amount);
        holder.mDate.setText(month + " " + year);
    }
}
