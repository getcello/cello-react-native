package com.celloreactnative

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.cello.cello_sdk.Cello
import kotlinx.coroutines.*
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeMap

class CelloReactNativeModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }
  @ReactMethod
  fun initialize(productId: String, token: String, environment: String?, promise: Promise) {
    val activity = currentActivity ?: run {
      promise.reject("ActivityError", "Activity is null")
      return
    }

    CoroutineScope(Dispatchers.IO).launch {
      try {
        Cello.initialize(activity, productId, token, environment)
        withContext(Dispatchers.Main) {
          val client = Cello.client()
          if (client != null) {
            promise.resolve(client.configuration.toString())
          } else {
            promise.reject("InitializationError", "Failed to obtain Cello client after initialization")
          }
        }
      } catch (e: Exception) {
        promise.reject("InitializationException", "Error initializing Cello: ${e.localizedMessage}", e)
      }
    }
  }

  @ReactMethod
  fun updateToken(token: String) {
    try {
      Cello.client()?.updateToken(token)
    } catch (e: Exception) {

    }
  }

  @ReactMethod
  fun changeLanguage(language: String) {
    try {
      Cello.client()?.changeLanguage(language)
    } catch (e: Exception) {

    }
  }

  @ReactMethod
  fun showFab() {
    try {
      Cello.client()?.showFab()
    } catch (e: Exception) {

    }
  }

  @ReactMethod
  fun hideFab() {
    try {
      Cello.client()?.hideFab()
    } catch (e: Exception) {

    }
  }

  @ReactMethod
  fun openWidget() {
    try {
      Cello.client()?.openWidget()
    } catch (e: Exception) {

    }
  }

  @ReactMethod
  fun hideWidget() {
    try {
      Cello.client()?.closeWidget()
    } catch (e: Exception) {

    }
  }

  @ReactMethod
  fun shutdown() {
    try {
      Cello.client()?.shutdown()
    } catch (e: Exception) {

    }
  }

  @ReactMethod
  fun getActiveUcc(promise: Promise) {
    try {
      val ucc = Cello.client()?.getActiveUcc()
      val resultMap = WritableNativeMap()
      ucc?.forEach { (key, value) ->
        resultMap.putString(key, value)
      }
      promise.resolve(resultMap)
    } catch (e: Exception) {
      promise.reject("UCC_ERROR", e.message)
    }
  }


  companion object {
    const val NAME = "CelloReactNative"
  }
}
