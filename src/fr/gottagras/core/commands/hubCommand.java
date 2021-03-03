package fr.gottagras.core.commands;

import fr.gottagras.core.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class hubCommand implements CommandExecutor {

    private Main main;

    public hubCommand(Main main)
    {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (player.getWorld() == main.hub())
            {
                player.teleport(main.hub_location());
                main.allClear(player);
                player.sendMessage(main.prefix + main.teleport);
            }
            else
            {
                commandSender.sendMessage(main.prefix + main.no_perm);
            }
        }
        else
        {
            commandSender.sendMessage(main.prefix + main.no_perm);
        }
        return false;
    }
}
