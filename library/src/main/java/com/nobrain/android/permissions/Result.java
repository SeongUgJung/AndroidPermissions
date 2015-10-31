package com.nobrain.android.permissions;

import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;

import java.util.HashSet;
import java.util.Set;

public class Result {

    // first = hasPermissions, second = noPermissions
    private SparseArray<Pair<Action0, Action1>> actions;
    private SparseArray<String[]> permissions;

    Result() {
        actions = new SparseArray<Pair<Action0, Action1>>();
        permissions = new SparseArray<String[]>();
    }

    public Result addPermissions(int requestCode, String... permissions) {

        this.permissions.append(requestCode, permissions);

        return this;
    }

    public Result putActions(int requestCode, Action0 hasPermissions, Action1 noPermissions) {
        this.actions.put(requestCode, new Pair<Action0, Action1>(hasPermissions, noPermissions));
        return this;
    }


    public Result putActions(int requestCode, Action0 hasPermissions) {
        this.actions.put(requestCode, new Pair<Action0, Action1>(hasPermissions, null));
        return this;
    }

    public void result(int requestCode, String[] permissions, int[] grantResults) {


        String[] servedPermissions = this.permissions.get(requestCode);
        Pair<Action0, Action1> servedAction = actions.get(requestCode);
        if (servedPermissions == null) {
            if (servedAction != null && servedAction.second != null) {
                servedAction.second.call(new String[0], permissions);
            }

            return;
        } else if (servedAction == null) {
            return;
        }

        Set<String> noPermissionsSet = new HashSet<String>(permissions.length);
        Set<String> hasPermissionsSet = new HashSet<String>(permissions.length);

        String servedPermission;
        for (int idx = 0, size = servedPermissions.length; idx < size; idx++) {
            servedPermission = servedPermissions[idx];
            int permissionIdx = findResultPermission(permissions, servedPermission);
            if (permissionIdx != -1) {
                if (grantResults[permissionIdx] != PackageManager.PERMISSION_GRANTED) {
                    noPermissionsSet.add(permissions[permissionIdx]);
                } else {
                    hasPermissionsSet.add(permissions[permissionIdx]);
                }
            } else {
                noPermissionsSet.add(servedPermission);
            }
        }

        if (noPermissionsSet.size() == 0) {
            servedAction.first.call();
        } else {
            String[] hasPermArray = new String[hasPermissionsSet.size()];
            String[] noPermArray = new String[noPermissionsSet.size()];
            servedAction.second.call(hasPermissionsSet.toArray(hasPermArray),
                    noPermissionsSet.toArray(noPermArray));
        }

    }

    private int findResultPermission(String[] permissions, String servedPermission) {
        for (int idx = 0, size = permissions.length; idx < size; idx++) {
            if (TextUtils.equals(permissions[idx], servedPermission)) {
                return idx;
            }
        }

        return -1;
    }


    public interface Action0 {
        void call();
    }

    public interface Action1 {
        void call(String[] hasPermissions, String[] noPermissions);
    }
}
