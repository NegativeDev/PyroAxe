package dev.negativekb.pyroaxe.commands;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

public abstract class PyroAxeCommand {

    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String permission;

    public abstract void onCommand(CommandSender sender, String[] args);
}
