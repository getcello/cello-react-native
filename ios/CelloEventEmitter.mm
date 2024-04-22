#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(CelloEventEmitter, NSObject)

RCT_EXTERN_METHOD(emitTokenAboutToExpire)
RCT_EXTERN_METHOD(emitTokenHasExpired)

@end
