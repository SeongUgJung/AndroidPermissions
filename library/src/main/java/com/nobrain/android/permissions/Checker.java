package com.nobrain.android.permissions;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.HashSet;
import java.util.Set;

public class Checker {

    private Activity activity;

    private String[] permissions;
    private Action0 hasPermissions;
    private Action1 noPermissions;

    Checker(Activity activity) {
        this.activity = activity;
    }

    public Checker permissions(String... permissions) {

        this.permissions = permissions;

        return this;
    }

    public Checker hasPermissions(Action0 hasPermissions) {
        this.hasPermissions = hasPermissions;
        return this;
    }

    public Checker noPermissions(Action1 noPermissions) {
        if (AndroidPermissions.isOverMarshmallow()) {
            this.noPermissions = noPermissions;
        } else {
            this.noPermissions = null;
        }
        return this;
    }

    public void check() {

        if (permissions == null || permissions.length < 0 || activity == null) {
            return;
        }

        if (!AndroidPermissions.isOverMarshmallow()) {
            if (hasPermissions != null) {
                hasPermissions.call(permissions);
            }
            return;
        }

        int length = permissions.length;
        Set<String> noPermissionSet = new HashSet<String>(length);
        for (int idx = 0; idx < length; idx++) {
            if (ContextCompat.checkSelfPermission(activity,
                    permissions[idx]) != PackageManager.PERMISSION_GRANTED) {
                noPermissionSet.add(permissions[idx]);
            }
        }

        if (noPermissionSet.size() == 0) {
            if (hasPermissions != null) {
                hasPermissions.call(permissions);
            }
        } else {
            if (noPermissions != null) {
                noPermissions.call(permissions);
            }
        }
    }


    public interface Action0 {
        /**
         * define what if it has permission.
         *
         * @param permissions
         */
        void call(String[] permissions);
    }

    public interface Action1 {
        /**
         * @param permissions
         * @return true, if it want to consume. otherwise false if it want to request permission
         */
        void call(String[] permissions);
    }


}
