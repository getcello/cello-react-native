package com.celloreactnative

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap
import com.cello.cello_sdk.Cello
import com.cello.cello_sdk.ProductUserDetails
import kotlinx.coroutines.*
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeMap

class CelloReactNativeModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }
  @ReactMethod
  fun initialize(productId: String, token: String, environment: String?, productUserDetailsMap: ReadableMap?, promise: Promise) {
    val activity = reactApplicationContext.currentActivity ?: run {
      promise.reject("ActivityError", "Activity is null")
      return
    }

    CoroutineScope(Dispatchers.IO).launch {
      try {
        val productUserDetails = try {
          productUserDetailsMap?.let { map ->
            ProductUserDetails(
              firstName = if (map.hasKey("firstName")) map.getString("firstName") else null,
              lastName = if (map.hasKey("lastName")) map.getString("lastName") else null,
              fullName = if (map.hasKey("fullName")) map.getString("fullName") else null,
              email = if (map.hasKey("email")) map.getString("email") else null
            )
          }
        } catch (e: Exception) {
          android.util.Log.w("CelloReactNative", "Failed to parse productUserDetails: ${e.message}")
          null
        }

        Cello.initialize(activity, productId, token, environment, productUserDetails)
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

  @ReactMethod
  fun getCampaignConfig(promise: Promise) {
    try {
      val campaignConfig = Cello.client()?.getCampaignConfig()
      val resultMap = WritableNativeMap()
      campaignConfig?.forEach { (key, value) ->
        when (value) {
          is String -> resultMap.putString(key, value)
          is Double -> resultMap.putDouble(key, value)
          is Int -> resultMap.putInt(key, value)
          is Long -> resultMap.putDouble(key, value.toDouble())
          is Float -> resultMap.putDouble(key, value.toDouble())
          is Boolean -> resultMap.putBoolean(key, value)
          null -> resultMap.putNull(key)
        }
      }
      promise.resolve(resultMap)
    } catch (e: Exception) {
      promise.reject("CAMPAIGN_CONFIG_ERROR", e.message)
    }
  }


  companion object {
    const val NAME = "CelloReactNative"
  }
}
