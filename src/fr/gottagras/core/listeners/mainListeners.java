package fr.gottagras.core.listeners;

import fr.gottagras.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class mainListeners implements Listener {
    private Main main;
    public mainListeners(Main main)
    {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if (player.getGameMode() == GameMode.SPECTATOR || player.getGameMode() == GameMode.CREATIVE || player.getWorld() == main.hub())
        {
            player.setAllowFlight(true);
        }
        else
        {
            player.setAllowFlight(false);
            player.setFlying(false);
        }
        // ANTI-LAG SPEC
        if(player.getGameMode() == GameMode.SPECTATOR)
        {
            // PLAYER X et Z
            double x = player.getLocation().getX();
            double z = player.getLocation().getZ();
            // BORDER CO
            double borderXp = player.getWorld().getWorldBorder().getSize()/2 + player.getWorld().getWorldBorder().getCenter().getX();
            double borderZp = player.getWorld().getWorldBorder().getSize()/2 + player.getWorld().getWorldBorder().getCenter().getZ();
            double borderXn = 0 - player.getWorld().getWorldBorder().getSize()/2 + player.getWorld().getWorldBorder().getCenter().getX();
            double borderZn = 0 - player.getWorld().getWorldBorder().getSize()/2 + player.getWorld().getWorldBorder().getCenter().getZ();
            if (x > borderXp+32 || x < borderXn-32  || z > borderZp+32  || z < borderZn-32 )
            {
                player.teleport(new Location(player.getWorld(), player.getWorld().getWorldBorder().getCenter().getX(), 150, player.getWorld().getWorldBorder().getCenter().getZ()));
            }
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent event)
    {
        event.setMotd(main.getConfig().getString("msg.motd").replace("&", "§"));
        event.setMaxPlayers(-1);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (player.isOp()) event.setJoinMessage(main.prefix + main.getConfig().getString("msg.op-join").replace("<player>", player.getDisplayName()).replace("&", "§"));
        else event.setJoinMessage(main.prefix + main.getConfig().getString("msg.player-join").replace("<player>", player.getDisplayName()).replace("&", "§"));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();

        if (player.isOp()) event.setQuitMessage(main.prefix + main.getConfig().getString("msg.op-quit").replace("<player>", player.getDisplayName()).replace("&", "§"));
        else event.setQuitMessage(main.prefix + main.getConfig().getString("msg.player-quit").replace("<player>", player.getDisplayName()).replace("&", "§"));
    }
}
