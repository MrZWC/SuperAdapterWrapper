/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/

package com.example.superadapterwrapper.widget.cordova;

import android.app.Activity;

import org.apache.cordova.ConfigXmlParser;
import org.apache.cordova.CordovaPreferences;
import org.apache.cordova.PluginEntry;

import java.util.List;

@Deprecated // Use Whitelist, CordovaPrefences, etc. directly.
public class MyConfig {
    private static final String TAG = "Config";

    static ConfigXmlParser parser;

    private MyConfig() {
    }

    public static void init(Activity action) {
        parser = new ConfigXmlParser();
        parser.parse(action);
        parser.getPreferences().setPreferencesBundle(action.getIntent().getExtras());
    }

    // Intended to be used for testing only; creates an empty configuration.
    public static void init() {
        if (parser == null) {
            parser = new ConfigXmlParser();
        }
    }

    public static String getStartUrl() {
        if (parser == null) {
            return "file:///android_asset/www/index.html";
        }
        return parser.getLaunchUrl();
    }

    public static String getErrorUrl() {
        return parser.getPreferences().getString("errorurl", null);
    }

    public static List<PluginEntry> getPluginEntries() {
        return parser.getPluginEntries();
    }

    public static CordovaPreferences getPreferences() {
        return parser.getPreferences();
    }

    public static boolean isInitialized() {
        return parser != null;
    }
}
