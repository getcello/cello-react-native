package com.celloreactnative

import com.cello.cello_sdk.Cello
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap


class CelloEventEmitterModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }

  companion object {
    const val NAME = "CelloEventEmitter"
    private const val TOKEN_ABOUT_TO_EXPIRE = "onTokenAboutToExpire"
    private const val TOKEN_HAS_EXPIRED = "onTokenHasExpired"
  }

  override fun getConstants(): Map<String, Any>? {
    val constants = HashMap<String, Any>()
    constants["TOKEN_ABOUT_TO_EXPIRE"] = TOKEN_ABOUT_TO_EXPIRE
    constants["TOKEN_HAS_EXPIRED"] = TOKEN_HAS_EXPIRED
    return constants
  }

  init {
    setupCelloListeners()
  }

  @ReactMethod
  fun addListener(eventName: String) {
    // Keep: Required for RN built in Event Emitter Calls.
  }

  @ReactMethod
  fun removeListeners(count: Int) {
    // Keep: Required for RN built in Event Emitter Calls.
  }

  private fun setupCelloListeners() {
    Cello.client()?.addTokenAboutToExpireListener {
      sendEvent(TOKEN_ABOUT_TO_EXPIRE, Arguments.createMap())
    }

    Cello.client()?.addTokenExpiredListener {
      sendEvent(TOKEN_HAS_EXPIRED, Arguments.createMap())
    }
  }

  private fun sendEvent(eventName: String, params: WritableMap?) {

  }
}
