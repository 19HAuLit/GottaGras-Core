package fr.gottagras.core.commands;

import fr.gottagras.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class alertCommand implements CommandExecutor
{
    private Main main;
    public alertCommand(Main main)
    {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (strings.length > 0)
        {
            StringBuilder msg = new StringBuilder();
            for (int i = 0; i < strings.length; i++)
            {
                msg.append(strings[i]+ " ");
            }
            Bukkit.broadcastMessage(main.prefix+msg.toString());
        }
        else
        {
            commandSender.sendMessage(main.prefix+"/alert [msg]");
        }
        return false;
    }
}
