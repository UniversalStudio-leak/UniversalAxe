package ru.universalstudio.universalaxe.command;

import java.util.*;
import org.bukkit.command.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 * @Author default source code: WinLocker02 (Thank pasting wAxes -> UniversalAxe)
 */

public interface CommandSub {

    boolean execute(CommandSender sender, String[] args);
    List<String> tab(CommandSender sender, String[] args);

    String command();
    String permission();
    String description(); // я бы здесь тыкнул аррей лист если бы кор был самописный
    boolean onlyPlayers();
}
