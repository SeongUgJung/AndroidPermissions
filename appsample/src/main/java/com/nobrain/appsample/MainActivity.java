package com.nobrain.appsample;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.nobrain.android.permissions.AndroidPermissions;
import com.nobrain.android.permissions.Checker;
import com.nobrain.android.permissions.Result;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 102;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpActionbar();

        AndroidPermissions.check(this)
                .permissions(Manifest.permission.CALL_PHONE)
                .hasPermissions(new Checker.Action0() {
                    @Override
                    public void call(String[] permissions) {
                        String msg = "Permission has " + permissions[0];
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .noPermissions(new Checker.Action1() {
                    @Override
                    public void call(String[] permissions) {
                        String msg = "Permission has no " + permissions[0];
                        Log.d(TAG, msg);
                        Toast.makeText(MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT).show();

                        ActivityCompat.requestPermissions(MainActivity.this
                                , new String[]{Manifest.permission.CALL_PHONE}
                                , REQUEST_CODE);
                    }
                })
                .check();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, final String[] permissions, int[] grantResults) {
        AndroidPermissions.result(MainActivity.this)
                .addPermissions(REQUEST_CODE, Manifest.permission.CALL_PHONE)
                .putActions(REQUEST_CODE, new Result.Action0() {
                    @Override
                    public void call() {
                        String msg = "Request Success : " + permissions[0];
                        Toast.makeText(MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT).show();

                    }
                }, new Result.Action1() {
                    @Override
                    public void call(String[] hasPermissions, String[] noPermissions) {
                        String msg = "Request Fail : " + noPermissions[0];
                        Toast.makeText(MainActivity.this,
                                msg,
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .result(requestCode, permissions, grantResults);
    }

    private void setUpActionbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}
