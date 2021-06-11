
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

#import <React/RCTEventEmitter.h>

@interface RNLetoteIntent : RCTEventEmitter <RCTBridgeModule>

+ (void)didReceiveSensorsDeeplinkParam:(NSString *)param;

@end
  
