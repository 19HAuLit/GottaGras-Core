package fr.gottagras.core;

import fr.gottagras.core.commands.hubCommand;
import fr.gottagras.core.commands.invseeCommand;
import fr.gottagras.core.commands.uhcCommand;
import fr.gottagras.core.listeners.hubListeners;
import fr.gottagras.core.listeners.mainListeners;
import fr.gottagras.core.listeners.uhcListeners;
import fr.gottagras.core.scoreboards.hubScoreboard;
import fr.gottagras.core.timers.hubTimer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.nio.file.Files;
import java.util.Collection;

public class Main extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        // Commands
        getCommand("hub").setExecutor(new hubCommand(this));
        getCommand("uhc").setExecutor(new uhcCommand(this));
        getCommand("invsee").setExecutor(new invseeCommand(this));
        // Listeners
        getServer().getPluginManager().registerEvents(new mainListeners(this), this);
        getServer().getPluginManager().registerEvents(new hubListeners(this), this);
        getServer().getPluginManager().registerEvents(new uhcListeners(this), this);
        // GameRule hub
        hub().setAnimalSpawnLimit(0);
        hub().setMonsterSpawnLimit(0);
        hub().setSpawnLocation(0,0,0);
        hub().setStorm(false);
        // Config
        saveDefaultConfig();
        // SCOREBOARD
        new hubTimer(this).startTimer();
    }

    @Override
    public void onDisable()
    {
        unLoadWord(Bukkit.getWorld("uhc"));
        unLoadWord(Bukkit.getWorld("uhc_nether"));
    }

    // Data
        // UHC
    public String uhc_state = "end";
    public Player[] uhc_join_players;
    public int uhc_number_join = 0;
    public int uhc_time = 0;
    public Boolean uhc_timerOff = true;
    public Player[] uhc_alive_players;
    public int uhc_number_alive = 0;
    public int uhc_initial_map_size()
    {
        return uhc_number_join*150;
    }
    public int uhc_final_map_size = 100;
    public int uhc_number_revive = 9999;
        // UHC.TIME
    public int uhc_time_invincible = getConfig().getInt("uhc.time.invincible");
    public int uhc_time_pvp = getConfig().getInt("uhc.time.pvp");
    public int uhc_time_border = getConfig().getInt("uhc.time.border");
    public int uhc_time_meetup = getConfig().getInt("uhc.time.meetup");
        // UHC.SCENARIO
    public Boolean uhc_scenario_nether = getConfig().getBoolean("uhc.scenario.nether");
    public Boolean uhc_scenario_finalheal = getConfig().getBoolean("uhc.scenario.final-heal");
    public Boolean uhc_scenario_alltreedrop = getConfig().getBoolean("uhc.scenario.alltreedrop");
    public int uhc_scenario_alltreedrop_rate = getConfig().getInt("uhc.scenario.alltreedrop-rate");
    public Boolean uhc_scenario_fireless = getConfig().getBoolean("uhc.scenario.fireless");
    public Boolean uhc_scenario_cutclean = getConfig().getBoolean("uhc.scenario.cutclean");
    public Boolean uhc_scenario_hasteyboys = getConfig().getBoolean("uhc.scenario.hasteyboys");
        // MSG
    public String prefix = getConfig().getString("msg.prefix").replace("&", "§");
    public String no_perm = getConfig().getString("msg.no-perm").replace("&", "§");
    public String teleport = getConfig().getString("msg.teleport").replace("&", "§");
    public String impossible = getConfig().getString("msg.impossible").replace("&", "§");
    public String revive = getConfig().getString("msg.revive").replace("&", "§");
        // HUB
    public World hub ()
    {
        return Bukkit.getWorld(getConfig().getString("hub.world"));
    }
    public double hub_x = getConfig().getDouble("hub.x");
    public double hub_y = getConfig().getDouble("hub.y");
    public double hub_z = getConfig().getDouble("hub.z");
    public Location hub_location ()
    {
        return new Location(hub(), hub_x, hub_y, hub_z);
    }

    // METHODS

    public void checkWinUhc()
    {
        if (uhc_number_alive == 1)
        {
            for (Player alive : uhc_alive_players)
            {
                if (alive != null)
                {
                    Bukkit.broadcastMessage(prefix + alive.getDisplayName() + " §9a gagné l'UHC, GG!");
                }
            }
        }
    }

    public void allClear(Player player)
    {
        clearStuff(player);
        clearEffect(player);
        player.setHealthScale(20);
        player.setHealth(20);
        player.setSaturation(20);
        player.setFoodLevel(20);
        player.setLevel(0);
        player.setExp(0);
        player.setGameMode(GameMode.SURVIVAL);
    }

    public void clearEffect(Player player)
    {
        Collection<PotionEffect> effect = player.getActivePotionEffects();
        for (PotionEffect potionEffect : effect)
        {
            PotionEffectType potionType = potionEffect.getType();
            player.removePotionEffect(potionType);
        }
    }

    public void clearStuff(Player player)
    {
        player.getInventory().clear();
        ItemStack air = new ItemStack(Material.AIR);
        player.getInventory().setHelmet(air);
        player.getInventory().setChestplate(air);
        player.getInventory().setLeggings(air);
        player.getInventory().setBoots(air);
    }

    public void unLoadWord(World world)
    {
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if (player.getWorld() == world)
            {
                allClear(player);
                player.teleport(hub_location());
                player.sendMessage(prefix + teleport);
            }
        }
        Bukkit.unloadWorld(world, true);
        return;
    }

    public void fileDelete(File file)
    {
        File[] contents = file.listFiles();
        if (contents != null)
        {
            for (File f : contents)
            {
                if (! Files.isSymbolicLink(f.toPath()))
                {
                    fileDelete(f);
                }
            }
        }
        file.delete();
        return;
    }
}
