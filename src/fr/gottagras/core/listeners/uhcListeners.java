package fr.gottagras.core.listeners;

import fr.gottagras.core.Main;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Date;

public class uhcListeners implements Listener {
    private Main main;
    public uhcListeners(Main main)
    {
        this.main = main;
    }

    private Player lastDamager = null;
    private Player lastPvPDamaged = null;
    Date date = null;

    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = ((Player) event.getEntity()).getPlayer();
            if (player.getWorld() == Bukkit.getWorld("uhc") || player.getWorld() == Bukkit.getWorld("uhc_nether"))
            {
                if (main.uhc_state.equalsIgnoreCase("start"))
                {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamageByEntity(EntityDamageByEntityEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = ((Player) event.getEntity()).getPlayer();
            if (player.getWorld() == Bukkit.getWorld("uhc") || player.getWorld() == Bukkit.getWorld("uhc_nether"))
            {
                if (main.uhc_state.equalsIgnoreCase("begin"))
                {
                    event.setCancelled(true);
                }
                else if (event.getDamager() instanceof Player)
                {
                    lastDamager = (Player) event.getDamager();
                    lastPvPDamaged = player;
                    date = new Date();
                }
                else if (event.getDamager() instanceof Projectile)
                {
                    Projectile projectile = (Projectile) event.getDamager();
                    if (projectile.getShooter() instanceof Player)
                    {
                        lastDamager = (Player) projectile.getShooter();
                        lastPvPDamaged = player;
                        date = new Date();
                    }
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        Player player = event.getEntity();
        if (player.getWorld() == Bukkit.getWorld("uhc") || player.getWorld() == Bukkit.getWorld("uhc_nether"))
        {
            event.setDeathMessage(null);
            // DROP GAPP
            main.allClear(player);
            Location location = player.getLocation();
            ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE, 1);
            location.getWorld().dropItemNaturally(location, gapple);
            // MESSAGE DE MORT
            if (lastPvPDamaged == player && new Date().getTime()-date.getTime() <= 10000) Bukkit.broadcastMessage(main.prefix + "§6" + lastDamager.getDisplayName() + "§7 viens de tuer §8" + player.getDisplayName());
            else Bukkit.broadcastMessage(main.prefix + "§8" + player.getDisplayName() + "§7 est encore mort du §6PvE");
            // GAMEMODE CHANGE
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(location);
            // SUPPRESSION DU JOUEUR DE LA GAME
            int i = 0;
            for (Player alive : main.uhc_alive_players)
            {
                if (alive == player)
                {
                    main.uhc_alive_players[i] = null;
                    main.uhc_number_alive--;
                }
                i++;
            }
            // CHECK WIN
            if (main.uhc_number_alive == 1)
            {
                for (Player alive : main.uhc_alive_players)
                {
                    if (alive != null)
                    {
                        Bukkit.broadcastMessage(main.prefix + alive.getDisplayName() + " §9a gagné l'UHC, GG!");
                    }
                }
            }
        }
    }
}
