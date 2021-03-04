package fr.gottagras.core.listeners;

import fr.gottagras.core.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;

public class mainListeners implements Listener {
    private Main main;
    public mainListeners(Main main)
    {
        this.main = main;
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
