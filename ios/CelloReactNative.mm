#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(CelloReactNative, NSObject)

RCT_EXTERN_METHOD(initialize:(NSString *)productId
                 withToken:(NSString *)token
                 withEnvironment:(nullable NSString *)environment
                 withProductUserDetails:(nullable NSDictionary *)productUserDetails
                 withLanguage:(nullable NSString *)language
                 withThemeMode:(nullable NSString *)themeMode
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(updateToken:(NSString *)token
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(changeLanguage:(NSString *)language
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setThemeMode:(NSString *)themeMode
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(showFab)

RCT_EXTERN_METHOD(hideFab)

RCT_EXTERN_METHOD(openWidget:(nullable NSString *)destination)

RCT_EXTERN_METHOD(hideWidget)

RCT_EXTERN_METHOD(shutdown)

RCT_EXTERN_METHOD(getActiveUcc:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getCampaignConfig:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getConfiguration:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(handleUrl:(NSString *)url
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(getPendingReferral:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(setReferralCode:(NSString *)code
                 withResolver:(RCTPromiseResolveBlock)resolve
                 withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(clearPendingReferral)

RCT_EXTERN_METHOD(checkPasteboardForReferral:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(readPasteboardForReferral:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

RCT_EXTERN_METHOD(checkInstallReferrer:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject)

+ (BOOL)requiresMainQueueSetup
{
  return NO;
}

@end
