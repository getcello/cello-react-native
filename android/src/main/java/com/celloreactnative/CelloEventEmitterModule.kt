package com.celloreactnative

import com.cello.cello_sdk.Cello
import com.cello.cello_sdk.managers.CelloWidgetListener
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.modules.core.DeviceEventManagerModule


@ReactModule(name = CelloEventEmitterModule.NAME)
class CelloEventEmitterModule(private val reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }

  companion object {
    const val NAME = "CelloEventEmitter"
    private const val TOKEN_ABOUT_TO_EXPIRE = "onTokenAboutToExpire"
    private const val TOKEN_HAS_EXPIRED = "onTokenHasExpired"
    private const val WIDGET_OPENED = "onWidgetOpened"
    private const val WIDGET_CLOSED = "onWidgetClosed"
  }

  override fun getConstants(): Map<String, Any>? {
    val constants = HashMap<String, Any>()
    constants["TOKEN_ABOUT_TO_EXPIRE"] = TOKEN_ABOUT_TO_EXPIRE
    constants["TOKEN_HAS_EXPIRED"] = TOKEN_HAS_EXPIRED
    constants["WIDGET_OPENED"] = WIDGET_OPENED
    constants["WIDGET_CLOSED"] = WIDGET_CLOSED
    return constants
  }

  private var tokenListenersRegistered = false

  private val widgetListener = object : CelloWidgetListener {
    override fun onWidgetOpened() {
      sendEvent(WIDGET_OPENED, Arguments.createMap())
    }

    override fun onWidgetClosed() {
      sendEvent(WIDGET_CLOSED, Arguments.createMap())
    }
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

  override fun invalidate() {
    Cello.removeWidgetListener(widgetListener)
    super.invalidate()
  }

  private fun setupCelloListeners() {
    Cello.addWidgetListener(widgetListener)
    registerTokenListeners()
  }

  fun registerTokenListeners() {
    if (tokenListenersRegistered) return
    val client = Cello.client() ?: return

    client.addTokenAboutToExpireListener {
      sendEvent(TOKEN_ABOUT_TO_EXPIRE, Arguments.createMap())
    }

    client.addTokenExpiredListener {
      sendEvent(TOKEN_HAS_EXPIRED, Arguments.createMap())
    }

    tokenListenersRegistered = true
  }

  private fun sendEvent(eventName: String, params: WritableMap?) {
    reactContext
      .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
      .emit(eventName, params)
  }
}
