package com.houseinspect.util;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.SearchView;

/**
 * Created by Lalit on 7/5/2016.
 */
public class KeyboardUtil {

    public static void setupKeyboardUI(View view, final Activity activity) {
        try {
            if(!(view instanceof EditText)) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        hideSoftKeyboard(activity);
                        return false;
                    }
                });
            }
            if(!(view instanceof SearchView)) {
                view.setOnTouchListener(new View.OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        hideSoftKeyboard(activity);
                        return false;
                    }
                });
            }
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                    View innerView = ((ViewGroup) view).getChildAt(i);
                    setupKeyboardUI(innerView, activity);
                }
            }
        }catch (Exception e){

        }

    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
        }
    }
}
