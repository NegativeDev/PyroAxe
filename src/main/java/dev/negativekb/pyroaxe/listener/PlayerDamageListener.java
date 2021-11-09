package dev.negativekb.pyroaxe.listener;

import dev.negativekb.pyroaxe.PyroAxe;
import dev.negativekb.pyroaxe.structure.PyroAxeItem;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

@RequiredArgsConstructor
public class PlayerDamageListener implements Listener {

    private final PyroAxe plugin;

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!((event.getDamager()) instanceof Player))
            return;

        PyroAxeItem item = plugin.getItem();

        Player player = (Player) event.getDamager();
        ItemStack itemInHand = player.getItemInHand();
        ItemMeta itemMeta = itemInHand.getItemMeta();
        boolean hasItemFlag = itemMeta.hasItemFlag(ItemFlag.HIDE_POTION_EFFECTS);
        boolean hasMaterial = itemInHand.getType().equals(item.getMaterial());
        boolean hasName = (itemMeta.getDisplayName()
                .contains(ChatColor.translateAlternateColorCodes('&', item.getName())));
        if (hasName && hasMaterial && hasItemFlag) {
            HashMap<Sound, Float> sounds = item.getSounds();
            World world = player.getWorld();
            sounds.forEach((sound, aFloat) -> world.playSound(event.getEntity().getLocation(), sound, 1, aFloat));

            double damageMultiplier = item.getDamageMultiplier();
            double damage = event.getDamage();
            damage *= (damageMultiplier + 1);
            event.setDamage(damage);
        }
    }
}
