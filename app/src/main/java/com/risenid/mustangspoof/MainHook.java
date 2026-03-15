/*
 * WearableSpoof
 * Copyright (C) 2023 Simon1511
 * CaimanSpoof
 * Copyright (C) 2024 RisenID
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.risenid.mustangspoof;

import android.os.Build;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {

    private static final String MODEL = "Pixel 10 Pro XL";
    private static final String DEVICE = "mustang";
    private static final String PRODUCT = "mustang";
    private static final String BRAND = "google";
    private static final String MANUFACTURER = "Google";
    private static final String FINGERPRINT = "google/mustang/mustang:16/CP1A.260305.018/14887507:user/release-keys";
    private static final String DESCRIPTION = "mustang-user 16 CP1A.260305.018 14887507 release-keys";
    private static final String ID = "CP1A.260305.018";
    private static final String BOOTLOADER = "deepspace-16.4-14791471";
    
    private static final String SOC_MANUFACTURER = "Google";
    private static final String SOC_MODEL = "Tensor G5";
    private static final String PLATFORM = "laguna";
    
    private static final long TIME = 1771034134000L;
    private static final String TIME_SEC = "1771034134";
    private static final String TAGS = "release-keys";
    private static final String TYPE = "user";
    private static final String USER = "android-build";
    private static final String HOST = "704b74c63ab8";
    
    private static final String RELEASE = "16";
    private static final int SDK_INT = 36;
    private static final String SDK_FULL = "36.1";
    private static final String SECURITY_PATCH = "2026-03-05";
    private static final String INCREMENTAL = "14887507";
    private static final String FIRST_API_LEVEL = "36";
    
    private static final String BUILD_DATE = "Sat Feb 14 01:55:34 UTC 2026";
    private static final String BUILD_UUID = "aB0LtAKwyW0cOiNpwy9jW6CCp4H4kd0KBNSmuvs1B6k";
    private static final String BASEBAND = "g5400i-251201-260127-B-14784805";
    
    private static final String CLIENT_ID = "android-google";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {        
        XposedBridge.log("mustangSpoof: Hooking into: " + lpparam.packageName);

        // --- Static Build Fields ---
        XposedHelpers.setStaticObjectField(Build.class, "DISPLAY", ID);
        XposedHelpers.setStaticObjectField(Build.class, "BOOTLOADER", BOOTLOADER);
        XposedHelpers.setStaticObjectField(Build.class, "HARDWARE", DEVICE);
        XposedHelpers.setStaticObjectField(Build.class, "BOARD", DEVICE);
        XposedHelpers.setStaticObjectField(Build.class, "BRAND", BRAND);
        XposedHelpers.setStaticObjectField(Build.class, "DEVICE", DEVICE);
        XposedHelpers.setStaticObjectField(Build.class, "PRODUCT", PRODUCT);
        XposedHelpers.setStaticObjectField(Build.class, "MANUFACTURER", MANUFACTURER);
        XposedHelpers.setStaticObjectField(Build.class, "MODEL", MODEL);
        XposedHelpers.setStaticObjectField(Build.class, "SOC_MANUFACTURER", SOC_MANUFACTURER);
        XposedHelpers.setStaticObjectField(Build.class, "SOC_MODEL", SOC_MODEL);
        XposedHelpers.setStaticObjectField(Build.class, "ID", ID);
        XposedHelpers.setStaticLongField(Build.class, "TIME", TIME);
        XposedHelpers.setStaticObjectField(Build.class, "TAGS", TAGS);	
        XposedHelpers.setStaticObjectField(Build.class, "TYPE", TYPE);
        XposedHelpers.setStaticObjectField(Build.class, "USER", USER);
        XposedHelpers.setStaticObjectField(Build.class, "HOST", HOST);
        XposedHelpers.setStaticObjectField(Build.class, "FINGERPRINT", FINGERPRINT);

        XposedHelpers.setStaticObjectField(Build.class, "SUPPORTED_ABIS", new String[]{"arm64-v8a"});
        XposedHelpers.setStaticObjectField(Build.class, "SUPPORTED_64_BIT_ABIS", new String[]{"arm64-v8a"});

        XposedHelpers.setStaticObjectField(Build.VERSION.class, "RELEASE", RELEASE);
        XposedHelpers.setStaticIntField(Build.VERSION.class, "SDK_INT", SDK_INT);
        XposedHelpers.setStaticObjectField(Build.VERSION.class, "SECURITY_PATCH", SECURITY_PATCH);
        XposedHelpers.setStaticObjectField(Build.VERSION.class, "INCREMENTAL", INCREMENTAL);

        // --- Radio/Baseband Hook ---
        XposedHelpers.findAndHookMethod(Build.class, "getRadioVersion", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.setResult(BASEBAND);
            }
        });

        // --- SystemProperties Hook ---
        Class<?> sysPropClass = XposedHelpers.findClass("android.os.SystemProperties", lpparam.classLoader);
        
        XC_MethodHook sysPropHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                String key = (String) param.args[0];

                if (key.equals("ro.soc.model")) param.setResult(SOC_MODEL);
                else if (key.equals("ro.soc.manufacturer")) param.setResult(SOC_MANUFACTURER);
                else if (key.endsWith(".model") || key.equals("ro.product.model") || key.equals("ro.product.model_for_attestation")) param.setResult(MODEL);
                else if (key.endsWith(".device") || key.equals("ro.product.device") || key.equals("ro.product.device_for_attestation")) param.setResult(DEVICE);
                else if (key.endsWith(".name") || key.equals("ro.product.name") || key.equals("ro.product.name_for_attestation")) param.setResult(PRODUCT);
                else if (key.endsWith(".brand") || key.equals("ro.product.brand") || key.equals("ro.product.brand_for_attestation")) param.setResult(BRAND);
                else if (key.endsWith(".manufacturer") || key.equals("ro.product.manufacturer") || key.equals("ro.product.manufacturer_for_attestation")) param.setResult(MANUFACTURER);
                else if (key.endsWith(".board") || key.equals("ro.product.board")) param.setResult(DEVICE);
                else if (key.endsWith(".build.fingerprint") || key.equals("ro.build.fingerprint")) param.setResult(FINGERPRINT);
                else if (key.endsWith(".build.id") || key.equals("ro.build.id")) param.setResult(ID);
                else if (key.endsWith(".build.tags") || key.equals("ro.build.tags")) param.setResult(TAGS);
                else if (key.endsWith(".build.type") || key.equals("ro.build.type")) param.setResult(TYPE);
                else if (key.equals("ro.build.user")) param.setResult(USER);
                else if (key.equals("ro.build.host")) param.setResult(HOST);
                else if (key.equals("ro.board.platform")) param.setResult(PLATFORM);
                else if (key.equals("ro.bootloader") || key.equals("ro.build.expect.bootloader")) param.setResult(BOOTLOADER);
                else if (key.equals("ro.build.description")) param.setResult(DESCRIPTION);
                else if (key.startsWith("ro.com.google.clientidbase")) param.setResult(CLIENT_ID);
                else if (key.equals("ro.opa.eligible_device")) param.setResult("true");
                else if (key.endsWith(".build.version.release") || key.equals("ro.build.version.release_or_codename")) param.setResult(RELEASE);
                else if (key.endsWith(".build.version.sdk")) param.setResult(String.valueOf(SDK_INT));
                else if (key.endsWith(".build.version.security_patch")) param.setResult(SECURITY_PATCH);
                else if (key.endsWith(".build.version.incremental")) param.setResult(INCREMENTAL);
                else if (key.equals("ro.product.first_api_level")) param.setResult(FIRST_API_LEVEL);
                else if (key.equals("ro.build.characteristics")) param.setResult("nosdcard");
                else if (key.endsWith(".build.version.sdk_full")) param.setResult(SDK_FULL);
                else if (key.endsWith(".build.uuid")) param.setResult(BUILD_UUID);
                else if (key.endsWith(".build.date")) param.setResult(BUILD_DATE);
                else if (key.endsWith(".build.date.utc")) param.setResult(TIME_SEC);
                else if (key.contains("baseband")) param.setResult(BASEBAND);
                else if (key.endsWith(".cpu.abilist")) param.setResult("arm64-v8a");
                else if (key.endsWith(".cpu.abilist32")) param.setResult("");
                else if (key.endsWith(".cpu.abilist64")) param.setResult("arm64-v8a");
                else if (key.equals("ro.build.flavor")) param.setResult(DEVICE + "-user");
            }
        };

        XposedHelpers.findAndHookMethod(sysPropClass, "get", String.class, sysPropHook);
        XposedHelpers.findAndHookMethod(sysPropClass, "get", String.class, String.class, sysPropHook);
    }
}
