package ru.universalstudio.universalaxe.utils.actionbar;

import org.bukkit.*;
import org.bukkit.entity.*;
import java.lang.reflect.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 * @Author default source code: WinLocker02 (Thank pasting wAxes -> UniversalAxe)
 */

public class Actionbar {

   private static String version;

   static{ // пусть будет
      String player = Bukkit.getServer().getClass().getPackage().getName();
      version = player.substring(player.lastIndexOf(46) + 1);
   }

   public static void sendActionbar(Player player, String text) {
      text = ChatColor.translateAlternateColorCodes('&', text);

      try {
         if (!version.equals("v1_16_R1") && !version.equals("v1_16_R2") && !version.equals("v1_16_R3")) {
            if (!version.equals("v1_12_R1") && !version.startsWith("v1_13") && !version.startsWith("v1_14_") && !version.startsWith("v1_15_")) {
               Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
               Class<?> packetPlayerChat = Class.forName("net.minecraft.server." + version + ".PacketPlayOutChat");
               Class<?> packet = Class.forName("net.minecraft.server." + version + ".Packet");
               Class<?> ichatcomp = Class.forName("net.minecraft.server." + version + ".ChatComponentText");
               Class<?> ichatbasecomp = Class.forName("net.minecraft.server." + version + ".IChatBaseComponent");
               Object craftPlayerCast = craftPlayer.cast(player);
               Object actionConstructor;
               if (!version.equalsIgnoreCase("v1_8_R1") && !version.contains("v1_7_")) {
                  Object actionpacket = ichatcomp.getConstructor(String.class).newInstance(text);
                  actionConstructor = packetPlayerChat.getConstructor(ichatbasecomp, Byte.TYPE).newInstance(actionpacket, 2);
                  Object handle = craftPlayer.getDeclaredMethod("getHandle").invoke(craftPlayerCast);
                  Field playerConnection = handle.getClass().getDeclaredField("playerConnection");
                  Object getHandle = playerConnection.get(handle);
                  getHandle.getClass().getDeclaredMethod("sendPacket", packet).invoke(getHandle, actionConstructor);
               } else {
                  actionConstructor = ichatbasecomp.cast(ichatcomp.getDeclaredMethod("a", String.class).invoke(ichatcomp, "{\"text\": \"" + text + "\"}"));
                  Object getHandle = craftPlayer.getDeclaredMethod("getHandle").invoke(craftPlayerCast);
                  Object playerConnection = getHandle.getClass().getDeclaredField("playerConnection").get(getHandle);
                  playerConnection.getClass().getDeclaredMethod("sendPacket", packet).invoke(playerConnection, packetPlayerChat.getConstructor(ichatbasecomp, Byte.TYPE).newInstance(actionConstructor, 2));
               }
            } else {
               new LegacyPreAction(player, text);
            }
         } else {
            new PreAction(player, text);
         }
      }
      catch (Exception e) {
         e.printStackTrace();
      }

   }

}
