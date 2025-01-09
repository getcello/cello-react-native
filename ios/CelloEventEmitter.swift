import Foundation
import React

@objc(CelloEventEmitter)
class CelloEventEmitter: RCTEventEmitter {

  // Add a shared instance for easier access
  static let shared = CelloEventEmitter()

  override init() {
    super.init()
    // Ensure we're initialized on the main thread
    if Thread.isMainThread {
      EventEmitter.shared = self
    } else {
      DispatchQueue.main.sync {
        EventEmitter.shared = self
      }
    }
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

    NotificationCenter.default.addObserver(
      self,
      selector: #selector(handleTokenAboutToExpire(notification:)),
      name: Notification.Name("CelloTokenAboutToExpire"),
      object: nil
    )

    NotificationCenter.default.addObserver(
      self,
      selector: #selector(handleTokenHasExpired(notification:)),
      name: Notification.Name("CelloTokenHasExpired"),
      object: nil
    )
  }

  @objc override func stopObserving() {
    hasListeners = false
    NotificationCenter.default.removeObserver(self)
  }

  // MARK: - Event Handlers
  @objc private func handleTokenAboutToExpire(notification: Notification) {
    if hasListeners {
      sendEvent(withName: "onTokenAboutToExpire", body: [:])
    }
  }

  @objc private func handleTokenHasExpired(notification: Notification) {
    if hasListeners {
      sendEvent(withName: "onTokenHasExpired", body: [:])
    }
  }

  // Add helper methods for emitting events
  @objc func emitTokenAboutToExpire() {
    if hasListeners {
      sendEvent(withName: "onTokenAboutToExpire", body: nil)
    }
  }

  @objc func emitTokenHasExpired() {
    if hasListeners {
      sendEvent(withName: "onTokenHasExpired", body: nil)
    }
  }
}

// Helper class to maintain shared instance
@objc class EventEmitter: NSObject {
  static var shared: CelloEventEmitter!
}
