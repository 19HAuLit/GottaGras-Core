package fr.gottagras.core.commands;

import fr.gottagras.core.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class uhcCommand implements CommandExecutor {
    private Main main;
    public uhcCommand(Main main)
    {
        this.main = main;
    }

    private String help = "\n- New: Permet de creer une nouvelle partie d'UHC\n- Join: Permet de rejoindre une partie d'UHC\n- Quit: Permet de quitter une partie d'UHC\n- Start: Permet de commencer une partie d'UHC\n- End: Permet de finir une partie d'UHC";
    private String help_new = "\n/uhc new <arg1> <arg2>\n- arg1: [True/False], génerer une nouvelle map UHC\n- arg2: [seed], si vous voulez une seed custom mettez-la ici";

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
                // CREATION DE LA PARTIE
                case "new":
                    if (strings.length > 1)
                    {
                        if (strings[1].equalsIgnoreCase("help"))
                        {
                            commandSender.sendMessage(main.prefix + help_new);
                            break;
                        }
                    }
                    if (player.isOp() && main.uhc_state.equals("end"))
                    {
                        // ANNONCE
                        Bukkit.broadcastMessage(main.prefix + "Un nouveau UHC viens d'etre creer ");
                        // CHANGEMENT DE L'ETAT DE JEU
                        main.uhc_state = "new";
                        // Modif de variable
                        main.uhc_join_players = new Player[9999];
                        // CREATION DES MAPS DE L'UHC + LOAD
                        WorldCreator worldCreatorUHC = new WorldCreator("uhc");
                        if (strings.length > 1)
                        {
                            if (strings[1].equalsIgnoreCase("false"))
                            {
                                worldCreatorUHC.createWorld();
                                player.teleport(new Location(Bukkit.getWorld("uhc"), 0, 100, 0));
                                player.setGameMode(GameMode.SPECTATOR);
                                break;
                            }
                        }
                        if (strings.length > 2)
                        {
                            if (strings[1].equalsIgnoreCase("true"))
                            {
                                long seed = Long.parseLong(strings[2]);
                                worldCreatorUHC.seed(seed);
                            }
                        }
                        File uhcFile = new File(System.getProperty("user.dir") + "\\uhc");
                        main.fileDelete(uhcFile);
                        worldCreatorUHC.environment(World.Environment.NORMAL);
                        worldCreatorUHC.createWorld();
                        player.teleport(new Location(Bukkit.getWorld("uhc"), 0, 100, 0));
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                // REJOINDRE LA PARTIE
                case "join":
                    if (main.uhc_state.equals("new"))
                    {
                        // VERIFICATION SI LE JOUEUR N'A PAS DEJA REJOIN
                        Boolean isIn = false;
                        for (Player join : main.uhc_join_players)
                        {
                            if (player == join) isIn = true;
                        }
                        if (isIn) commandSender.sendMessage(main.prefix + main.no_perm);
                        else
                        {
                            // AJOUT DU JOUEUR DANS LA LISTE
                            main.uhc_join_players[main.uhc_number_join] = player;
                            main.uhc_number_join++;
                            // ANNONCE
                            Bukkit.broadcastMessage(main.prefix + player.getDisplayName()  +" a rejoint l'UHC");
                        }
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                // QUITTER LA PARTIE
                case "quit":
                    if (main.uhc_state.equals("new"))
                    {
                        // VERIFIACTION SI LE JOUEUR A REJOIN LA PARTIE
                        Boolean isIn = false;
                        int player_number = 0;
                        int i = -1;
                        for (Player join : main.uhc_join_players)
                        {
                            i++;
                            if (player == join)
                            {
                                isIn = true;
                                player_number = i;
                            }
                        }
                        if (isIn)
                        {
                            main.uhc_join_players[player_number] = null;
                            // ANNONCE
                            Bukkit.broadcastMessage(main.prefix + player.getDisplayName() + " a quitte l'UHC");
                        }
                        else commandSender.sendMessage(main.prefix + main.no_perm);
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                // LANCER LA PARTIE
                case "start":
                    if (player.isOp() && main.uhc_state.equals("new"))
                    {
                        Bukkit.broadcastMessage(main.prefix + "L'UHC START !!!");
                        main.uhc_state = "start";
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                // FERMER LA PARTIE
                case "end":
                    if (player.isOp() && !main.uhc_state.equals("end"))
                    {
                        // ANNONCE
                        Bukkit.broadcastMessage(main.prefix + "Fin de l'UHC");
                        // CHANGEMENT DE L'ETAT
                        main.uhc_state = "end";
                        // UNLOAD
                        main.unLoadWord(Bukkit.getWorld("uhc"));
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                // HELP
                default:
                    commandSender.sendMessage(main.prefix + help);
                    break;
            }
        }
        else Bukkit.broadcastMessage(main.prefix + main.no_perm);
        return false;
    }
}
