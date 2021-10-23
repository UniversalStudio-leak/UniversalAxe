package ru.universalstudio.universalaxe.utils;

import java.util.*;
import org.bukkit.*;
import java.util.stream.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import org.bukkit.inventory.*;
import org.bukkit.configuration.file.*;
import ru.universalstudio.universalaxe.utils.actionbar.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 * @Author default source code: WinLocker02 (Thank pasting wAxes -> UniversalAxe)
 */

public class Utils {

    private static FileConfiguration config;

    public static FileConfiguration getConfig() {
        return config != null ? config : (config = Config.getFile("config.yml"));
    }

    public static void reloadConfig() {
        config = Config.getFile("config.yml");
    }

    public static String getMessage(String path) {
        return getConfig().getString("messages." + path);
    }

    public static String getString(String path) {
        return getConfig().getString(path);
    }

    public static List<String> getStringList(String path) {
        return getConfig().getStringList(path);
    }

    public static int getInt(String path) {
        return getConfig().getInt(path);
    }

    public static double getDouble(String path) {
        return getConfig().getDouble(path);
    }

    public static boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static List<String> color(List<String> text) {
        return text.stream().map(x -> color(x)).collect(Collectors.toList());
    }

    public static boolean has(CommandSender player, String permission) { // прост винлокер берёт одни и те же утилитки и ему насрать используется тот или иной код. на оптимизацию похуй, а мне не похуй. сказали без костылей - доплатите и получите. сказали на высшем сорте и без костылей - распишите и получите, высший сорт.
        if(!player.hasPermission(permission)) {
            sendMessage(player, getConfig().getString("messages.no-permission"));
            return false;
        }
        return true;
    }

    public static void sendMessage(CommandSender player, String text) {

        if(text.isEmpty()) return;

        for(String line : text.split(";")) {
            line = line.replace("%player%", player.getName());

            if(line.startsWith("title:")) {
                if(player instanceof Player)
                    Title.sendTitle((Player) player, line.split("title:")[1]);
            }
            else if(line.startsWith("actionbar:")) {
                if(player instanceof Player)
                    Actionbar.sendActionbar((Player) player, line.split("actionbar:")[1]);
            }
            else {
                player.sendMessage(color(getMessage("prefix") + line));
            }
        }
    }

    public static void giveItem(Player player, ItemStack item) {
        if(isInventoryFull(player)) {
            Location loc = player.getLocation();
            loc.getWorld().dropItem(loc, item);
        }
        else {
            player.getInventory().addItem(item);
        }
    }

    public static boolean isInventoryFull(Player player) {
        return !Arrays.asList(player.getInventory().getStorageContents()).contains(null);
    }
}