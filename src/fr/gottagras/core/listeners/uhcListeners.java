package fr.gottagras.core.listeners;

import fr.gottagras.core.Main;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class uhcListeners implements Listener {
    private Main main;
    public uhcListeners(Main main)
    {
        this.main = main;
    }

    private Random random = new Random();

    private Player lastDamager = null;
    private Player lastPvPDamaged = null;
    private Date date = null;

    // METHODS

    public void cutclean(Block block, ItemStack drop, Player player, int xp)
    {
        block.setType(Material.AIR);
        Location location = new Location(block.getLocation().getWorld(), block.getX()+0.5, block.getY()+0.5, block.getZ()+0.5);
        location.getWorld().dropItem(location, drop);
        player.giveExp(xp);
    }

    // EVENTS

    @EventHandler
    public void onDrop(PlayerDropItemEvent event)
    {
        if (event.getItemDrop().getItemStack().getType() == Material.RAW_BEEF)
        {
            event.getItemDrop().getItemStack().setType(Material.COOKED_BEEF);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material material = block.getType();
        if (player.getWorld() == Bukkit.getWorld("uhc") || player.getWorld() == Bukkit.getWorld("uhc_nether"))
        {
            if (main.uhc_scenario_cutclean)
            {
                if (material == Material.GOLD_ORE)
                {
                    event.setCancelled(true);
                    cutclean(block, new ItemStack(Material.GOLD_INGOT), player, 3);
                }
                else if (material == Material.IRON_ORE)
                {
                    event.setCancelled(true);
                    cutclean(block, new ItemStack(Material.IRON_INGOT), player, 1);
                }
                else if (material == Material.GRAVEL)
                {
                    event.setCancelled(true);
                    cutclean(block, new ItemStack(Material.FLINT), player, 0);
                }
            }
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();
        if (player.getWorld() == Bukkit.getWorld("uhc") || player.getWorld() == Bukkit.getWorld("uhc_nether"))
        {
            for (ItemStack itemStack : event.getInventory().getContents())
            {
                if (main.uhc_scenario_hasteyboys)
                {
                    if (itemStack.getType() == Material.WOOD_PICKAXE || itemStack.getType() == Material.WOOD_AXE || itemStack.getType() == Material.STONE_PICKAXE || itemStack.getType() == Material.STONE_AXE || itemStack.getType() == Material.GOLD_PICKAXE || itemStack.getType() == Material.GOLD_AXE || itemStack.getType() == Material.IRON_PICKAXE || itemStack.getType() == Material.IRON_AXE || itemStack.getType() == Material.DIAMOND_PICKAXE || itemStack.getType() == Material.DIAMOND_AXE)
                    {
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
                        itemMeta.spigot().setUnbreakable(true);
                        itemStack.setItemMeta(itemMeta);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        if (player.getWorld() == Bukkit.getWorld("uhc") || player.getWorld() == Bukkit.getWorld("uhc_nether"))
        {
            boolean isOut = true;
            for (Player joinPlayer : main.uhc_alive_players)
            {
                if (joinPlayer == player)
                {
                    main.uhc_number_alive++;
                    isOut = false;
                }
            }
            if (isOut) player.setGameMode(GameMode.SPECTATOR);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        if (player.getWorld() == Bukkit.getWorld("uhc") || player.getWorld() == Bukkit.getWorld("uhc_nether"))
        {
            for (Player joinPlayer : main.uhc_alive_players)
            {
                if (joinPlayer == player)
                {
                    main.uhc_number_alive--;
                }
            }
        }

    }

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
                if (main.uhc_scenario_fireless)
                {
                    if (event.getCause() == EntityDamageEvent.DamageCause.FIRE || event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK || event.getCause() == EntityDamageEvent.DamageCause.LAVA)
                    {
                        event.setCancelled(true);
                    }
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
    public void onLeavesDrop(LeavesDecayEvent event)
    {
        Block block = event.getBlock();
        Location location = new Location(block.getWorld(), block.getX()+0.5, block.getY()+0.5, block.getZ()+0.5);
        if (main.uhc_scenario_alltreedrop)
        {
            if (location.getWorld() == Bukkit.getWorld("uhc") || location.getWorld() == Bukkit.getWorld("uhc_nether"))
            {
                if (block.getType() == Material.LEAVES)
                {
                    double rdmDrop = random.nextDouble() * 100;
                    if (rdmDrop <= main.uhc_scenario_alltreedrop_rate)
                    {
                        ItemStack drop = new ItemStack(Material.APPLE);
                        location.getWorld().dropItem(location, drop);
                    }
                }
            }
        }
    }

    @EventHandler
    public void Portal(PlayerPortalEvent event)
    {
        Player player = event.getPlayer();
        World nether = Bukkit.getWorld("uhc_nether");
        World uhc = Bukkit.getWorld("uhc");
        if (main.uhc_scenario_nether)
        {
            if (player.getWorld() == nether || player.getWorld() == uhc)
            {
                World world = nether;
                if (player.getWorld() == nether) world = uhc;
                int x = player.getLocation().getBlockX()/2;
                int z = player.getLocation().getBlockZ()/2;
                Location location = new Location(world, x, player.getLocation().getBlockY(), z);
                event.useTravelAgent(true);
                event.getPortalTravelAgent().setCanCreatePortal(true);
                Location portalLoc = event.getPortalTravelAgent().findOrCreate(location);
                Location loc = new Location(portalLoc.getWorld(), portalLoc.getBlockX()+0.5, portalLoc.getBlockY(), portalLoc.getBlockZ()+0.5);
                player.teleport(loc);
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
            main.checkWinUhc();
        }
    }
}
