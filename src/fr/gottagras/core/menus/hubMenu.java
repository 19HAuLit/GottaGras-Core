package fr.gottagras.core.menus;

import fr.gottagras.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class hubMenu {
    private Main main;
    public hubMenu(Main main)
    {
        this.main = main;
    }

    public Inventory menu()
    {
        Inventory inventory = Bukkit.createInventory(null, 18, "§4Menu");
        inventory.setItem(4, uhc());
        inventory.setItem(13, uhc_settings());
        return inventory;
    }

    public ItemStack navigator()
    {
        ItemStack itemStack = new ItemStack(Material.WATCH);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§4Menu");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack uhc()
    {
        ItemStack itemStack = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§6Join UHC");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack uhc_settings()
    {
        ItemStack itemStack = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§7UHC SETTINGS");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
