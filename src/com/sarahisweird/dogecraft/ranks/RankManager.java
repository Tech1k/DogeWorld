package com.sarahisweird.dogecraft.ranks;

import com.sarahisweird.dogecraft.dbmanager.DBException;
import com.sarahisweird.dogecraft.dbmanager.DBManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RankManager {
    public static Rank errorRank = new Rank("ERROR", "ERROR", "ERROR") {
        @Override
        public boolean canFormatMessages() {
            return false;
        }
    };

    private static MemberRank memberRank = new MemberRank();
    private static DonatorRank donatorRank = new DonatorRank();
    private static DeveloperRank developerRank = new DeveloperRank();
    private static CMRank cmRank = new CMRank();
    private static OwnerRank ownerRank = new OwnerRank();

    private static Rank rankIdToRank(int id) {
        switch (id) {
            case 0:
                return memberRank;
            case 1:
                return donatorRank;
            case 2:
                return developerRank;
            case 3:
                return cmRank;
            case 4:
                return ownerRank;
            default:
                return errorRank;
        }
    }

    private static int rankToRankId(Rank rank) {
        if (rank instanceof MemberRank) {
            return 0;
        } else if (rank instanceof DonatorRank) {
            return 1;
        } else if (rank instanceof DeveloperRank) {
            return 2;
        } else if (rank instanceof CMRank) {
            return 3;
        } else if (rank instanceof OwnerRank) {
            return 4;
        }

        return -1;
    }

    public static Rank getRank(Player player) {
        try {
            return rankIdToRank(DBManager.getPlayerRank(player));
        } catch (DBException e) {
            return errorRank;
        }
    }

    public static boolean setRank(Player player, Rank rank) {
        try {
            DBManager.setPlayerRank(player, rankToRankId(rank));

            return true;
        } catch (DBException e) {
            return false;
        }
    }

    /** Formats a message based on the player's rank.
     * For example: A member's message won't be formatted, while one from a donator will be.
     * @param player The calling player
     * @param message The message to be formatted
     * @return The (un)formatted message.
     */
    public static String formatMessage(Player player, String message) {
        if (getRank(player).canFormatMessages()) {
            return ChatColor.translateAlternateColorCodes('&', message);
        } else {
            return message;
        }
    }

    public static Rank rankNameToRank(String name) {
        if (name.equalsIgnoreCase("member")) {
            return memberRank;
        } else if (name.equalsIgnoreCase("donator")) {
            return donatorRank;
        } else if (name.equalsIgnoreCase("developer")) {
            return developerRank;
        } else if (name.equalsIgnoreCase("cm")) {
            return cmRank;
        } else if (name.equalsIgnoreCase("owner")) {
            return ownerRank;
        } else {
            return errorRank;
        }
    }
}
