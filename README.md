# Android Permissions Checker

Android M was added to check Permission.
but Permission check processing is so dirty.

This Project is to be simple, Checking permissions.

# Install

I'm ready to deploy. I need more time. sorry.
  
# How to use

* To check permissions

```java

AndroidPermissions.check(this)
    .permissions(Manifest.permission.CALL_PHONE)
    .hasPermissions(new Checker.Action0() {
        @Override
        public void call(String[] permissions) {
            // do something..
        }
    })
    .noPermissions(new Checker.Action1() {
        @Override
        public void call(String[] permissions) {
            // do something..
        }
    })
    .check();


```

* To get Result

```java

AndroidPermissions.result(MainActivity.this)
    .addPermissions(REQUEST_CODE, Manifest.permission.CALL_PHONE)
    .putActions(REQUEST_CODE, new Result.Action0() {
        @Override
        public void call() {
            // do something..
        }
    }, new Result.Action1() {
        @Override
        public void call(String[] hasPermissions, String[] noPermissions) {
            // do something..
        }
    })
    .result(requestCode, permissions, grantResults);

```

## Why Interface's name is "Action0, Action1"

It is not good naming. but I wish you to use Lambda Expression. [Gradle-RetroLambda](https://github.com/evant/gradle-retrolambda)
