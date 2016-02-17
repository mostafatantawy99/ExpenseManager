package com.danielkim.expensemanager;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

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
}
