package dev.negativekb.pyroaxe;

import dev.negativekb.pyroaxe.commands.CommandPyroAxe;
import dev.negativekb.pyroaxe.listener.PlayerDamageListener;
import dev.negativekb.pyroaxe.structure.PyroAxeItem;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

public final class PyroAxe extends JavaPlugin {

    @Getter
    private static PyroAxe instance;
    @Getter
    private PyroAxeItem item;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        loadItem();

        getCommand("pyroaxe").setExecutor(new CommandPyroAxe(this));
        Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(this), this);
    }

    public void loadItem() {
        ConfigurationSection item = getConfig().getConfigurationSection("item");
        if (item == null) {
            System.out.println("[PyroAxe] Invalid configuration! Please reset your config.yml file.");
            return;
        }

        String name = item.getString("name");
        String stringMaterial = item.getString("material");
        Material material = Material.valueOf(stringMaterial);
        List<String> lore = item.getStringList("lore");

        ConfigurationSection enchantsSection = item.getConfigurationSection("enchants");
        if (enchantsSection == null) {
            System.out.println("[PyroAxe] Invalid Enchants section in the PyroAxe configuration section.");
            return;
        }

        HashMap<Enchantment, Integer> enchantments = new HashMap<>();
        enchantsSection.getKeys(false).forEach(s -> {
            Enchantment byName = Enchantment.getByName(s);
            enchantments.put(byName, enchantsSection.getInt(s, 1));
        });

        ConfigurationSection soundsSection = item.getConfigurationSection("sounds");
        if (soundsSection == null) {
            System.out.println("[PyroAxe] Invalid Sounds section in the PyroAxe configuration section.");
            return;
        }

        HashMap<Sound, Float> sounds = new HashMap<>();
        soundsSection.getKeys(false).forEach(s -> {
            Sound sound = Sound.valueOf(s);
            sounds.put(sound, (float) soundsSection.getDouble(s, 1D));
        });

        double multiplier = item.getDouble("damage-multiplier");

        this.item = new PyroAxeItem(name, material, lore, sounds, enchantments, multiplier);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
