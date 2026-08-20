package org.telegram.messenger;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.telegram.tgnet.ConnectionsManager;

import java.util.Locale;

/**
 * Authentication-related helpers used by GhostGram.
 *
 * Telegram phone authentication remains the canonical login flow. API ID and
 * hash identify the application, not a user session, so this helper validates
 * and persists them for the next process start; it does not pretend that
 * changing them at runtime authenticates an account.
 */
public final class GhostGramLoginHelper {
    private static final String PREFS = "ghostgram_credentials";
    private static final String KEY_API_ID = "api_id";
    private static final String KEY_API_HASH = "api_hash";

    private GhostGramLoginHelper() {
    }

    public static boolean isValidApiCredentials(int apiId, String apiHash) {
        return apiId > 0 && !TextUtils.isEmpty(apiHash) && apiHash.matches("[0-9a-fA-F]{32}");
    }

    public static boolean saveApiCredentials(Context context, int apiId, String apiHash) {
        if (context == null || !isValidApiCredentials(apiId, apiHash)) {
            return false;
        }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .edit()
                .putInt(KEY_API_ID, apiId)
                .putString(KEY_API_HASH, apiHash.toLowerCase(Locale.US))
                .apply();
        return true;
    }

    public static int getConfiguredApiId() {
        Context context = ApplicationLoader.applicationContext;
        if (context == null) {
            return BuildVars.APP_ID;
        }
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getInt(KEY_API_ID, BuildVars.APP_ID);
    }

    public static String getConfiguredApiHash() {
        Context context = ApplicationLoader.applicationContext;
        if (context == null) {
            return BuildVars.APP_HASH;
        }
        return context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
                .getString(KEY_API_HASH, BuildVars.APP_HASH);
    }

    public static boolean hasCustomApiCredentials() {
        return isValidApiCredentials(getConfiguredApiId(), getConfiguredApiHash())
                && getConfiguredApiId() != BuildVars.APP_ID;
    }

    /**
     * Returns a non-secret identifier for the current authenticated session.
     * The raw MTProto authorization key is intentionally never exposed to the
     * UI or clipboard. This identifier is suitable for account diagnostics,
     * not for logging in on another device.
     */
    public static String getCurrentSessionKeyId(int account) {
        if (!UserConfig.getInstance(account).isClientActivated()) {
            return null;
        }
        long authKeyId = ConnectionsManager.native_getCurrentAuthKeyId(account);
        if (authKeyId == 0) {
            return null;
        }
        int dcId = ConnectionsManager.native_getCurrentDatacenterId(account);
        return String.format(Locale.US, "dc%d:%016x", dcId, authKeyId);
    }

    public static String exportCurrentSessionString() {
        return getCurrentSessionKeyId(UserConfig.selectedAccount);
    }
}
