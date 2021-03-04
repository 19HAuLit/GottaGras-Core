package fr.gottagras.core.timers;

import fr.gottagras.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
                        if (main.uhc_time == main.uhc_time_invincible)
                        {
                            main.uhc_state = "begin";
                            Bukkit.broadcastMessage(main.prefix + "Invincibilité désactivée");
                        }
                        else if (main.uhc_time == main.uhc_time_pvp)
                        {
                            main.uhc_state = "pvp";
                            Bukkit.broadcastMessage(main.prefix + "PvP activé");
                        }
                        else if (main.uhc_time == main.uhc_time_border)
                        {
                            main.uhc_state = "border";
                            Bukkit.broadcastMessage(main.prefix + "Border en mouvement");
                        }
                        else if (main.uhc_time == main.uhc_time_meetup)
                        {
                            main.uhc_state = "meetup";
                            Bukkit.broadcastMessage(main.prefix + "Début du Meetup");
                        }
                    }
                    main.uhc_time++;
                }
            },0,20);
        }
    }
}
