import Foundation
import React

@objc(CelloEventEmitter)
class CelloEventEmitter: RCTEventEmitter {

  override init() {
    super.init()
  }

  // MARK: - Properties
  private var hasListeners = false

  // MARK: - RCTEventEmitter
  override func supportedEvents() -> [String]! {
    return [
      "onTokenAboutToExpire",
      "onTokenHasExpired"
    ]
  }

  override func constantsToExport() -> [AnyHashable: Any] {
    return [
      "TOKEN_ABOUT_TO_EXPIRE": "onTokenAboutToExpire",
      "TOKEN_HAS_EXPIRED": "onTokenHasExpired"
    ]
  }

  @objc override static func requiresMainQueueSetup() -> Bool {
    return false
  }

  @objc override func startObserving() {
    hasListeners = true
    // keeping the method for backwards compatibility
  }

  @objc override func stopObserving() {
    hasListeners = false
    // keeping the method for backwards compatibility
  }
}
