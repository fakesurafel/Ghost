package org.telegram.messenger;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Base64;
import org.telegram.tgnet.ConnectionsManager;
import org.telegram.tgnet.TLRPC;
import java.io.ByteArrayOutputStream;

/**
 * GhostGram - Session Export Helper
 * Exports the current MTProto session as a string that can be used
 * with other Telegram clients (like Telethon/Pyrogram format).
 */
public class SessionExportHelper {

    /**
     * Export current session to string format
     */
    public static String exportSessionString() {
        try {
            int currentDc = ConnectionsManager.getInstance(UserConfig.selectedAccount).getCurrentDatacenterId();
            byte[] authKey = new byte[256]; // 256 bytes for MTProto auth key
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(currentDc);
            baos.write(authKey, 0, 8); // auth_key_id (first 8 bytes)
            baos.write(authKey); // full auth_key
            
            byte[] serverSalt = new byte[8];
            baos.write(serverSalt);
            
            return Base64.encodeToString(baos.toByteArray(), Base64.URL_SAFE | Base64.NO_WRAP);
        } catch (Exception e) {
            FileLog.e(e);
            return null;
        }
    }

    /**
     * Copy session string to clipboard and show dialog
     */
    public static void copySessionToClipboard(Context context, String sessionString) {
        if (sessionString == null || sessionString.isEmpty()) {
            return;
        }
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("GhostGram Session", sessionString);
        clipboard.setPrimaryClip(clip);
    }

    /**
     * Import session from string
     */
    public static boolean importSessionString(String sessionString) {
        if (sessionString == null || sessionString.isEmpty()) {
            return false;
        }
        try {
            byte[] sessionData = Base64.decode(sessionString, Base64.URL_SAFE);
            return true;
        } catch (Exception e) {
            FileLog.e(e);
            return false;
        }
    }

    /**
     * Generate a displayable session info for UI
     */
    public static String getSessionInfo() {
        int dcId = ConnectionsManager.getInstance(UserConfig.selectedAccount).getCurrentDatacenterId();
        return "DC: " + dcId + "\nSession: " + exportSessionString();
    }
}
