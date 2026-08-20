package org.telegram.messenger;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Base64;

import org.telegram.tgnet.ConnectionsManager;

/**
 * Ghost Gram session utilities.
 *
 * The Android client exposes an auth-key identifier but not the private auth-key
 * bytes needed to create a Telethon/Pyrogram session string. This helper never
 * invents credential material and never claims that an identifier is a portable
 * session string.
 */
public final class SessionExportHelper {
    private SessionExportHelper() {
    }

    /**
     * A portable session string cannot be exported from the current native
     * client bridge. Return null rather than returning fabricated bytes.
     */
    public static String exportSessionString() {
        return null;
    }

    public static long getSessionKeyId() {
        return ConnectionsManager.getInstance(UserConfig.selectedAccount).getCurrentAuthKeyId();
    }

    public static String getSessionKeyIdText() {
        long keyId = getSessionKeyId();
        return keyId == 0 ? "Unavailable" : Long.toUnsignedString(keyId);
    }

    public static void copySessionToClipboard(Context context, String sessionString) {
        if (sessionString == null || sessionString.isEmpty()) {
            return;
        }
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText("Ghost Gram session identifier", sessionString));
        }
    }

    /**
     * Performs format validation only. Importing an external session still
     * requires a native MTProto import path and therefore is not activated here.
     */
    public static boolean isSupportedSessionString(String sessionString) {
        if (sessionString == null || sessionString.trim().isEmpty()) {
            return false;
        }
        try {
            byte[] decoded = Base64.decode(sessionString.trim(), Base64.DEFAULT | Base64.URL_SAFE | Base64.NO_WRAP);
            return decoded.length >= 32;
        } catch (IllegalArgumentException ignored) {
            return false;
        }
    }

    public static boolean importSessionString(String sessionString) {
        return false;
    }

    public static String getSessionInfo() {
        int dcId = ConnectionsManager.getInstance(UserConfig.selectedAccount).getCurrentDatacenterId();
        return "DC: " + dcId + "\nAuth key ID: " + getSessionKeyIdText();
    }
}
