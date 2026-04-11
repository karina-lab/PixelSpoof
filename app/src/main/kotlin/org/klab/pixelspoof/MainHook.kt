package org.klab.pixelspoof

import android.content.pm.FeatureInfo
import android.os.Build
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {

    companion object {
        private const val MODEL = "Pixel 10 Pro XL"
        private const val DEVICE = "mustang"
        private const val PRODUCT = "mustang"
        private const val BRAND = "google"
        private const val MANUFACTURER = "Google"
        private const val FINGERPRINT = "google/mustang/mustang:16/CP1A.260405.005/15001963:user/release-keys"
        private const val DESCRIPTION = "mustang-user 16 CP1A.260405.005 15001963 release-keys"
        private const val ID = "CP1A.260405.005"
        private const val BOOTLOADER = "deepspace-16.4-14791471"

        private const val SOC_MANUFACTURER = "Google"
        private const val SOC_MODEL = "Tensor G5"
        private const val PLATFORM = "laguna"

        private const val TIME = 1773134299000L
        private const val TIME_SEC = "1773134299"
        private const val TAGS = "release-keys"
        private const val TYPE = "user"
        private const val USER = "android-build"
        private const val HOST = "704b74c63ab8"

        private const val RELEASE = "16"
        private const val SDK_INT = 36
        private const val SDK_FULL = "36.1"
        private const val SECURITY_PATCH = "2026-04-05"
        private const val INCREMENTAL = "15001963"
        private const val FIRST_API_LEVEL = "36"

        private const val BUILD_DATE = "Tue Mar 10 09:18:19 UTC 2026"
        private const val BUILD_UUID = "-Y1LYKyFD2eSJBBIi73YKklZAh0xm8WiliiY6e8vlYg"
        private const val BASEBAND = "g5400i-251201-260127-B-14784805"

        private const val CLIENT_ID = "android-google"

        private val PIXEL_FEATURES = setOf(
            "com.google.android.feature.PIXEL_EXPERIENCE",
            "com.google.android.feature.TURBO_PRELOAD",
            "com.google.android.feature.WELLBEING",
            "com.google.android.feature.D2D_CABLE_MIGRATION_FEATURE",
            "com.google.android.feature.PIXEL_2017_EXPERIENCE",
            "com.google.android.feature.PIXEL_2018_EXPERIENCE",
            "com.google.android.feature.PIXEL_2019_EXPERIENCE",
            "com.google.android.feature.PIXEL_2019_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2020_EXPERIENCE",
            "com.google.android.feature.PIXEL_2020_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2021_EXPERIENCE",
            "com.google.android.feature.PIXEL_2021_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2022_EXPERIENCE",
            "com.google.android.feature.PIXEL_2022_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2023_EXPERIENCE",
            "com.google.android.feature.PIXEL_2023_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2024_EXPERIENCE",
            "com.google.android.feature.PIXEL_2024_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.PIXEL_2025_EXPERIENCE",
            "com.google.android.feature.PIXEL_2025_MIDYEAR_EXPERIENCE",
            "com.google.android.feature.GOOGLE_BUILD",
            "com.google.android.feature.GOOGLE_EXPERIENCE",
            "com.google.android.feature.GOOGLE_CAMERA_EXPERIENCE",
            "com.google.android.feature.QUICK_TAP",
            "com.google.android.feature.NOW_PLAYING_APP_26Q1",
            "com.google.android.feature.NEXT_GENERATION_ASSISTANT",
            "com.google.android.feature.GEMINI_EXPERIENCE",
            "com.google.android.feature.AMBIENT_DATA",
            "com.google.android.feature.CONTEXTUAL_SEARCH",
            "com.google.android.feature.CONTEXTUAL_SEARCH_LIVE_TRANSLATE",
            "com.android.systemui.SUPPORTS_DRAG_ASSISTANT_TO_SPLIT"
        )
    }

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        XposedBridge.log("mustangSpoof: Hooking into: ${lpparam.packageName}")

        XposedHelpers.setStaticObjectField(Build::class.java, "DISPLAY", ID)
        XposedHelpers.setStaticObjectField(Build::class.java, "BOOTLOADER", BOOTLOADER)
        XposedHelpers.setStaticObjectField(Build::class.java, "HARDWARE", DEVICE)
        XposedHelpers.setStaticObjectField(Build::class.java, "BOARD", DEVICE)
        XposedHelpers.setStaticObjectField(Build::class.java, "BRAND", BRAND)
        XposedHelpers.setStaticObjectField(Build::class.java, "DEVICE", DEVICE)
        XposedHelpers.setStaticObjectField(Build::class.java, "PRODUCT", PRODUCT)
        XposedHelpers.setStaticObjectField(Build::class.java, "MANUFACTURER", MANUFACTURER)
        XposedHelpers.setStaticObjectField(Build::class.java, "MODEL", MODEL)
        XposedHelpers.setStaticObjectField(Build::class.java, "SOC_MANUFACTURER", SOC_MANUFACTURER)
        XposedHelpers.setStaticObjectField(Build::class.java, "SOC_MODEL", SOC_MODEL)
        XposedHelpers.setStaticObjectField(Build::class.java, "ID", ID)
        XposedHelpers.setStaticLongField(Build::class.java, "TIME", TIME)
        XposedHelpers.setStaticObjectField(Build::class.java, "TAGS", TAGS)
        XposedHelpers.setStaticObjectField(Build::class.java, "TYPE", TYPE)
        XposedHelpers.setStaticObjectField(Build::class.java, "USER", USER)
        XposedHelpers.setStaticObjectField(Build::class.java, "HOST", HOST)
        XposedHelpers.setStaticObjectField(Build::class.java, "FINGERPRINT", FINGERPRINT)

        XposedHelpers.setStaticObjectField(Build::class.java, "SUPPORTED_ABIS", arrayOf("arm64-v8a"))
        XposedHelpers.setStaticObjectField(Build::class.java, "SUPPORTED_64_BIT_ABIS", arrayOf("arm64-v8a"))

        XposedHelpers.setStaticObjectField(Build.VERSION::class.java, "RELEASE", RELEASE)
        XposedHelpers.setStaticIntField(Build.VERSION::class.java, "SDK_INT", SDK_INT)
        XposedHelpers.setStaticObjectField(Build.VERSION::class.java, "SECURITY_PATCH", SECURITY_PATCH)
        XposedHelpers.setStaticObjectField(Build.VERSION::class.java, "INCREMENTAL", INCREMENTAL)

        XposedHelpers.findAndHookMethod(Build::class.java, "getRadioVersion", object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                param.result = BASEBAND
            }
        })

        val pmsClass = XposedHelpers.findClass("android.app.ApplicationPackageManager", lpparam.classLoader)

        XposedHelpers.findAndHookMethod(pmsClass, "hasSystemFeature", String::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                if (PIXEL_FEATURES.contains(param.args[0] as String)) {
                    param.result = true
                }
            }
        })

        XposedHelpers.findAndHookMethod(pmsClass, "hasSystemFeature", String::class.java, Int::class.javaPrimitiveType, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                if (PIXEL_FEATURES.contains(param.args[0] as String)) {
                    param.result = true
                }
            }
        })

        XposedHelpers.findAndHookMethod(pmsClass, "getSystemAvailableFeatures", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                val originalFeatures = param.result as? Array<FeatureInfo> ?: return
                val newFeatures = originalFeatures.toMutableList()

                for (featureName in PIXEL_FEATURES) {
                    if (originalFeatures.none { it.name == featureName }) {
                        val info = FeatureInfo().apply { name = featureName }
                        newFeatures.add(info)
                    }
                }
                param.result = newFeatures.toTypedArray()
            }
        })

        val sysPropClass = XposedHelpers.findClass("android.os.SystemProperties", lpparam.classLoader)

        val sysPropHook = object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                val key = param.args[0] as String

                param.result = when {
                    key == "ro.soc.model" -> SOC_MODEL
                    key == "ro.soc.manufacturer" -> SOC_MANUFACTURER
                    key.endsWith(".model") || key == "ro.product.model" || key == "ro.product.model_for_attestation" -> MODEL
                    key.endsWith(".device") || key == "ro.product.device" || key == "ro.product.device_for_attestation" -> DEVICE
                    key.endsWith(".name") || key == "ro.product.name" || key == "ro.product.name_for_attestation" -> PRODUCT
                    key.endsWith(".brand") || key == "ro.product.brand" || key == "ro.product.brand_for_attestation" -> BRAND
                    key.endsWith(".manufacturer") || key == "ro.product.manufacturer" || key == "ro.product.manufacturer_for_attestation" -> MANUFACTURER
                    key.endsWith(".board") || key == "ro.product.board" -> DEVICE
                    key.endsWith(".build.fingerprint") || key == "ro.build.fingerprint" -> FINGERPRINT
                    key.endsWith(".build.id") || key == "ro.build.id" -> ID
                    key.endsWith(".build.tags") || key == "ro.build.tags" -> TAGS
                    key.endsWith(".build.type") || key == "ro.build.type" -> TYPE
                    key == "ro.build.user" -> USER
                    key == "ro.build.host" -> HOST
                    key == "ro.board.platform" -> PLATFORM
                    key == "ro.bootloader" || key == "ro.build.expect.bootloader" -> BOOTLOADER
                    key == "ro.build.description" -> DESCRIPTION
                    key.startsWith("ro.com.google.clientidbase") -> CLIENT_ID
                    key == "ro.opa.eligible_device" -> "true"
                    key.endsWith(".build.version.release") || key == "ro.build.version.release_or_codename" -> RELEASE
                    key.endsWith(".build.version.sdk") -> SDK_INT.toString()
                    key.endsWith(".build.version.security_patch") -> SECURITY_PATCH
                    key.endsWith(".build.version.incremental") -> INCREMENTAL
                    key == "ro.product.first_api_level" -> FIRST_API_LEVEL
                    key == "ro.build.characteristics" -> "nosdcard"
                    key.endsWith(".build.version.sdk_full") -> SDK_FULL
                    key.endsWith(".build.uuid") -> BUILD_UUID
                    key.endsWith(".build.date") -> BUILD_DATE
                    key.endsWith(".build.date.utc") -> TIME_SEC
                    key.contains("baseband") -> BASEBAND
                    key.endsWith(".cpu.abilist") -> "arm64-v8a"
                    key.endsWith(".cpu.abilist32") -> ""
                    key.endsWith(".cpu.abilist64") -> "arm64-v8a"
                    key == "ro.build.flavor" -> "$DEVICE-user"
                    else -> return
                }
            }
        }

        XposedHelpers.findAndHookMethod(sysPropClass, "get", String::class.java, sysPropHook)
        XposedHelpers.findAndHookMethod(sysPropClass, "get", String::class.java, String::class.java, sysPropHook)
    }
}
