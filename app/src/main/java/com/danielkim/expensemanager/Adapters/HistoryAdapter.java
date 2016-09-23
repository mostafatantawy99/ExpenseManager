package com.danielkim.expensemanager.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.danielkim.expensemanager.ExpenseItem;
import com.danielkim.expensemanager.R;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Daniel on 2/20/2016.
 */

public class HistoryAdapter extends CursorAdapter {
    ExpenseItem item;
    static Context mContext;
    LayoutInflater layoutInflater;

    private TextView categoryTxt;
    private TextView amountTxt;
    private TextView dateTxt;
    private TextView paymentMethodTxt;
    private ImageView circle;

    public HistoryAdapter(Context context, Cursor c, int flags) {
        super(context, c, 0);
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return layoutInflater.inflate(R.layout.li_history_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        item = ExpenseItem.fromCursor(cursor);

        categoryTxt = (TextView)view.findViewById(R.id.txt_hist_category);
        amountTxt = (TextView)view.findViewById(R.id.txt_hist_amount);
        dateTxt = (TextView)view.findViewById(R.id.txt_hist_date);
        paymentMethodTxt = (TextView)view.findViewById(R.id.txt_hist_payment_method);
        circle = (ImageView)view.findViewById(R.id.circle_hist);

        String note = item.getNote();
        categoryTxt.setText(note.isEmpty() ? item.getCategory().getName() : note);
        DecimalFormat df = new DecimalFormat("#.00");
        amountTxt.setText("$" + df.format(item.getAmount()));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.CANADA);
        dateTxt.setText(sdf.format(item.getDateMillis()));
        paymentMethodTxt.setText(item.getPaymentMethod());
        circle.setColorFilter(Color.parseColor(item.getCategory().getColour()));
    }
}
