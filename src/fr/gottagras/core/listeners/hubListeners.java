package fr.gottagras.core.listeners;

import fr.gottagras.core.Main;
import fr.gottagras.core.menus.hubMenu;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Weather;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherEvent;

public class hubListeners implements Listener
{
    private Main main;

    public hubListeners(Main main)
    {
        this.main = main;
    }

    // Events

    @EventHandler
    public void onWeather(WeatherChangeEvent event)
    {
        if (Bukkit.getWorld("world") == event.getWorld()) event.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (player.getWorld() == main.hub())
        {
            main.allClear(player);
            player.setGameMode(GameMode.SURVIVAL);
            player.teleport(main.hub_location());
            new hubMenu(main).setItem(player);
        }
    }

    @EventHandler
    public void onInteractInventory(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        if (player.getWorld() == main.hub() && player.getGameMode() == GameMode.SURVIVAL)
        {
            event.setCancelled(true);
            if (event.getCurrentItem() != null)
            {
                if (event.getCurrentItem().getType() != Material.AIR)
                {
                    String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
                    if (itemName.equals(new hubMenu(main).uhc().getItemMeta().getDisplayName()))
                    {
                        if (main.uhc_state.equals("new")) player.performCommand("uhc join");
                        else player.performCommand("uhc spec");
                    }
                    else if (itemName.equals(new hubMenu(main).uhc_settings().getItemMeta().getDisplayName()))
                    {
                        player.performCommand("uhc uhc");
                    }
                }
            }
        }
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
            if (event.getItem() != null)
            {
                String itemName = event.getItem().getItemMeta().getDisplayName();
                if (itemName.equals(new hubMenu(main).navigator().getItemMeta().getDisplayName()))
                {
                    player.openInventory(new hubMenu(main).menu());
                }
                else if (itemName.equals(new hubMenu(main).jump().getItemMeta().getDisplayName()))
                {
                    player.performCommand("jump");
                }
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
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player && event.getEntity().getWorld() == main.hub())
        {
            event.setCancelled(true);
        }
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
