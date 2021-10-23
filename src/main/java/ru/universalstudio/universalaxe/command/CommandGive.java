package ru.universalstudio.universalaxe.command;

import java.util.*;
import org.bukkit.*;
import java.util.stream.*;
import org.bukkit.entity.*;
import org.bukkit.command.*;
import ru.universalstudio.universalaxe.*;
import ru.universalstudio.universalaxe.utils.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 * @Author default source code: WinLocker02 (Thank pasting wAxes -> UniversalAxe)
 */

public class CommandGive implements CommandSub {

    @Override
    public boolean execute(CommandSender sender, String[] args) {

        if(args.length < 2) return false;

        Player player = Bukkit.getPlayer(args[0]);

        if(player == null) {

            Utils.sendMessage(sender, Utils.getMessage("player-null"));

            return true;
        }

        Axe axe = AxesPlugin.instance().getAxesManager().getAxe(args[1]);

        if(axe == null) {
            Utils.sendMessage(sender, Utils.getMessage("axe-null"));

            return true;
        }


        Utils.giveItem(player, axe.getItem());

        Utils.sendMessage(sender, Utils.getMessage("give.gived"));
        Utils.sendMessage(player, Utils.getMessage("give.player-gived").replace("%name%", axe.getDisplayName()));

        return true;
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {

        if(args.length == 2)
            return AxesPlugin.instance().getAxesManager().getAxes()
                    .stream().map(x -> x.getName()).collect(Collectors.toList());

        return null;
    }

    @Override
    public String command() {
        return "give";
    }

    @Override
    public String permission() {
        return "give";
    }

    @Override
    public String description() {
        return Utils.getMessage("give.usage");
    }

    @Override
    public boolean onlyPlayers() {
        return false;
    }
}
