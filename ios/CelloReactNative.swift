import CelloSDK

@objc(CelloReactNative)
class CelloReactNative: NSObject {

  @objc(initialize:withToken:withEnvironment:withResolver:withRejecter:)
  func initialize(productId: String, token: String, environment: String?, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {
    let resolver = resolve
    let rejecter = reject

    Cello.initialize(for: productId, with: token, environment: environment) { result in
      switch result {
        case .success(let configuration):
          resolver(configuration)
        case .failure(let error):
          rejecter("InitializationError", "Failed to initialize Cello: \(error.localizedDescription)", error)
      }
    }
  }

  @objc(updateToken:withResolver:withRejecter:)
  func updateToken(token: String, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
    if #available(iOS 14.0, *) {
      Cello.updateToken(token: token) { result in
        switch result {
          case .success(let res):
            resolve(res)
          case .failure(let error):
            reject("TokenUpdateError", "Failed to update token: \(error.localizedDescription)", error)
        }
      }
    } else {
      reject("UnavailableError", "Token update feature is not available in your iOS version.", nil)
    }
  }

  @objc(changeLanguage:withResolver:withRejecter:)
  func changeLanguage(language: String, resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) {
    if #available(iOS 14.0, *) {
      Cello.changeLanguage(to: language) { result in
        switch result {
          case .success(let res):
            resolve(res)
          case .failure(let error):
            reject("LanguageChangeError", "Failed to change language: \(error.localizedDescription)", error)
        }
      }
    } else {
      reject("UnavailableError", "Language change feature is not available in your iOS version.", nil)
    }
  }

  @objc(showFab)
  func showFab() -> Void {
    Cello.showFab()
  }

  @objc(hideFab)
  func hideFab() -> Void {
    Cello.hideFab()
  }

  @objc(openWidget)
  func openWidget() -> Void {
    Cello.openWidget()
  }

  @objc(hideWidget)
  func hideWidget() -> Void {
    Cello.hideWidget()
  }

  @objc(shutdown)
  func shutdown() -> Void {
    Cello.shutdown()
  }

  @objc(getActiveUcc:withRejecter:)
  func getActiveUcc(_ resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {
    if let result = Cello.getActiveUcc() {
      resolve(result)
    } else {
      reject("NO_ACTIVE_UCC", "No active UCC found", nil)
    }
  }

  @objc(getCampaignConfig:withRejecter:)
  func getCampaignConfig(_ resolve: @escaping RCTPromiseResolveBlock, reject: @escaping RCTPromiseRejectBlock) -> Void {
    if let result = Cello.getCampaignConfig() {
      resolve(result)
    } else {
      resolve(nil)
    }
  }
}
