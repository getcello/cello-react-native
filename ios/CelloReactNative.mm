#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(CelloReactNative, NSObject)

RCT_EXTERN_METHOD(initialize:(NSString *)productId
                 withToken:(NSString *)token
                 withEnvironment:(nullable NSString *)environment
                 withProductUserDetails:(nullable NSDictionary *)productUserDetails
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(updateToken:(NSString *)token
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(changeLanguage:(NSString *)language
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(showFab)

RCT_EXTERN_METHOD(hideFab)

RCT_EXTERN_METHOD(openWidget)

RCT_EXTERN_METHOD(hideWidget)

RCT_EXTERN_METHOD(shutdown)

RCT_EXTERN_METHOD(getActiveUcc:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getCampaignConfig:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

@end
