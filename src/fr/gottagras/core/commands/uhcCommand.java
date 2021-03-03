package fr.gottagras.core.commands;

import fr.gottagras.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class uhcCommand implements CommandExecutor {
    private Main main;
    public uhcCommand(Main main)
    {
        this.main = main;
    }

    private String help = "\n- New: Permet de creer une nouvelle partie d'UHC\n- Join: Permet de rejoindre une partie d'UHC\n- Quit: Permet de quitter une partie d'UHC\n- Start: Permet de commencer une partie d'UHC\n- End: Permet de finir une partie d'UHC";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (strings.length == 0)
        {
            commandSender.sendMessage(main.prefix + help);
        }
        else if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            switch (strings[0].toLowerCase())
            {
                case "new":
                    if (player.isOp() && main.uhc_state == "end")
                    {
                        Bukkit.broadcastMessage(main.prefix + "Un nouveau UHC viens d'etre creer ");
                        main.uhc_state = "new";
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                case "join":
                    if (main.uhc_state == "new")
                    {
                        Bukkit.broadcastMessage(main.prefix + player.getDisplayName()  +" a rejoint l'UHC");
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                case "quit":
                    if (main.uhc_state != "end")
                    {
                        Bukkit.broadcastMessage(main.prefix + player.getDisplayName() + " a quitte l'UHC");
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                case "start":
                    if (player.isOp() && main.uhc_state == "new")
                    {
                        Bukkit.broadcastMessage(main.prefix + "L'UHC START !!!");
                        main.uhc_state = "start";
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                case "end":
                    if (player.isOp() && main.uhc_state != "end")
                    {
                        Bukkit.broadcastMessage(main.prefix + "Fin de l'UHC");
                        main.uhc_state = "end";
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                default:
                    commandSender.sendMessage(main.prefix + help);
                    break;
            }
        }
        else Bukkit.broadcastMessage(main.prefix + main.no_perm);
        return false;
    }
}
