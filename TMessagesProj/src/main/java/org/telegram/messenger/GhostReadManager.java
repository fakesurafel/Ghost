package org.telegram.messenger;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * GhostGram - Ghost Read Mode Manager
 * When enabled, read receipts will NOT be sent to the server.
 * Messages will be marked as read locally only.
 */
public class GhostReadManager {

    private static final String PREFS_NAME = "ghostgram_config";
    private static final String KEY_GHOST_MODE = "ghost_read_mode_enabled";

    private static GhostReadManager instance;
    private SharedPreferences prefs;

    private GhostReadManager() {
        prefs = ApplicationLoader.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static GhostReadManager getInstance() {
        if (instance == null) {
            instance = new GhostReadManager();
        }
        return instance;
    }

    public boolean isGhostModeEnabled() {
        return prefs.getBoolean(KEY_GHOST_MODE, false);
    }

    public void setGhostModeEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_GHOST_MODE, enabled).apply();
    }

    /**
     * Call this method before sending read history to server.
     * Returns true if read receipt should be BLOCKED.
     */
    public boolean shouldBlockReadReceipt() {
        return isGhostModeEnabled();
    }

    /**
     * Get status text for UI
     */
    public String getStatusText() {
        return isGhostModeEnabled() ? "Ghost Mode: ON" : "Ghost Mode: OFF";
    }
}
