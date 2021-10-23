package ru.universalstudio.universalaxe.utils.actionbar;

import java.util.*;
import org.bukkit.*;
import java.lang.reflect.*;
import org.bukkit.entity.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 * @Author default source code: WinLocker02 (Thank pasting wAxes -> UniversalAxe)
 */

public class PreAction {

   public PreAction(Player player, String text) {
      try {
         Object chat = Objects.requireNonNull(getNMS("ChatComponentText")).getConstructor(String.class).newInstance(text);
         Object chattype = Objects.requireNonNull(getNMS("ChatMessageType")).getField("GAME_INFO").get(null);
         Constructor<?> actionConstructor = Objects.requireNonNull(getNMS("PacketPlayOutChat")).getConstructor(getNMS("IChatBaseComponent"), getNMS("ChatMessageType"), Class.forName("java.util.UUID"));
         Object actionpacket = actionConstructor.newInstance(chat, chattype, player.getUniqueId());
         sendPacket(player, actionpacket);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static void sendPacket(Player player, Object packet) {
      try {
         Object handle = player.getClass().getMethod("getHandle", new Class[0]).invoke(player);
         Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
         playerConnection.getClass().getMethod("sendPacket", getNMS("Packet")).invoke(playerConnection, packet);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static Class<?> getNMS(String name) {
      String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
      try {
         return Class.forName("net.minecraft.server." + version + "." + name);
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      return null;
   }
}
