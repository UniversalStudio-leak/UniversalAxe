package ru.universalstudio.universalaxe;

import org.bukkit.*;
import org.bukkit.plugin.java.*;
import ru.universalstudio.universalaxe.utils.*;
import ru.universalstudio.universalaxe.command.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 * @Author default source code: WinLocker02 (Thank pasting wAxes -> UniversalAxe)
 */

public class AxesPlugin extends JavaPlugin {

    private static AxesPlugin instance;
    private CommandManager commandManager;
    private AxesManager axesManager;

    public static AxesPlugin instance() {
        return instance;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public AxesManager getAxesManager() {
        return axesManager;
    }

    /**
     * Auto-generation
     */
    @Override
    public void onEnable() {
        instance = this; // внимание: половина кода это официальные сурсы плагинов WinLocker02! Поэтому бить за пробелы и вынос страки не там меня низя
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[UniversalCode] Plugin recompiled by NaulbiMIX | Sponsored by FlatiCommunity (https://t.me/flaticommunity) | Specially publication for https://teletype.in/@naulbimix/rumine");

        getServer().getPluginManager().registerEvents(new AxeListener(), this);

        reloadConfig();

        commandManager = new CommandManager();
        commandManager.registerCommand(new CommandGive());
        commandManager.registerCommand(new CommandReload());

        getCommand("uaxe").setExecutor(commandManager);

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[UniversalAxe] The plugin started successfully! Website: u-studio.su");
    }

    /**
     * Auto-generation
     */
    @Override
    public void onDisable() {
        axesManager.disableManager();
        AxeAnimation.TASKS.forEach(x -> x.cancel());
    }

    @Override
    public void reloadConfig() {
        Utils.reloadConfig();
        axesManager = new AxesManager();
    }
}
