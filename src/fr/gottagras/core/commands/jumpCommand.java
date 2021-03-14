package fr.gottagras.core.commands;

import fr.gottagras.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class jumpCommand implements CommandExecutor {
    private Main main;
    public jumpCommand(Main main)
    {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (commandSender instanceof Player)
        {
            Player player = (((Player) commandSender).getPlayer());
            player.teleport(main.jump_location());
            player.sendMessage(main.prefix + main.teleport);
        }
        else
        {
            commandSender.sendMessage(main.prefix + main.impossible);
        }
        return false;
    }
}
