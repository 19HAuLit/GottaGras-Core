package fr.gottagras.core.commands;

import fr.gottagras.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class invseeCommand implements CommandExecutor
{
    private Main main;
    public invseeCommand(Main main)
    {
        this.main = main;
    }

    private String help = "/invsee <player>";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (strings.length > 0)
            {
                for (Player playerInv : Bukkit.getOnlinePlayers())
                {
                    if (playerInv.getDisplayName().equalsIgnoreCase(strings[0]))
                    {
                        if (player.isOp())
                        {
                            player.openInventory(playerInv.getInventory());
                        }
                        else if (player.getGameMode() == GameMode.SPECTATOR)
                        {
                            player.openInventory(playerInv.getInventory());
                        }
                        else player.sendMessage(main.prefix + main.impossible);
                    }
                }
            }
            else commandSender.sendMessage(main.prefix + help);
        }
        else commandSender.sendMessage(main.prefix + main.impossible);
        return false;
    }
}
