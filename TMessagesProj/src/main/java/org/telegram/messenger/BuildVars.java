package org.telegram.messenger;

public class BuildVars {

    public static boolean DEBUG = false;
    public static boolean DEBUG_PRIVATE_VERSION = false;
    public static boolean LOGS_ENABLED = false;
    public static boolean USE_CLOUD_STRINGS = true;
    public static boolean CHECK_UPDATES = false;
    public static boolean NO_SCOPED_STORAGE = true;

    // GhostGram Custom Credentials
    public static int APP_ID = 30976966;
    public static String APP_HASH = "0c5e5777a695ace6c079ad5e5f7b6f48";

    public static String SMS_HASH = "O2P2z+/jLJWrzIaG";
    public static String PLAYSTORE_APP_URL = "https://play.google.com/store/apps/details?id=com.ghostgram.app";

    // You can change these to your own links
    public static String TGX_GITHUB = "https://github.com/TGX-Android/Telegram-X";
    public static String TGX_APPCENTER = "https://install.appcenter.ms/orgs/telegram-x/apps/telegram-x/distribution_groups/all-users-of-telegram-x";
    public static String TGX_APPCENTER_SECRET = "";

    static {
        if (ApplicationLoader.applicationContext != null) {
            // Any init code if needed
        }
    }
}
