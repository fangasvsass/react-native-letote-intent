
package com.reactintent;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

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
  public boolean gotoPermissionSetting() {
    boolean success = true;
    Intent intent = new Intent();
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    String packageName = reactContext.getPackageName();

    OSUtils.ROM romType = OSUtils.getRomType();
    switch (romType) {
      case EMUI: // 华为
        intent.putExtra("packageName", packageName);
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
        break;
      case Flyme: // 魅族
        intent.setAction("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", packageName);
        break;
      case MIUI: // 小米
        String rom = getMiuiVersion();
        if ("V6".equals(rom) || "V7".equals(rom)) {
          intent.setAction("miui.intent.action.APP_PERM_EDITOR");
          intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
          intent.putExtra("extra_pkgname", packageName);
        } else if ("V8".equals(rom) || "V9".equals(rom)) {
          intent.setAction("miui.intent.action.APP_PERM_EDITOR");
          intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
          intent.putExtra("extra_pkgname", packageName);
        } else {
          intent = getAppDetailsSettingsIntent(packageName);
        }
        break;
      case Sony: // 索尼
        intent.putExtra("packageName", packageName);
        intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
        break;
      case ColorOS: // OPPO
        intent.putExtra("packageName", packageName);
        intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.PermissionManagerActivity"));
        break;
      case EUI: // 乐视
        intent.putExtra("packageName", packageName);
        intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
        break;
      case LG: // LG
        intent.setAction("android.intent.action.MAIN");
        intent.putExtra("packageName", packageName);
        ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity");
        intent.setComponent(comp);
        break;
      case SamSung: // 三星
      case SmartisanOS: // 锤子
        gotoAppDetailSetting(packageName,reactContext);
        break;
      default:
        intent.setAction(Settings.ACTION_SETTINGS);
        success = false;
        break;
    }
    try {
      reactContext.startActivity(intent);
    } catch (Exception e) {
      e.printStackTrace();
      // 跳转失败, 前往普通设置界面
      success = false;
    }
    return success;
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

}