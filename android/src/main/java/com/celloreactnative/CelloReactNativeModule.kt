package com.celloreactnative

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReadableMap
import com.cello.cello_sdk.Cello
import com.cello.cello_sdk.CelloInitializationResult
import com.cello.cello_sdk.ProductUserDetails
import kotlinx.coroutines.*
import com.facebook.react.bridge.WritableArray
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.WritableNativeArray
import com.facebook.react.bridge.WritableNativeMap
import org.json.JSONArray
import org.json.JSONObject

class CelloReactNativeModule(reactContext: ReactApplicationContext) :
  ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String {
    return NAME
  }
  @ReactMethod
  fun initialize(productId: String, token: String, environment: String?, productUserDetailsMap: ReadableMap?, language: String?, themeMode: String?, promise: Promise) {
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

        Cello.initialize(
          activity,
          productId,
          token,
          environment,
          productUserDetails,
          language,
          themeMode
        ) { result ->
          when (result) {
            is CelloInitializationResult.Success ->
              promise.resolve(result.configuration.toWritableMap())

            is CelloInitializationResult.Failure ->
              promise.reject(
                "InitializationError",
                result.error.localizedMessage ?: "Cello initialization failed",
                result.error
              )
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
  fun setThemeMode(themeMode: String) {
    try {
      Cello.client()?.setThemeMode(themeMode)
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
  fun openWidget(destination: String?) {
    try {
      Cello.client()?.openWidget(destination = destination)
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

  @ReactMethod
  fun getConfiguration(promise: Promise) {
    try {
      val configuration = Cello.client()?.getConfiguration()
      promise.resolve(configuration?.toMap()?.toWritableMap())
    } catch (e: Exception) {
      promise.reject("CONFIGURATION_ERROR", e.message)
    }
  }

  private fun Map<*, *>.toWritableMap(): WritableMap {
    val map = WritableNativeMap()

    forEach { (key, value) ->
      val name = key as? String ?: return@forEach

      when (value) {
        null, JSONObject.NULL -> map.putNull(name)
        is String -> map.putString(name, value)
        is Boolean -> map.putBoolean(name, value)
        is Int -> map.putInt(name, value)
        is Number -> map.putDouble(name, value.toDouble())
        is Map<*, *> -> map.putMap(name, value.toWritableMap())
        is List<*> -> map.putArray(name, value.toWritableArray())
        is JSONObject -> map.putMap(name, value.toWritableMap())
        is JSONArray -> map.putArray(name, value.toWritableArray())
        else -> map.putString(name, value.toString())
      }
    }

    return map
  }

  private fun List<*>.toWritableArray(): WritableArray {
    val array = WritableNativeArray()

    forEach { value ->
      when (value) {
        null, JSONObject.NULL -> array.pushNull()
        is String -> array.pushString(value)
        is Boolean -> array.pushBoolean(value)
        is Int -> array.pushInt(value)
        is Number -> array.pushDouble(value.toDouble())
        is Map<*, *> -> array.pushMap(value.toWritableMap())
        is List<*> -> array.pushArray(value.toWritableArray())
        is JSONObject -> array.pushMap(value.toWritableMap())
        is JSONArray -> array.pushArray(value.toWritableArray())
        else -> array.pushString(value.toString())
      }
    }

    return array
  }

  private fun JSONObject.toWritableMap(): WritableMap {
    val map = WritableNativeMap()

    keys().forEach { name ->
      when (val value = get(name)) {
        JSONObject.NULL -> map.putNull(name)
        is String -> map.putString(name, value)
        is Boolean -> map.putBoolean(name, value)
        is Int -> map.putInt(name, value)
        is Number -> map.putDouble(name, value.toDouble())
        is JSONObject -> map.putMap(name, value.toWritableMap())
        is JSONArray -> map.putArray(name, value.toWritableArray())
        else -> map.putString(name, value.toString())
      }
    }

    return map
  }

  private fun JSONArray.toWritableArray(): WritableArray {
    val array = WritableNativeArray()

    for (index in 0 until length()) {
      when (val value = get(index)) {
        JSONObject.NULL -> array.pushNull()
        is String -> array.pushString(value)
        is Boolean -> array.pushBoolean(value)
        is Int -> array.pushInt(value)
        is Number -> array.pushDouble(value.toDouble())
        is JSONObject -> array.pushMap(value.toWritableMap())
        is JSONArray -> array.pushArray(value.toWritableArray())
        else -> array.pushString(value.toString())
      }
    }

    return array
  }


  companion object {
    const val NAME = "CelloReactNative"
  }
}
