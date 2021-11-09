package dev.negativekb.pyroaxe.structure;

import lombok.Data;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Data
public class PyroAxeItem {

    private final String name;
    private final Material material;
    private final List<String> lore;
    private final HashMap<Sound, Float> sounds;
    private final HashMap<Enchantment, Integer> enchantments;
    private final double damageMultiplier;

    public ItemStack toItem() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        List<String> metaLore = new ArrayList<>();
        lore.forEach(s -> metaLore.add(ChatColor.translateAlternateColorCodes('&', s)));

        meta.setLore(metaLore);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        enchantments.forEach((enchantment, integer) -> meta.addEnchant(enchantment, integer, true));

        itemStack.setItemMeta(meta);
        return itemStack;
    }
}
