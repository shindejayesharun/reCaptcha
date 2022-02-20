
import 'dart:async';

import 'package:flutter/services.dart';

class Recaptcha {
  static const MethodChannel _channel = MethodChannel('recaptcha');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String> reCaptcha(String siteKey) async {
    return await _channel.invokeMethod("reCaptcha", {"key": siteKey});
  }
}
