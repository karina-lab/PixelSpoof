package org.klab.pixelspoof

import android.content.pm.FeatureInfo
import android.os.Build
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

class MainHook : IXposedHookLoadPackage {

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        XposedBridge.log("mustangSpoof: Hooking into: ${lpparam.packageName}")

        XposedHelpers.setStaticObjectField(Build::class.java, "DISPLAY", Values.ID)
        XposedHelpers.setStaticObjectField(Build::class.java, "BOOTLOADER", Values.BOOTLOADER)
        XposedHelpers.setStaticObjectField(Build::class.java, "HARDWARE", Values.DEVICE)
        XposedHelpers.setStaticObjectField(Build::class.java, "BOARD", Values.DEVICE)
        XposedHelpers.setStaticObjectField(Build::class.java, "BRAND", Values.BRAND)
        XposedHelpers.setStaticObjectField(Build::class.java, "DEVICE", Values.DEVICE)
        XposedHelpers.setStaticObjectField(Build::class.java, "PRODUCT", Values.PRODUCT)
        XposedHelpers.setStaticObjectField(Build::class.java, "MANUFACTURER", Values.MANUFACTURER)
        XposedHelpers.setStaticObjectField(Build::class.java, "MODEL", Values.MODEL)
        XposedHelpers.setStaticObjectField(Build::class.java, "SOC_MANUFACTURER", Values.SOC_MANUFACTURER)
        XposedHelpers.setStaticObjectField(Build::class.java, "SOC_MODEL", Values.SOC_MODEL)
        XposedHelpers.setStaticObjectField(Build::class.java, "ID", Values.ID)
        XposedHelpers.setStaticLongField(Build::class.java, "TIME", Values.TIME)
        XposedHelpers.setStaticObjectField(Build::class.java, "TAGS", Values.TAGS)
        XposedHelpers.setStaticObjectField(Build::class.java, "TYPE", Values.TYPE)
        XposedHelpers.setStaticObjectField(Build::class.java, "USER", Values.USER)
        XposedHelpers.setStaticObjectField(Build::class.java, "HOST", Values.HOST)
        XposedHelpers.setStaticObjectField(Build::class.java, "FINGERPRINT", Values.FINGERPRINT)

        XposedHelpers.setStaticObjectField(Build::class.java, "SUPPORTED_ABIS", arrayOf("arm64-v8a"))
        XposedHelpers.setStaticObjectField(Build::class.java, "SUPPORTED_64_BIT_ABIS", arrayOf("arm64-v8a"))

        XposedHelpers.setStaticObjectField(Build.VERSION::class.java, "RELEASE", Values.RELEASE)
        XposedHelpers.setStaticIntField(Build.VERSION::class.java, "SDK_INT", Values.SDK_INT)
        XposedHelpers.setStaticObjectField(Build.VERSION::class.java, "SECURITY_PATCH", Values.SECURITY_PATCH)
        XposedHelpers.setStaticObjectField(Build.VERSION::class.java, "INCREMENTAL", Values.INCREMENTAL)

        XposedHelpers.findAndHookMethod(Build::class.java, "getRadioVersion", object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                param.result = Values.BASEBAND
            }
        })

        val pmsClass = XposedHelpers.findClass("android.app.ApplicationPackageManager", lpparam.classLoader)

        XposedHelpers.findAndHookMethod(pmsClass, "hasSystemFeature", String::class.java, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                if (Values.PIXEL_FEATURES.contains(param.args[0] as String)) {
                    param.result = true
                }
            }
        })

        XposedHelpers.findAndHookMethod(pmsClass, "hasSystemFeature", String::class.java, Int::class.javaPrimitiveType, object : XC_MethodHook() {
            override fun beforeHookedMethod(param: MethodHookParam) {
                if (Values.PIXEL_FEATURES.contains(param.args[0] as String)) {
                    param.result = true
                }
            }
        })

        XposedHelpers.findAndHookMethod(pmsClass, "getSystemAvailableFeatures", object : XC_MethodHook() {
            override fun afterHookedMethod(param: MethodHookParam) {
                val originalFeatures = param.result as? Array<FeatureInfo> ?: return
                val newFeatures = originalFeatures.toMutableList()

                for (featureName in Values.PIXEL_FEATURES) {
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
                    key == "ro.soc.model" -> Values.SOC_MODEL
                    key == "ro.soc.manufacturer" -> Values.SOC_MANUFACTURER
                    key.endsWith(".model") || key == "ro.product.model" || key == "ro.product.model_for_attestation" -> Values.MODEL
                    key.endsWith(".device") || key == "ro.product.device" || key == "ro.product.device_for_attestation" -> Values.DEVICE
                    key.endsWith(".name") || key == "ro.product.name" || key == "ro.product.name_for_attestation" -> Values.PRODUCT
                    key.endsWith(".brand") || key == "ro.product.brand" || key == "ro.product.brand_for_attestation" -> Values.BRAND
                    key.endsWith(".manufacturer") || key == "ro.product.manufacturer" || key == "ro.product.manufacturer_for_attestation" -> Values.MANUFACTURER
                    key.endsWith(".board") || key == "ro.product.board" -> Values.DEVICE
                    key.endsWith(".build.fingerprint") || key == "ro.build.fingerprint" -> Values.FINGERPRINT
                    key.endsWith(".build.id") || key == "ro.build.id" -> Values.ID
                    key.endsWith(".build.tags") || key == "ro.build.tags" -> Values.TAGS
                    key.endsWith(".build.type") || key == "ro.build.type" -> Values.TYPE
                    key == "ro.build.user" -> Values.USER
                    key == "ro.build.host" -> Values.HOST
                    key == "ro.board.platform" -> Values.PLATFORM
                    key == "ro.bootloader" || key == "ro.build.expect.bootloader" -> Values.BOOTLOADER
                    key == "ro.build.description" -> Values.DESCRIPTION
                    key.startsWith("ro.com.google.clientidbase") -> Values.CLIENT_ID
                    key == "ro.opa.eligible_device" -> "true"
                    key.endsWith(".build.version.release") || key == "ro.build.version.release_or_codename" -> Values.RELEASE
                    key.endsWith(".build.version.sdk") -> Values.SDK_INT.toString()
                    key.endsWith(".build.version.security_patch") -> Values.SECURITY_PATCH
                    key.endsWith(".build.version.incremental") -> Values.INCREMENTAL
                    key == "ro.product.first_api_level" -> Values.FIRST_API_LEVEL
                    key == "ro.build.characteristics" -> "nosdcard"
                    key.endsWith(".build.version.sdk_full") -> Values.SDK_FULL
                    key.endsWith(".build.uuid") -> Values.BUILD_UUID
                    key.endsWith(".build.date") -> Values.BUILD_DATE
                    key.endsWith(".build.date.utc") -> Values.TIME_SEC
                    key.contains("baseband") -> Values.BASEBAND
                    key.endsWith(".cpu.abilist") -> "arm64-v8a"
                    key.endsWith(".cpu.abilist32") -> ""
                    key.endsWith(".cpu.abilist64") -> "arm64-v8a"
                    key == "ro.build.flavor" -> "${Values.DEVICE}-user"
                    else -> return
                }
            }
        }

        XposedHelpers.findAndHookMethod(sysPropClass, "get", String::class.java, sysPropHook)
        XposedHelpers.findAndHookMethod(sysPropClass, "get", String::class.java, String::class.java, sysPropHook)
    }
}
