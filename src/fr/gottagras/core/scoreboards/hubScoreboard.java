package fr.gottagras.core.scoreboards;

import fr.gottagras.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class hubScoreboard {
    private Main main;
    public hubScoreboard(Main main)
    {
        this.main = main;
    }

    public void setScoreBoard(Player player)
    {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("GottaGras-HUB", "ScoreBoard");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(main.prefix);
        Score info_1 = objective.getScore("§6Joueurs connectés: §7"+ Bukkit.getOnlinePlayers().size());
        info_1.setScore(1);
        Score info_2 = objective.getScore("§6Hub by §7Lylixn");
        info_2.setScore(2);
        Score info_3 = objective.getScore("§6IP: §719h.20h.fun");
        info_3.setScore(3);
        player.setScoreboard(board);
    }
}
