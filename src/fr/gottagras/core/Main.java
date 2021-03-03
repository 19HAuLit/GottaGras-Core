package fr.gottagras.core;

import fr.gottagras.core.commands.hubCommand;
import fr.gottagras.core.commands.uhcCommand;
import fr.gottagras.core.listeners.hubListeners;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class Main extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        // Commands
        getCommand("hub").setExecutor(new hubCommand(this));
        getCommand("uhc").setExecutor(new uhcCommand(this));
        // Listeners
        getServer().getPluginManager().registerEvents(new hubListeners(this), this);
        // GameRule hub
        hub().setAnimalSpawnLimit(0);
        hub().setMonsterSpawnLimit(0);
        hub().setSpawnLocation(0,0,0);
        hub().setStorm(false);
        // Config
        saveDefaultConfig();
    }

    // Data
        // UHC
    public String uhc_state = "end";
        // MSG
    public String prefix = getConfig().getString("msg.prefix").replace("&", "§");
    public String no_perm = getConfig().getString("msg.no-perm").replace("&", "§");
    public String teleport = getConfig().getString("msg.teleport").replace("&", "§");
    public String impossible = getConfig().getString("msg.impossible").replace("&", "§");
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

    public void allClear(Player player)
    {
        clearStuff(player);
        clearEffect(player);
        player.setHealthScale(20);
        player.setHealth(20);
        player.setSaturation(20);
        player.setFoodLevel(20);
        player.setLevel(0);
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
}
