package org.telegram.messenger;

import android.content.Context;
import android.content.SharedPreferences;

public class BuildVars {

    public static boolean DEBUG = false;
    public static boolean DEBUG_PRIVATE_VERSION = false;
    public static boolean LOGS_ENABLED = false;
    public static boolean USE_CLOUD_STRINGS = true;
    public static boolean CHECK_UPDATES = false;
    public static boolean NO_SCOPED_STORAGE = true;

    // Additional flags expected by compilation (synced with gradle.properties)
    public static boolean DEBUG_VERSION = false;
    public static boolean IS_BILLING_UNAVAILABLE = false;
    public static String GOOGLE_AUTH_CLIENT_ID = "";
    public static String APP_VERSION_NAME = "12.9.2";
    public static int APP_VERSION_CODE = 6991;
    public static String BUILD_VERSION_STRING = "12.9.2";

    // GhostGram Custom Credentials
    public static int APP_ID = 30976966;
    public static String APP_HASH = "0c5e5777a695ace6c079ad5e5f7b6f48";

    public static String SMS_HASH = "O2P2z+/jLJWrzIaG";
    public static String PLAYSTORE_APP_URL = "https://play.google.com/store/apps/details?id=com.ghostgram.app";
    public static String HUAWEI_STORE_URL = "https://appgallery.huawei.com/app/C123456789";

    // Security and features
    public static boolean SUPPORTS_PASSKEYS = true;
    public static String SAFETYNET_KEY = "";

    // You can change these to your own links
    public static String TGX_GITHUB = "https://github.com/TGX-Android/Telegram-X";
    public static String TGX_APPCENTER = "https://install.appcenter.ms/orgs/telegram-x/apps/telegram-x/distribution_groups/all-users-of-telegram-x";
    public static String TGX_APPCENTER_SECRET = "";

    public static boolean isHuaweiStoreApp() {
        return false;
    }

    public static boolean isBetaApp() {
        return false;
    }

    public static boolean isStandaloneApp() {
        return true;
    }

    public static boolean isGooglePlayApp() {
        return true;
    }

    public static boolean useInvoiceBilling() {
        return false;
    }

    public static String getSmsHash() {
        return SMS_HASH;
    }

    static {
        if (ApplicationLoader.applicationContext != null) {
            // Any init code if needed
        }
    }
}
