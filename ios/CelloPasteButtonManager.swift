import CelloSDK
import UIKit

@objc(CelloPasteButtonManager)
class CelloPasteButtonManager: RCTViewManager {
  override func view() -> UIView! {
    let container = UIView()
    if #available(iOS 16.0, *) {
      // Why: React Native creates views on the main thread, which the Swift compiler cannot see through RCTViewManager.
      let control = MainActor.assumeIsolated { Cello.makePasteControl() }
      control.translatesAutoresizingMaskIntoConstraints = false
      container.addSubview(control)
      NSLayoutConstraint.activate([
        control.leadingAnchor.constraint(equalTo: container.leadingAnchor),
        control.trailingAnchor.constraint(equalTo: container.trailingAnchor),
        control.topAnchor.constraint(equalTo: container.topAnchor),
        control.bottomAnchor.constraint(equalTo: container.bottomAnchor),
      ])
    }
    return container
  }

  override static func requiresMainQueueSetup() -> Bool {
    return true
  }
}
