package ru.universalstudio.universalaxe.command;

import java.util.*;
import org.bukkit.command.*;
import ru.universalstudio.universalaxe.*;
import ru.universalstudio.universalaxe.utils.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 * @Author default source code: WinLocker02 (Thank pasting wAxes -> UniversalAxe)
 */

public class CommandReload implements CommandSub {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        AxesPlugin.instance().reloadConfig();
        Utils.sendMessage(sender, Utils.getMessage("reload.reloaded"));
        return true;
    }

    @Override
    public List<String> tab(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public String command() {
        return "reload";
    }

    @Override
    public String permission() {
        return "reload";
    }

    @Override
    public String description() {
        return Utils.getMessage("reload.usage");
    }

    @Override
    public boolean onlyPlayers() {
        return false;
    }

}
