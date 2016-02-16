package com.danielkim.expensemanager.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.danielkim.expensemanager.DBHelper;
import com.danielkim.expensemanager.R;

import java.util.Calendar;

/**
 * Created by Daniel on 2/14/2016.
 * Add a new expense
 */
public class AddExpenseActivity extends AppCompatActivity
    implements DatePickerDialog.OnDateSetListener{
    private FloatingActionButton fabDone = null;
    DBHelper db;

    // add_expense_header
    private TextView txtExpenseInput = null; // User inputs the expense amount
    private TextView txtExpandNumPad = null; // Expand arrow icon. Visible while numpad is hidden
    private LinearLayout layoutNumPad = null; // Layout of the numpad
    private Button btnCancel = null; // exit activity without adding expense

    //add_expense_body
    private Spinner spinnerCategory = null; // Select expense category
    private Spinner spinnerPaymentMethod = null; // Select payment method
    private EditText txtNotes = null; // Add note
    private EditText txtDate = null; // Expense date. Defaults to current date. Clicking opens CalendarView

    //numpad
    private Button btnExpenseInputOkay = null; // Button to indicate that expense amount was inputted. Hides the numpad.
    private ImageButton btnBackspace = null;

    private String strExpenseTotal = ""; //stores string of user inputted expense
    private String date; //date of expense
    private Calendar calendar;

    public AddExpenseActivity() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        db = new DBHelper(this);
        calendar = calendar.getInstance();

        fabDone = (FloatingActionButton)findViewById(R.id.fab_done);
        txtExpenseInput = (TextView)findViewById(R.id.txt_expense_total);
        txtExpandNumPad = (TextView)findViewById(R.id.expand_numpad);
        btnCancel = (Button)findViewById(R.id.btn_cancel);
        layoutNumPad = (LinearLayout)findViewById(R.id.add_expense_numpad);
        btnExpenseInputOkay = (Button)findViewById(R.id.btn_okay);
        spinnerCategory = (Spinner)findViewById(R.id.spinner_category);
        spinnerPaymentMethod = (Spinner)findViewById(R.id.spinner_payment_method);
        txtNotes = (EditText)findViewById(R.id.add_expense_note);
        txtDate = (EditText)findViewById(R.id.add_expense_date);
        btnBackspace = (ImageButton)findViewById(R.id.btnBackspace);
        btnBackspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (strExpenseTotal != "") {
                    strExpenseTotal = strExpenseTotal.substring(0, strExpenseTotal.length() - 1);
                    txtExpenseInput.setText(strExpenseTotal);
                }
            }
        });

        txtExpenseInput.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (layoutNumPad.getVisibility() == View.GONE){
                    layoutNumPad.setVisibility(View.VISIBLE);
                    txtExpandNumPad.setVisibility(View.GONE);
                } else {
                    layoutNumPad.setVisibility(View.GONE);
                    txtExpandNumPad.setVisibility(View.VISIBLE);
                }
            }
        });

        txtExpandNumPad.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                layoutNumPad.setVisibility(View.VISIBLE);
                txtExpandNumPad.setVisibility(View.GONE);
            }
        });

        btnExpenseInputOkay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                layoutNumPad.setVisibility(View.GONE);
                txtExpandNumPad.setVisibility(View.VISIBLE);
            }
        });

        // close activity
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate();
            }
        });
    }

    public void onNumPadButtonClick(View v){
        Button btn = (Button)findViewById(v.getId());
        strExpenseTotal += btn.getText();

        txtExpenseInput.setText(strExpenseTotal);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = (monthOfYear + 1) + " / " + dayOfMonth + " / " + year;
        txtDate.setText(date);
    }

    // Open CalendarView to choose date
    public void chooseDate(){
        DatePickerDialog datePicker = new DatePickerDialog(this, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    // User clicks doneFab button to add expense
    public void confirmAddExpense(View v){

    }
}
