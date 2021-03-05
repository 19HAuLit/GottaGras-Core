package fr.gottagras.core.listeners;

import fr.gottagras.core.Main;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class hubListeners implements Listener
{
    private Main main;

    public hubListeners(Main main)
    {
        this.main = main;
    }

    // Events
    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (player.getWorld() == main.hub())
        {
            main.allClear(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(main.hub_location());
        }
        return;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (player.getWorld() == main.hub() && player.getGameMode() == GameMode.SURVIVAL)
        {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK)
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        Player player = event.getPlayer();
        if (player.getWorld() == main.hub() && player.getGameMode() == GameMode.SURVIVAL)
        {
            event.setCancelled(true);
            player.sendMessage(main.prefix + main.impossible);
        }
        return;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if (player.getWorld() == main.hub())
        {
            player.setSaturation(20);
            player.setFoodLevel(20);
        }
        return;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player && event.getEntity().getWorld() == main.hub())
        {
            event.setCancelled(true);
        }
        return;
    }

    @EventHandler
    public void Painting(PaintingBreakByEntityEvent event)
    {
        if (event.getRemover() instanceof Player)
        {
            Player player = (Player) event.getRemover();
            if (player.getWorld() == main.hub() && player.getGameMode() == GameMode.SURVIVAL)
            {
                event.setCancelled(true);
            }
        }
        else
        {
            event.setCancelled(true);
        }
    }
}
