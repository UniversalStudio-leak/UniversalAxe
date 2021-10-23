package ru.universalstudio.universalaxe.events;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import ru.universalstudio.universalaxe.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 * @Author default source code: WinLocker02 (Thank pasting wAxes -> UniversalAxe)
 */

public class AxeHitEvent extends Event {

    private Player shooter;
    private Location location;
    private Entity entity;
    private Block block;
    private Axe axe;
    private ArmorStand armorStand;

    public AxeHitEvent(ArmorStand armorStand, Axe axe, Player shooter, Location location, Entity entity, Block block) {
        this.axe = axe;
        this.shooter = shooter;
        this.location = location;
        this.entity = entity;
        this.block = block;
        this.armorStand = armorStand;
    }

    public Player getShooter() {
        return shooter;
    }

    public Location getLocation() {
        return location;
    }

    public Entity getEntityAttached() {
        return entity;
    }

    public Block getBlockAttached() {
        return block;
    }

    public Axe getAxe() {
        return axe;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
    }

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}