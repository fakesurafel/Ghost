package org.telegram.messenger;

import org.telegram.tgnet.TLRPC;

import java.util.ArrayList;

/**
 * GhostGram's direct-chat policy.
 *
 * This class is deliberately limited to classification.  It does not mutate
 * the server dialog list; callers decide whether to omit a row or reject a
 * navigation target.  A dialog is allowed only when it resolves to a normal
 * user peer.  Bots, self-chat, groups, channels, stories and folder/service
 * rows are rejected.
 */
public final class PrivateChatFilter {
    private PrivateChatFilter() {
    }

    public static boolean shouldShowDialog(int account, TLRPC.Dialog dialog) {
        if (dialog == null || dialog.id == 0 || dialog.folder_id != 0) {
            return false;
        }
        if (!DialogObject.isUserDialog(dialog.id)) {
            return false;
        }
        if (dialog.id == UserConfig.getInstance(account).getClientUserId()) {
            return false;
        }
        TLRPC.User user = AccountInstance.getInstance(account).getMessagesController().getUser(dialog.id);
        return user != null && !user.bot && !user.deleted;
    }

    public static boolean shouldShowDialog(long dialogId) {
        int account = UserConfig.selectedAccount;
        if (dialogId <= 0 || dialogId == UserConfig.getInstance(account).getClientUserId()) {
            return false;
        }
        TLRPC.User user = AccountInstance.getInstance(account).getMessagesController().getUser(dialogId);
        return user != null && !user.bot && !user.deleted;
    }

    public static ArrayList<TLRPC.Dialog> filterPrivateChatsOnly(int account, ArrayList<TLRPC.Dialog> dialogs) {
        ArrayList<TLRPC.Dialog> filtered = new ArrayList<>();
        if (dialogs == null) {
            return filtered;
        }
        for (TLRPC.Dialog dialog : dialogs) {
            if (shouldShowDialog(account, dialog)) {
                filtered.add(dialog);
            }
        }
        return filtered;
    }

    public static ArrayList<TLRPC.Dialog> filterPrivateChatsOnly(ArrayList<TLRPC.Dialog> dialogs) {
        return filterPrivateChatsOnly(UserConfig.selectedAccount, dialogs);
    }

    public static boolean isPrivateChatAllowed(long dialogId) {
        return shouldShowDialog(dialogId);
    }

    public static String getBlockedMessage() {
        return "GhostGram allows direct personal chats only.";
    }
}
