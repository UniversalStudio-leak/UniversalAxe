package ru.universalstudio.universalaxe;

import org.bukkit.*;
import org.bukkit.event.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.event.block.*;
import org.bukkit.event.player.*;
import ru.universalstudio.universalaxe.utils.*;
import ru.universalstudio.universalaxe.events.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 * @Author default source code: WinLocker02 (Thank pasting wAxes -> UniversalAxe)
 */

public class AxeListener implements Listener {

    @EventHandler
    public void onHit(AxeHitEvent e) {
        if(e.getShooter() == null) return;

        if(e.getEntityAttached() != null) {
            if(e.getEntityAttached() instanceof LivingEntity) {

                LivingEntity len = (LivingEntity) e.getEntityAttached();
                len.damage(e.getAxe().getDamage());

                if(e.getAxe().getEffects() != null)
                    e.getAxe().getEffects().forEach(x -> len.addPotionEffect(x));

                Utils.sendMessage(e.getShooter(), Utils.getMessage("axe-hit").replace("%name%", len.getName()));
            }
        }
        else {
            if(e.getAxe().isDiminish())
                Utils.giveItem(e.getShooter(), e.getAxe().getItem());

            Utils.sendMessage(e.getShooter(), Utils.getMessage("axe-nohit"));
        }

        if(e.getAxe().getParticle() != null) {
            e.getLocation().getWorld().spawnParticle(e.getAxe().getParticle(), e.getLocation(), 5);
        }

        if(e.getAxe().getSound() != null) {
            e.getLocation().getWorld().playSound(e.getLocation(), e.getAxe().getSound(), 10, 1);
        }
    }


    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if(Utils.getStringList("disable-worlds").contains(e.getPlayer().getWorld().getName())) return;

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if(e.getHand() == EquipmentSlot.OFF_HAND) return;

            Axe axe = AxesPlugin.instance().getAxesManager().getAxe(e.getItem());

            if(axe != null) {

                if(axe.isDiminish()) {
                    ItemStack item = e.getItem();
                    item.setAmount(item.getAmount() - 1);

                    Player p = e.getPlayer();
                    PlayerInventory inventory = p.getInventory();

                    inventory.setItem(inventory.getHeldItemSlot(), item);
                }

                Bukkit.getScheduler().runTask(AxesPlugin.instance(), () -> AxeAnimation.animation(e.getPlayer(), axe, 2));

                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onArmorStand(PlayerArmorStandManipulateEvent e) {
        if(e.getRightClicked().hasMetadata("uaxes-armorstand")) {
            e.setCancelled(true);
        }
    }
}