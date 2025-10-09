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
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class MainHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {        
        XposedBridge.log("mustangSpoof: Hooking into: " + lpparam.packageName);

        // ro.product.board
        XposedHelpers.setStaticObjectField(Build.class, "BOARD", "mustang");

        // ro.product.brand
        XposedHelpers.setStaticObjectField(Build.class, "BRAND", "google");

        // ro.product.device
        XposedHelpers.setStaticObjectField(Build.class, "DEVICE", "mustang");

        // ro.product.name
        XposedHelpers.setStaticObjectField(Build.class, "PRODUCT", "mustang");

        // ro.product.manufacturer
        XposedHelpers.setStaticObjectField(Build.class, "MANUFACTURER", "Google");

        // ro.product.model
        XposedHelpers.setStaticObjectField(Build.class, "MODEL", "Pixel 10 Pro XL");

        // ro.soc.model
        XposedHelpers.setStaticObjectField(Build.class, "SOC_MODEL", "Tensor G5");

        // ro.build.id
        XposedHelpers.setStaticObjectField(Build.class, "ID", "BD3A.251005.003.W3");
		
		// ro.build.date.utc
        XposedHelpers.setStaticObjectField(Build.class, "TIME", "1758594137");

        // ro.build.fingerprint
        XposedHelpers.setStaticObjectField(Build.class, "FINGERPRINT",
                "google/mustang/mustang:16/BD3A.251005.003.W3/14147046:user/release-keys");
    }
}