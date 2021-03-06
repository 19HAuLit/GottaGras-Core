package fr.gottagras.core.menus;

import fr.gottagras.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class uhcMenu implements Listener
{
    private Main main;
    public uhcMenu(Main main)
    {
        this.main = main;
    }

    public Inventory menu()
    {
        Inventory inventory = Bukkit.createInventory(null, 27, "§6UHC");
        inventory.setItem(0, mode());
        inventory.setItem(9, nether());
        inventory.setItem(10, finalheal());
        inventory.setItem(11, alltreedrop());
        inventory.setItem(12, fireless());
        inventory.setItem(13, cutclean());
        inventory.setItem(14, hasteyboys());
        inventory.setItem(26, game());
        return inventory;
    }

    @EventHandler
    public void onClickMenu(InventoryClickEvent event)
    {
        if (event.getInventory().getName().equals(menu().getName()))
        {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getCurrentItem() != null)
            {
                if (player.isOp() && event.getCurrentItem().getType() != Material.AIR)
                {
                    String itemName = event.getCurrentItem().getItemMeta().getDisplayName();
                    if (itemName.equals(mode().getItemMeta().getDisplayName()))
                    {
                        if (main.uhc_mode.equals("uhc"))
                        {
                            main.uhc_mode = "meetup";
                            main.uhc_time_invincible = 30;
                            main.uhc_time_pvp = 45;
                            main.uhc_time_border = 360;
                            main.uhc_time_meetup = 600;
                        }
                        else
                        {
                            main.uhc_mode = "uhc";
                            main.uhc_time_invincible = main.getConfig().getInt("uhc.time.invincible");
                            main.uhc_time_pvp = main.getConfig().getInt("uhc.time.pvp");
                            main.uhc_time_border = main.getConfig().getInt("uhc.time.border");
                            main.uhc_time_meetup = main.getConfig().getInt("uhc.time.meetup");
                        }
                    }
                    else if (itemName.equals(nether().getItemMeta().getDisplayName()))
                    {
                        main.uhc_scenario_nether = !main.uhc_scenario_nether;
                    }
                    else if (itemName.equals(finalheal().getItemMeta().getDisplayName()))
                    {
                        main.uhc_scenario_finalheal = !main.uhc_scenario_finalheal;
                    }
                    else if (itemName.equals(alltreedrop().getItemMeta().getDisplayName()))
                    {
                        main.uhc_scenario_alltreedrop = !main.uhc_scenario_alltreedrop;
                    }
                    else if (itemName.equals(fireless().getItemMeta().getDisplayName()))
                    {
                        main.uhc_scenario_fireless = !main.uhc_scenario_fireless;
                    }
                    else if (itemName.equals(cutclean().getItemMeta().getDisplayName()))
                    {
                        main.uhc_scenario_cutclean = !main.uhc_scenario_cutclean;
                    }
                    else if (itemName.equals(hasteyboys().getItemMeta().getDisplayName()))
                    {
                        main.uhc_scenario_hasteyboys = !main.uhc_scenario_hasteyboys;
                    }
                    else if (itemName.equals(game().getItemMeta().getDisplayName()))
                    {
                        if (main.uhc_state.equals("end")) player.performCommand("uhc new");
                        else if (main.uhc_state.equals("new")) player.performCommand("uhc start");
                        else player.performCommand("uhc end");
                    }
                }

                player.closeInventory();
                player.openInventory(menu());
            }
        }
    }

    public ItemStack mode()
    {
        ItemStack itemStack = new ItemStack(Material.IRON_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (main.uhc_mode.equals("uhc")) itemMeta.setDisplayName("§7Mode: §6UHC");
        else itemMeta.setDisplayName("§7Mode: §6Meetup");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack nether()
    {
        ItemStack itemStack = new ItemStack(Material.OBSIDIAN);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (main.uhc_scenario_nether) itemMeta.setDisplayName("§7Nether: §6ON");
        else itemMeta.setDisplayName("§7Nether: §6OFF");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack finalheal()
    {
        ItemStack itemStack = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (main.uhc_scenario_finalheal) itemMeta.setDisplayName("§7FinalHeal: §6ON");
        else itemMeta.setDisplayName("§7FinalHeal: §6OFF");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack alltreedrop()
    {
        ItemStack itemStack = new ItemStack(Material.SAPLING);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (main.uhc_scenario_alltreedrop) itemMeta.setDisplayName("§7AllTreeDrop: §6ON");
        else itemMeta.setDisplayName("§7AllTreeDrop: §6OFF");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack fireless()
    {
        ItemStack itemStack = new ItemStack(Material.FLINT_AND_STEEL);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (main.uhc_scenario_fireless) itemMeta.setDisplayName("§7FireLess: §6ON");
        else itemMeta.setDisplayName("§7FireLess: §6OFF");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack cutclean()
    {
        ItemStack itemStack = new ItemStack(Material.IRON_ORE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (main.uhc_scenario_cutclean) itemMeta.setDisplayName("§7CutClean: §6ON");
        else itemMeta.setDisplayName("§7CutClean: §6OFF");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack hasteyboys()
    {
        ItemStack itemStack = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (main.uhc_scenario_hasteyboys) itemMeta.setDisplayName("§7HasteyBoys: §6ON");
        else itemMeta.setDisplayName("§7HasteyBoys: §6OFF");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack game()
    {
        ItemStack itemStack = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (main.uhc_state.equals("end")) itemMeta.setDisplayName("§7Creer une game");
        else if (main.uhc_state.equals("new")) itemMeta.setDisplayName("§6Start la game");
        else itemMeta.setDisplayName("§4Stop la game");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
