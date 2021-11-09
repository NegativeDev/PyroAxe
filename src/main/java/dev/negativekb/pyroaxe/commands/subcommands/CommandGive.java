package dev.negativekb.pyroaxe.commands.subcommands;

import dev.negativekb.pyroaxe.PyroAxe;
import dev.negativekb.pyroaxe.commands.PyroAxeCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class CommandGive extends PyroAxeCommand {

    private final String invalidUsage;
    private final String useValidNumber;
    private final String invalidPlayer;
    private final String success;

    public CommandGive() {
        setName("give");
        setPermission("pyroaxe.give");

        invalidUsage = "&cInvalid usage. Please do &7/pyroaxe give <player> <amount>";
        useValidNumber = "&cInvalid number. Please use a valid number!";
        invalidPlayer = "&cInvalid player. The player that you have requested is offline.";
        success = "&aSuccessfully given &e%amount% &aPyro-Axes to &e%target%&a!";
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', invalidUsage));
            return;
        }

        Optional<Player> target = getPlayer(args[0]);
        if (!target.isPresent()) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', invalidPlayer));
            return;
        }

        int amount = 1;
        if (args.length >= 2) {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (Exception e) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', useValidNumber));
                return;
            }
        }

        ItemStack item = PyroAxe.getInstance().getItem().toItem();
        for (int i = 0; i < amount; i++) {
            target.get().getInventory().addItem(item);
        }

        String successMsg = success;
        successMsg = successMsg.replaceAll("%target%", target.get().getName())
                .replace("%amount%", String.valueOf(amount));

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', successMsg));
    }

    public Optional<Player> getPlayer(String name) {
        return Optional.ofNullable(Bukkit.getPlayer(name));
    }
}
