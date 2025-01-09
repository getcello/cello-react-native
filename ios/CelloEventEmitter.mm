#import <React/RCTBridgeModule.h>
#import <React/RCTEventEmitter.h>

@interface RCT_EXTERN_MODULE(CelloEventEmitter, RCTEventEmitter)

RCT_EXTERN_METHOD(supportedEvents)
RCT_EXTERN_METHOD(emitTokenAboutToExpire)
RCT_EXTERN_METHOD(emitTokenHasExpired)

@end
