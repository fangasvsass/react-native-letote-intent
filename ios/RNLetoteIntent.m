#import "RNLetoteIntent.h"
#import <UIKit/UIKit.h>
#import <UserNotifications/UserNotifications.h>

#define EXCEED_IOS_10 ([[[UIDevice currentDevice] systemVersion] floatValue]>= 10.0 ? YES : NO)

@implementation RNLetoteIntent

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(gotoPermissionSetting) {
//    NSURL * url= [NSURL URLWithString:@"prefs:root=LOCATION_SERVICES"];
    NSURL * url = [NSURL URLWithString:UIApplicationOpenSettingsURLString];
    if (EXCEED_IOS_10) {
        if ([[UIApplication sharedApplication] canOpenURL:url]) {
            [[UIApplication sharedApplication]openURL:url options:@{} completionHandler:^(BOOL success) {
            }];
        }
    }else{
        if ([[UIApplication sharedApplication] canOpenURL:url]) {
            [[UIApplication sharedApplication] openURL:url];
        }
    }
}
RCT_REMAP_METHOD(isAllowReceiveNotification,findEventsWithResolver:(RCTPromiseResolveBlock)resolve
                 rejecter:(RCTPromiseRejectBlock)reject)
{
    if ([[[UIDevice currentDevice] systemVersion] doubleValue] >=10.0) { //iOS10以上包含iOS10
        UNUserNotificationCenter* center = [UNUserNotificationCenter currentNotificationCenter];
        [center getNotificationSettingsWithCompletionHandler:^(UNNotificationSettings * _Nonnull settings) {
            if (settings.authorizationStatus == UNAuthorizationStatusNotDetermined){
                //未选择;
                resolve([[NSArray alloc] initWithObjects:@(0), nil]);
            }else if (settings.authorizationStatus == UNAuthorizationStatusDenied){
                //未授权;
                resolve([[NSArray alloc] initWithObjects:@(0), nil]);
            }else if (settings.authorizationStatus == UNAuthorizationStatusAuthorized){
                //已授权;
                resolve([[NSArray alloc] initWithObjects:@(1), nil]);
            }
        }];
    }else if([[[UIDevice currentDevice] systemVersion] doubleValue] >=8.0 && [[[UIDevice currentDevice] systemVersion] doubleValue] <10.0){ // ios8-ios10
        if ([[UIApplication sharedApplication] currentUserNotificationSettings].types  == UIRemoteNotificationTypeNone) {
            resolve([[NSArray alloc] initWithObjects:@(0), nil]);
        }else{
            resolve([[NSArray alloc] initWithObjects:@(1), nil]);
        }
    }
}
/*
 About — prefs:root=General&path=About
 Accessibility — prefs:root=General&path=ACCESSIBILITY
 AirplaneModeOn— prefs:root=AIRPLANE_MODE
 Auto-Lock — prefs:root=General&path=AUTOLOCK
 Brightness — prefs:root=Brightness
 Bluetooth — prefs:root=General&path=Bluetooth
 Date& Time — prefs:root=General&path=DATE_AND_TIME
 FaceTime — prefs:root=FACETIME
 General— prefs:root=General
 Keyboard — prefs:root=General&path=Keyboard
 iCloud — prefs:root=CASTLE  iCloud
 Storage & Backup — prefs:root=CASTLE&path=STORAGE_AND_BACKUP
 International — prefs:root=General&path=INTERNATIONAL
 Location Services — prefs:root=LOCATION_SERVICES
 Music — prefs:root=MUSIC
 Music Equalizer — prefs:root=MUSIC&path=EQ
 Music VolumeLimit— prefs:root=MUSIC&path=VolumeLimit
 Network — prefs:root=General&path=Network
 Nike + iPod — prefs:root=NIKE_PLUS_IPOD
 Notes — prefs:root=NOTES
 Notification — prefs:root=NOTIFICATIONS_ID
 Phone — prefs:root=Phone
 Photos — prefs:root=Photos
 Profile — prefs:root=General&path=ManagedConfigurationList
 Reset — prefs:root=General&path=Reset
 Safari — prefs:root=Safari  Siri — prefs:root=General&path=Assistant
 Sounds — prefs:root=Sounds
 SoftwareUpdate— prefs:root=General&path=SOFTWARE_UPDATE_LINK
 Store — prefs:root=STORE
 Twitter — prefs:root=TWITTER
 Usage — prefs:root=General&path=USAGE
 VPN — prefs:root=General&path=Network/VPN
 Wallpaper — prefs:root=Wallpaper
 Wi-Fi — prefs:root=WIFI
 */
@end
