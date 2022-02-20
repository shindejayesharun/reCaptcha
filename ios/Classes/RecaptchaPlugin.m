#import "RecaptchaPlugin.h"
#if __has_include(<recaptcha/recaptcha-Swift.h>)
#import <recaptcha/recaptcha-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "recaptcha-Swift.h"
#endif

@implementation RecaptchaPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftRecaptchaPlugin registerWithRegistrar:registrar];
}
@end
