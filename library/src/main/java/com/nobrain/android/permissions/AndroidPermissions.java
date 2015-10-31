package com.nobrain.android.permissions;

import android.app.Activity;
import android.os.Build;

public class AndroidPermissions {

    public static Checker check(Activity activity) {

        return new Checker(activity);
    }

    public static Result result(Activity activity) {
        return new Result();
    }

    public static boolean isOverMarshmallow() {
        // API 23 = Build.VERSION_CODES.M
        return Build.VERSION.SDK_INT >= 23;
    }

}
