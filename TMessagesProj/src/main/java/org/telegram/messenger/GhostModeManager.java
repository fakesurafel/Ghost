package org.telegram.messenger;

import android.content.Context;
import android.content.SharedPreferences;

/** Account-scoped privacy settings for GhostGram. */
public final class GhostModeManager {
    private static final String PREF_NAME = "ghostgram_privacy";
    private static final String KEY_GHOST_MODE = "ghost_mode_enabled_";

    private GhostModeManager() {
    }

    public static boolean isGhostModeEnabled() {
        return isGhostModeEnabled(UserConfig.selectedAccount);
    }

    public static boolean isGhostModeEnabled(int account) {
        SharedPreferences preferences = preferences();
        return preferences != null && preferences.getBoolean(KEY_GHOST_MODE + account, false);
    }

    public static void setGhostModeEnabled(boolean enabled) {
        setGhostModeEnabled(UserConfig.selectedAccount, enabled);
    }

    public static void setGhostModeEnabled(int account, boolean enabled) {
        SharedPreferences preferences = preferences();
        if (preferences != null) {
            preferences.edit().putBoolean(KEY_GHOST_MODE + account, enabled).apply();
        }
    }

    public static boolean shouldSuppressTyping() {
        return shouldSuppressTyping(UserConfig.selectedAccount);
    }

    public static boolean shouldSuppressTyping(int account) {
        return isGhostModeEnabled(account);
    }

    public static boolean shouldSuppressReadReceipts() {
        return shouldSuppressReadReceipts(UserConfig.selectedAccount);
    }

    public static boolean shouldSuppressReadReceipts(int account) {
        return isGhostModeEnabled(account);
    }

    private static SharedPreferences preferences() {
        Context context = ApplicationLoader.applicationContext;
        return context == null ? null : context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}
