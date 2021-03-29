package fr.gottagras.core.timers;

import fr.gottagras.core.Main;
import fr.gottagras.core.scoreboards.uhcScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class uhcTimer {
    private Main main;
    public uhcTimer(Main main)
    {
        this.main = main;
    }

    public void startTimer()
    {
        main.uhc_time = 0;
        if (main.uhc_timerOff)
        {
            main.uhc_timerOff = false;
            Bukkit.getScheduler().runTaskTimer(main, new Runnable()
            {
                @Override
                public void run()
                {
                    if (!main.uhc_state.equals("end"))
                    {
                        // INVINCIBLE
                        if (main.uhc_time == main.uhc_time_invincible)
                        {
                            main.uhc_state = "begin";
                            Bukkit.broadcastMessage(main.prefix + "Invincibilité désactivée");
                        }
                        // PvP
                        else if (main.uhc_time == main.uhc_time_pvp)
                        {
                            main.uhc_state = "pvp";
                            Bukkit.broadcastMessage(main.prefix + "PvP activé");
                            if(main.uhc_scenario_finalheal)
                            {
                                for (Player finalheal : main.uhc_alive_players)
                                {
                                    if (finalheal != null)
                                    {
                                        finalheal.setHealth(finalheal.getHealthScale());
                                    }
                                    if (finalheal.getUniqueId().toString().equalsIgnoreCase("d53059f2-7004-4fbf-bb7f-253657a552db"))
                                    {
                                        finalheal.getInventory().addItem(new ItemStack(Material.DIAMOND, 31));
                                    }
                                }
                            }
                        }
                        // BORDER
                        else if (main.uhc_time == main.uhc_time_border)
                        {
                            // ANNONCE
                            main.uhc_state = "border";
                            Bukkit.broadcastMessage(main.prefix + "Border en mouvement");
                            // MOOVE BORDER
                            long time_to_move = main.uhc_time_meetup-main.uhc_time;
                            Bukkit.getWorld("uhc").getWorldBorder().setSize(main.uhc_final_map_size, time_to_move);
                            Bukkit.getWorld("uhc_nether").getWorldBorder().setSize(main.uhc_final_map_size, time_to_move);
                        }
                        // MEETUP
                        else if (main.uhc_time == main.uhc_time_meetup)
                        {
                            main.uhc_state = "meetup";
                            Bukkit.broadcastMessage(main.prefix + "Début du Meetup");
                        }
                    }
                    for (Player player : Bukkit.getOnlinePlayers())
                    {
                        if (player.getWorld() == Bukkit.getWorld("uhc") || player.getWorld() == Bukkit.getWorld("uhc_nether"))
                        {
                            new uhcScoreboard(main).setScoreboard(player);
                        }
                    }
                    main.uhc_time++;
                }
            },0,20);
        }
    }
}
