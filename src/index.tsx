import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'cello-react-native' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo Go\n';

const CelloReactNative = NativeModules.CelloReactNative
  ? NativeModules.CelloReactNative
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

// Keep CelloEventEmitter for backwards compatibility
const CelloEventEmitter = NativeModules.CelloEventEmitter
  ? NativeModules.CelloEventEmitter
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

function initialize(productId: string, token: string): Promise<any> {
  return CelloReactNative.initialize(productId, token);
}

function updateToken(token: string): Promise<any> {
  return CelloReactNative.updateToken(token);
}

function changeLanguage(language: string): Promise<any> {
  return CelloReactNative.changeLanguage(language);
}

function showFab() {
  return CelloReactNative.showFab();
}

function hideFab() {
  return CelloReactNative.hideFab();
}

function openWidget() {
  return CelloReactNative.openWidget();
}

function hideWidget() {
  return CelloReactNative.hideWidget();
}

function shutdown() {
  return CelloReactNative.shutdown();
}

function getActiveUcc() {
  return CelloReactNative.getActiveUcc();
}

function addListener(_event: any, _callback: any) {
  // Return a dummy listener object that does nothing
  return {
    remove: () => {},
  };
}

const Cello = {
  initialize,
  updateToken,
  changeLanguage,
  showFab,
  hideFab,
  openWidget,
  hideWidget,
  shutdown,
  getActiveUcc,
  addListener,
};

export const CelloEvents = {
  /** @deprecated */
  tokenAboutToExpire: CelloEventEmitter.TOKEN_ABOUT_TO_EXPIRE,
  /** @deprecated */
  tokenHasExpired: CelloEventEmitter.TOKEN_HAS_EXPIRED,
};

export default Cello;
