package dev.negativekb.pyroaxe.commands.subcommands;

import dev.negativekb.pyroaxe.PyroAxe;
import dev.negativekb.pyroaxe.commands.PyroAxeCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandReload extends PyroAxeCommand {

    private final PyroAxe plugin;

    public CommandReload(PyroAxe plugin) {
        this.plugin = plugin;

        setName("reload");
        setPermission("pyroaxe.reload");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        plugin.reloadConfig();
        plugin.loadItem();

        sender.sendMessage(ChatColor.GREEN + "Successfully reloaded the configuration file.");
    }
}
