package ru.universalstudio.universalaxe;

import java.util.*;
import org.bukkit.*;
import org.bukkit.potion.*;
import org.bukkit.inventory.*;
import org.bukkit.configuration.file.*;
import ru.universalstudio.universalaxe.item.*;
import ru.universalstudio.universalaxe.utils.*;

/**
 * @Author source code: NaulbiMIX
 * @Author plugin code: UniversalStudio
 * @Author default source code: WinLocker02 (Thank pasting wAxes -> UniversalAxe)
 */

public class AxesManager {

    private List<Axe> axes = new ArrayList<>();
    private FileConfiguration data = Config.getFile("data.yml");

    public AxesManager() {
        for(String name : Utils.getConfig().getConfigurationSection("axes").getKeys(false)) {

            UUID uuid = null;

            if(data.getString("axes." + name) != null)
                uuid = UUID.fromString(data.getString("axes." + name));
            else uuid = UUID.randomUUID();

            ItemStack item = ItemBuilder.loadItemBuilder(Utils.getConfig(), "axes." + name + ".item").build();
            double damage = Utils.getDouble("axes." + name + ".damage");
            int time = Utils.getInt("axes." + name + ".time");

            Particle particle = null;

            if(Utils.getString("axes." + name + ".particle") != null)
                particle = Particle.valueOf(Utils.getString("axes." + name + ".particle").toUpperCase());

            Sound sound = null;

            if(Utils.getString("axes." + name + ".sound") != null)
                sound = Sound.valueOf(Utils.getString("axes." + name + ".sound").toUpperCase());

            boolean diminish = Utils.getBoolean("axes." + name + ".diminish");
            List<PotionEffect> effects = new ArrayList<>();

            if(Utils.getStringList("axes." + name + ".effects") != null) {

                for(String s : Utils.getStringList("axes." + name + ".effects")) {

                    PotionEffectType type = PotionEffectType.getByName(s.split(":")[0].toUpperCase());
                    int level = Integer.parseInt(s.split(":")[1]) - 1;
                    int duration = Integer.parseInt(s.split(":")[2]) * 20;

                    effects.add(new PotionEffect(type, duration, level));
                }
            }

            this.axes.add(new Axe(name, item, damage, time, particle, sound, diminish, effects, uuid));
        }
    }

    public void disableManager() {
        axes.forEach(x -> data.set("axes." + x.getName(), x.getUUID().toString()));
        Config.save(data, "data.yml");
    }

    public List<Axe> getAxes() {
        return axes;
    }

    public Axe getAxe(ItemStack item) {

        ItemNBTWrapper wrapper = new ItemNBTWrapper(item);
        if(!wrapper.hasKey("waxes-uuid")) return null; // Я был в ахуе с этого момента когда деобфнул и декомпильнул их плагин)

        UUID uuid = UUID.fromString(wrapper.getValue("waxes-uuid"));

        return getAxe(uuid);
    }

    public Axe getAxe(String name) {
        return this.axes.stream().filter(x -> x.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }

    public Axe getAxe(UUID uuid) {
        return this.axes.stream().filter(x -> x.getUUID().equals(uuid)).findAny().orElse(null);
    }
}
