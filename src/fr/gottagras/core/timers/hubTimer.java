package fr.gottagras.core.timers;

import fr.gottagras.core.Main;
import fr.gottagras.core.scoreboards.hubScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class hubTimer {
    private Main main;
    public hubTimer(Main main)
    {
        this.main = main;
    }

    public void startTimer()
    {
        main.getServer().getScheduler().runTaskTimer(main, new Runnable()
        {
            @Override
            public void run()
            {
                for (Player player : Bukkit.getOnlinePlayers())
                {
                    if (player.getWorld() == Bukkit.getWorld("world"))
                    {
                        new hubScoreboard(main).setScoreBoard(player);
                    }
                }
            }
        },0,20);
    }
}
