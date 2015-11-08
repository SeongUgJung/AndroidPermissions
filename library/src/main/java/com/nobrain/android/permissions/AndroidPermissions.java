package com.nobrain.android.permissions;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

public class AndroidPermissions {

    /**
     * get instance of checking
     *
     * @param context context
     * @return intance
     */
    public static Checker check(Context context) {

        return new Checker(context);
    }

    /**
     * get instance of onRequestPermissionsResult
     *
     * @param activity activity
     * @return instance
     */
    @Deprecated
    public static Result result(Activity activity) {
        return result();
    }

    /**
     * get instance of onRequestPermissionsResult
     *
     * @return instance
     */
    public static Result result() {
        return new Result();
    }

    public static boolean isOverMarshmallow() {
        // API 23 = Build.VERSION_CODES.M
        return Build.VERSION.SDK_INT >= 23;
    }

}
