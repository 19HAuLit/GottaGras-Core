package fr.gottagras.core.scoreboards;

import fr.gottagras.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class uhcScoreboard {
    private Main main;
    public uhcScoreboard(Main main)
    {
        this.main = main;
    }

    public void setScoreboard(Player player)
    {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        player.setScoreboard(infos(board));
        player.setScoreboard(health(board));
    }

    public Scoreboard health(Scoreboard board)
    {
        Objective objective = board.registerNewObjective("Health", "health");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName("HP");

        for(Player online : Bukkit.getOnlinePlayers()){
            Score score = objective.getScore(online);
            score.setScore((int)online.getHealth());
        }
        return board;
    }


    public Scoreboard infos(Scoreboard board)
    {
        Objective objective = board.registerNewObjective("GottaGras-UHC", "ScoreBoard");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(main.prefix);
        Score info_1 = objective.getScore("§6Joueurs en vie§7: "+main.uhc_number_alive);
        info_1.setScore(1);
        Score info_2 = null;
        if (main.uhc_time_pvp-main.uhc_time > 0) info_2 = objective.getScore("§6Temps avant le PvP: §7" + (main.uhc_time_pvp-main.uhc_time)/60 + " min " + (main.uhc_time_pvp-main.uhc_time)%60 + "s");
        else info_2 = objective.getScore("§6PvP Actif");
        info_2.setScore(2);
        Score info_3 = objective.getScore("§6Taille de la map: §7" + (int)Bukkit.getWorld("uhc").getWorldBorder().getSize());
        info_3.setScore(3);
        Score info_4 = null;
        if (main.uhc_time_border-main.uhc_time > 0) info_4 = objective.getScore("§6Border dans: §7" + (main.uhc_time_border-main.uhc_time)/60 + " min " + (main.uhc_time_border-main.uhc_time)%60 + "s");
        else if (main.uhc_time_meetup-main.uhc_time > 0) info_4 = objective.getScore("§6Meetup dans: §7" + (main.uhc_time_meetup-main.uhc_time)/60 + " min " + (main.uhc_time_meetup-main.uhc_time)%60 + "s");
        else info_4 = objective.getScore("§7Tapez-vous !!!");
        info_4.setScore(4);
        Score info_5 = objective.getScore("§6Temps§7: " + main.uhc_time/60 + " min " + main.uhc_time%60 + "s");
        info_5.setScore(5);
        return board;
    }
}
