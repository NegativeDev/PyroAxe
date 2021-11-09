package dev.negativekb.pyroaxe.commands;

import dev.negativekb.pyroaxe.PyroAxe;
import dev.negativekb.pyroaxe.commands.subcommands.CommandGive;
import dev.negativekb.pyroaxe.commands.subcommands.CommandReload;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandPyroAxe implements CommandExecutor {

    private final ArrayList<PyroAxeCommand> subCommands = new ArrayList<>();

    public CommandPyroAxe(PyroAxe plugin) {
        subCommands.add(new CommandGive());
        subCommands.add(new CommandReload(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {
            runCommand(sender, args);
            return true;
        }

        // Gets argument 0
        String arg = args[0];
        // Removes argument 0
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);
        Optional<PyroAxeCommand> pyroAxeCommand = subCommands.stream()
                .filter(cmd -> (cmd.getName().equalsIgnoreCase(arg)))
                .findFirst();

        if (pyroAxeCommand.isPresent())
            runSubCommand(pyroAxeCommand.get(), sender, newArgs);
        else
            runCommand(sender, newArgs);
        return true;
    }

    private void runSubCommand(PyroAxeCommand command, CommandSender sender, String[] args) {
        Optional<String> permission = Optional.ofNullable(command.getPermission());
        if (permission.isPresent() && !(sender.hasPermission(permission.get())))
            return;

        command.onCommand(sender, args);
    }

    private void runCommand(CommandSender sender, String[] args) {
        List<String> help = new ArrayList<>();
        help.add("&7-------- &6PyroAxe &7--------");
        help.add("&e/pyroaxe give <player> <amount>");
        help.add("&e/pyroaxe reload");
        help.add("&7------------------------");

        help.forEach(s -> sender.sendMessage(ChatColor.translateAlternateColorCodes('&', s)));
    }
}
