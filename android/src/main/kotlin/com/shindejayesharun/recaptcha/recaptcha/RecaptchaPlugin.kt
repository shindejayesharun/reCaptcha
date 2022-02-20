package com.shindejayesharun.recaptcha.recaptcha

import android.app.Activity
import android.util.Log
import androidx.annotation.NonNull
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.safetynet.SafetyNet
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result





/** RecaptchaPlugin */
class RecaptchaPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {


  companion object {
    const val TAG = "RecaptchaPlugin"
  }

  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  private var activity: Activity? = null


  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "recaptcha")
    channel.setMethodCallHandler(this)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    this.activity = binding.activity;
  }

  override fun onDetachedFromActivityForConfigChanges() {
    TODO("Not yet implemented")
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    TODO("Not yet implemented")
  }

  override fun onDetachedFromActivity() {
    this.activity = null;
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if(call.method == "reCaptcha"){
      val siteKey: String? = call.argument("key")
      SafetyNet.getClient(activity).verifyWithRecaptcha(siteKey)
        .addOnSuccessListener(activity) { response ->
          if (!response.tokenResult.isEmpty()) {
            Log.d(TAG,"response ${response.tokenResult}")
           result.success(response.tokenResult)
          }
        }
        .addOnFailureListener(activity) { e ->
          if (e is ApiException) {
            Log.d(TAG,("Error message: " + CommonStatusCodes.getStatusCodeString(e.statusCode)))
          } else {
            Log.d(TAG, "Unknown type of error: " + e.message)
          }
        }
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
