import { NativeEventEmitter, NativeModules, Platform } from 'react-native';

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

function initialize(
  productId: string,
  token: string,
  environment?: string
): Promise<any> {
  return CelloReactNative.initialize(productId, token, environment);
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

function addListener(event: any, callback: any) {
  const eventEmitter = new NativeEventEmitter(CelloEventEmitter);
  return eventEmitter.addListener(event, callback);
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
  tokenAboutToExpire: CelloEventEmitter.TOKEN_ABOUT_TO_EXPIRE,
  tokenHasExpired: CelloEventEmitter.TOKEN_HAS_EXPIRED,
};

export default Cello;
