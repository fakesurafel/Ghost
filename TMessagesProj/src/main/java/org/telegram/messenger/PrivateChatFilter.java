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
    public static boolean isPrivateMode() {
        return true;
    }

    public static boolean shouldShowDialog(long dialogId) {
        return shouldShowDialog(UserConfig.selectedAccount, dialogId);
    }

    public static boolean shouldShowDialog(int account, long dialogId) {
        if (DialogObject.isEncryptedDialog(dialogId)) {
            TLRPC.EncryptedChat encryptedChat = MessagesController.getInstance(account).getEncryptedChat(DialogObject.getEncryptedChatId(dialogId));
            if (encryptedChat == null) {
                return false;
            }
            dialogId = encryptedChat.user_id;
        }

        // Positive IDs represent users; negative IDs represent groups and channels.
        if (dialogId <= 0) {
            return false;
        }

        TLRPC.User user = MessagesController.getInstance(account).getUser(dialogId);
        return user != null && !user.bot && !user.self && !user.deleted;
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
     */
    public static boolean isPrivateChatAllowed(long dialogId) {
        return isPrivateMode() && shouldShowDialog(dialogId);
    }

    /**
     * Get error message when trying to access blocked feature
     */
    public static String getBlockedMessage() {
        return "GhostGram: Only private chats are allowed.";
    }
}
