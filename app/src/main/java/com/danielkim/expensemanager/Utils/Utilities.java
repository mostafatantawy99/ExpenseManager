package com.danielkim.expensemanager.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Daniel on 2/16/2016.
 */
public final class Utilities {
    private Utilities() {
    }

    // close soft keyboard
    public static void hideKeyboard(Activity activity){
        InputMethodManager mgr = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(activity.getWindow().getCurrentFocus().getWindowToken(), 0);
    }

    public static Date convertDateToNearestMonthYear(Date d){
        Calendar c = Calendar.getInstance();
        c.setTime(d);

        c.set(Calendar.MILLISECOND, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);

        return c.getTime();
    }

    public static String convertMonthIntToLongName(int month){
        String monthString;
        switch (month) {
            case 1:  monthString = "January";       break;
            case 2:  monthString = "February";      break;
            case 3:  monthString = "March";         break;
            case 4:  monthString = "April";         break;
            case 5:  monthString = "May";           break;
            case 6:  monthString = "June";          break;
            case 7:  monthString = "July";          break;
            case 8:  monthString = "August";        break;
            case 9:  monthString = "September";     break;
            case 10: monthString = "October";       break;
            case 11: monthString = "November";      break;
            case 12: monthString = "December";      break;
            default: monthString = "Invalid month"; break;
        }
        return monthString;
    }
}
