package fr.gottagras.core;

import fr.gottagras.core.commands.hubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
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
        // Config
        saveDefaultConfig();
    }

    // Data
        // MSG
    public String prefix = getConfig().getString("msg.prefix");
    public String no_perm = getConfig().getString("msg.no-perm");
    public String teleport = getConfig().getString("msg.teleport");
        // HUB
    public World hub = Bukkit.getWorld(getConfig().getString("hub.world"));
    public double hub_x = getConfig().getDouble("hub.x");
    public double hub_y = getConfig().getDouble("hub.y");
    public double hub_z = getConfig().getDouble("hub.z");

    // METHODS

    public void allClear(Player player)
    {
        clearStuff(player);
        clearEffect(player);
        player.setHealthScale(20);
        player.setHealth(20);
        player.setSaturation(20);
        player.setFoodLevel(20);
        player.setLevel(20);
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
