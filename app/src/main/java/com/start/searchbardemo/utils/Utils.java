package com.start.searchbardemo.utils;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Start on 16/12/2.
 */

public class Utils {

    public static float dp2px(Context context, int dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (float) (dpValue * scale + 0.5);
    }

}

