
package com.reactintent;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationManagerCompat;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RNLetoteIntentModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;

    public RNLetoteIntentModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNLetoteIntent";
    }

    @ReactMethod
    public void gotoPermissionSetting() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String packageName = reactContext.getPackageName();
        OSUtils.ROM romType = OSUtils.getRomType();
        switch (romType) {
            case Flyme: // 魅族
                intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.putExtra("packageName", packageName);
                break;
            default:
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", reactContext.getPackageName(), null);
                intent.setData(uri);
                break;
        }
        try {
            reactContext.startActivity(intent);
        } catch (Exception e) {
            Intent it = new Intent();
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            it.setAction(Settings.ACTION_SETTINGS);
            reactContext.startActivity(it);
        }
    }

    /**
     * 获取 MIUI 版本号
     */
    private static String getMiuiVersion() {
        String propName = "ro.miui.ui.version.name";
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(
                    new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return line;
    }

    /**
     * 跳转:「应用详情」界面
     *
     * @param packageName 应用包名
     */
    public static void gotoAppDetailSetting(String packageName, ReactApplicationContext context) {
        context.startActivity(getAppDetailsSettingsIntent(packageName));
    }


    /**
     * 获取跳转「应用详情」的意图
     *
     * @param packageName 应用包名
     * @return 意图
     */
    public static Intent getAppDetailsSettingsIntent(String packageName) {
        return new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + packageName))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @ReactMethod
    public void openActivity(String name) {
        Intent intent = new Intent(name);
        reactContext.startActivity(intent);
    }

    @ReactMethod
    public void isAllowReceiveNotification(Promise promise) {
        promise.resolve(NotificationManagerCompat.from(this.reactContext).areNotificationsEnabled());
    }

    @ReactMethod
    public void gotoNotifycation() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, reactContext.getPackageName());
        reactContext.startActivity(intent);

    }

}