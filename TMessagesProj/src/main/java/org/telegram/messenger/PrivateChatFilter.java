package org.telegram.messenger;

import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;

/**
 * GhostGram - Private Chat Filter
 * Removes groups, channels, bots, and stories from the app.
 * Only allows private 1-on-1 user chats.
 */
public class PrivateChatFilter {

    private static final String PREFS_NAME = "ghostgram_config";
    private static final String KEY_PRIVATE_ONLY = "private_chats_only";

    /**
     * Check if a dialog should be shown (private chats only)
     * @param dialogId The dialog ID
     * @return true if should show, false if should hide
     */
    public static boolean shouldShowDialog(long dialogId) {
        // Positive IDs = users (private chats)
        // Negative IDs = groups/channels
        if (dialogId < 0) {
            return false; // Hide groups and channels
        }

        // Check if it's a bot
        TLRPC.User user = MessagesController.getInstance().getUser(dialogId);
        if (user != null && user.bot) {
            return false; // Hide bots
        }

        return true;
    }

    /**
     * Filter a list of dialogs to only show private chats
     */
    public static ArrayList<TLRPC.Dialog> filterPrivateChatsOnly(ArrayList<TLRPC.Dialog> dialogs) {
        ArrayList<TLRPC.Dialog> filtered = new ArrayList<>();
        if (dialogs == null) return filtered;

        for (TLRPC.Dialog dialog : dialogs) {
            if (shouldShowDialog(dialog.id)) {
                filtered.add(dialog);
            }
        }
        return filtered;
    }

    /**
     * Check if user is trying to open a non-private chat
     * Use this in ChatActivity to block entry
     */
    public static boolean isPrivateChatAllowed(long dialogId) {
        return shouldShowDialog(dialogId);
    }

    /**
     * Get error message when trying to access blocked feature
     */
    public static String getBlockedMessage() {
        return "GhostGram: Only private chats are allowed.";
    }
}
