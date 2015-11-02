package com.nobrain.android.permissions;

import android.app.Activity;
import android.os.Build;

public class AndroidPermissions {

    /**
     * get instance of checking
     * @param activity activity
     * @return intance
     */
    public static Checker check(Activity activity) {

        return new Checker(activity);
    }

    /**
     * get instance of onRequestPermissionsResult
     * @param activity activity
     * @return instance
     */
    public static Result result(Activity activity) {
        return new Result();
    }

    public static boolean isOverMarshmallow() {
        // API 23 = Build.VERSION_CODES.M
        return Build.VERSION.SDK_INT >= 23;
    }

}
