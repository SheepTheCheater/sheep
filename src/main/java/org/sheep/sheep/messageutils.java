package org.sheep.sheep;

import net.md_5.bungee.api.ChatColor;

public class messageutils {
    public static String getPrefix(){
        return ChatColor.of("#00FFFF") + "" + ChatColor.BOLD + "S" +
                ChatColor.of("#2AFFEF") + "" + ChatColor.BOLD + "h" +
                ChatColor.of("#55FFDF") + "" + ChatColor.BOLD + "e" +
                ChatColor.of("#7FFFCE") + "" + ChatColor.BOLD + "e" +
                ChatColor.of("#AAFFBE") + "" + ChatColor.BOLD + "p" +
                ChatColor.WHITE  + " » ";
    }
    public static String formatMessage(String message){
        return getPrefix() + message;
    }
}
