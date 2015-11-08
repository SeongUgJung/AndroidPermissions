package com.nobrain.android.permissions;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.HashSet;
import java.util.Set;

public class Checker {

    private Context context;

    private String[] permissions;
    private Action0 hasPermissions;
    private Action1 noPermissions;

    Checker(Context context) {
        this.context = context;
    }

    /**
     * Add permissions for Checking
     *
     * @param permissions ex) {@code Manifest.permission.CALL_PHONE}
     * @return {@link Checker}
     */
    public Checker permissions(String... permissions) {

        this.permissions = permissions;

        return this;
    }

    /**
     * execute Action, if all permissions have granted
     *
     * @param hasPermissions action, if it granted all
     * @return {@link Checker}
     */
    public Checker hasPermissions(Action0 hasPermissions) {
        this.hasPermissions = hasPermissions;
        return this;
    }

    /**
     * execute Action, if some permissions have denied
     *
     * @param noPermissions action, if it denied some permissions
     * @return {@link Checker}
     */
    public Checker noPermissions(Action1 noPermissions) {
        if (AndroidPermissions.isOverMarshmallow()) {
            this.noPermissions = noPermissions;
        } else {
            this.noPermissions = null;
        }
        return this;
    }

    /**
     * execute checking
     */
    public void check() {

        if (permissions == null || permissions.length < 0 || context == null) {
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
            if (context.checkSelfPermission(permissions[idx]) != PackageManager.PERMISSION_GRANTED) {
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


    /**
     * action of granted all
     */
    public interface Action0 {
        /**
         * define what if it has permission.
         *
         * @param permissions granted permissions
         */
        void call(String[] permissions);
    }

    /**
     * action of denied some permission
     */
    public interface Action1 {
        /**
         * define what if it has denied permission
         * @param permissions denied permissions
         */
        void call(String[] permissions);
    }


}
