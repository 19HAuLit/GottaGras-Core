package fr.gottagras.core.commands;

import fr.gottagras.core.Main;
import fr.gottagras.core.menus.uhcMenu;
import fr.gottagras.core.timers.uhcTimer;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.Random;

public class uhcCommand implements CommandExecutor {
    private Main main;
    public uhcCommand(Main main)
    {
        this.main = main;
    }

    private String help = "\n- New: Permet de creer une nouvelle partie d'UHC\n- Join: Permet de rejoindre une partie d'UHC\n- Quit: Permet de quitter une partie d'UHC\n- UHC: Permet de voir les paramètres de la partie\n- Start: Permet de commencer une partie d'UHC\n- Spec: Permet de regarder la partie en cours\n- Revive: Permet de faire respawn un joueur\n- End: Permet de finir une partie d'UHC";
    private String help_new = "\n/uhc new <arg1>\n- arg1: [Seed/False], seed: choisir la seed de la map | false: ne pas generer de map";
    private Random random = new Random();

    public void giveStuffUHC(Player player)
    {
        // PIOCHE
        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        pickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
        pickaxeMeta.spigot().setUnbreakable(true);
        pickaxe.setItemMeta(pickaxeMeta);
        // HACHE
        ItemStack axe = new ItemStack(Material.STONE_AXE);
        ItemMeta axeMeta = axe.getItemMeta();
        axeMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
        axeMeta.spigot().setUnbreakable(true);
        axe.setItemMeta(axeMeta);

        ItemStack beef = new ItemStack(Material.COOKED_BEEF, 64);
        ItemStack book = new ItemStack(Material.BOOK, 6);

        player.getInventory().addItem(pickaxe, axe, beef, book);
    }

    public void giveStuffUHCMeetup(Player player)
    {
        // SWORD
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordMeta = sword.getItemMeta();
        swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
        swordMeta.spigot().setUnbreakable(true);
        sword.setItemMeta(swordMeta);
        // BOW
        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
        bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 3, true);
        bowMeta.spigot().setUnbreakable(true);
        bow.setItemMeta(bowMeta);
        // BLOCK
        ItemStack block = new ItemStack(Material.COBBLESTONE, 64);
        // WATER
        ItemStack water = new ItemStack(Material.WATER_BUCKET);
        // PIOCHE
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta pickaxeMeta = pickaxe.getItemMeta();
        pickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 3, true);
        pickaxeMeta.spigot().setUnbreakable(true);
        pickaxe.setItemMeta(pickaxeMeta);
        // MIAM MIAM
        ItemStack beef = new ItemStack(Material.COOKED_BEEF, 64);
        // ROD
        ItemStack rod = new ItemStack(Material.FISHING_ROD);
        ItemMeta rodMeta = rod.getItemMeta();
        rodMeta.spigot().setUnbreakable(true);
        rod.setItemMeta(rodMeta);
        // GAPP
        ItemStack gapp = new ItemStack(Material.GOLDEN_APPLE, 12);
        // ARROW
        ItemStack arrow = new ItemStack(Material.ARROW);

        // HELMET
        ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta helmetMeta = helmet.getItemMeta();
        helmetMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        helmetMeta.spigot().setUnbreakable(true);
        helmet.setItemMeta(helmetMeta);
        // CHESTPLATE
        ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2, true);
        chestplateMeta.spigot().setUnbreakable(true);
        chestplate.setItemMeta(chestplateMeta);
        // LEGGINS
        ItemStack leggins = new ItemStack(Material.IRON_LEGGINGS);
        ItemMeta legginsMeta = leggins.getItemMeta();
        legginsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        legginsMeta.spigot().setUnbreakable(true);
        leggins.setItemMeta(legginsMeta);
        // BOOTS
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        ItemMeta bootsMeta = boots.getItemMeta();
        bootsMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        bootsMeta.spigot().setUnbreakable(true);
        boots.setItemMeta(bootsMeta);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggins);
        player.getInventory().setBoots(boots);
        player.getInventory().addItem(sword, bow, block, block, water, pickaxe, rod, beef, gapp, block, block, water, water, arrow);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings)
    {
        if (strings.length == 0)
        {
            commandSender.sendMessage(main.prefix + help);
        }
        else if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            switch (strings[0].toLowerCase())
            {
                // CREATION DE LA PARTIE
                case "new":
                    if (strings.length > 1)
                    {
                        if (strings[1].equalsIgnoreCase("help"))
                        {
                            commandSender.sendMessage(main.prefix + help_new);
                            break;
                        }
                    }
                    if (player.isOp() && main.uhc_state.equals("end"))
                    {
                        // CHANGEMENT DE L'ETAT DE JEU
                        main.uhc_state = "new";
                        // Modif de variable
                        main.uhc_join_players = new Player[255];
                        main.uhc_number_join = 0;
                        // CREATION DES MAPS DE L'UHC + LOAD
                        WorldCreator worldCreatorUHC = new WorldCreator("uhc");
                        worldCreatorUHC.environment(World.Environment.NORMAL);
                        WorldCreator worldCreatorUHCNether = new WorldCreator("uhc_nether");
                        worldCreatorUHCNether.environment(World.Environment.NETHER);
                        if (strings.length > 1)
                        {
                            if (strings[1].equalsIgnoreCase("false"))
                            {
                                worldCreatorUHC.createWorld();
                                worldCreatorUHCNether.createWorld();
                                break;
                            }
                            else
                            {
                                long seed = Long.parseLong(strings[1]);
                                worldCreatorUHC.seed(seed);
                                worldCreatorUHCNether.seed(seed);
                            }
                        }
                        File uhcFile = new File(System.getProperty("user.dir") + "\\uhc");
                        main.fileDelete(uhcFile);
                        worldCreatorUHC.environment(World.Environment.NORMAL);
                        worldCreatorUHC.createWorld();
                        File uhcNetherFile = new File(System.getProperty("user.dir") + "\\uhc_nether");
                        main.fileDelete(uhcNetherFile);
                        worldCreatorUHCNether.environment(World.Environment.NETHER);
                        worldCreatorUHCNether.createWorld();
                        // ANNONCE
                        Bukkit.broadcastMessage(main.prefix + "Un nouveau UHC viens d'etre creer ");
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                // REJOINDRE LA PARTIE
                case "join":
                    if (main.uhc_state.equals("new"))
                    {
                        // VERIFICATION SI LE JOUEUR N'A PAS DEJA REJOIN
                        Boolean isIn = false;
                        for (Player join : main.uhc_join_players)
                        {
                            if (player == join) isIn = true;
                        }
                        if (isIn) commandSender.sendMessage(main.prefix + main.no_perm);
                        else
                        {
                            // AJOUT DU JOUEUR DANS LA LISTE
                            main.uhc_join_players[main.uhc_number_join] = player;
                            main.uhc_number_join++;
                            // ANNONCE
                            Bukkit.broadcastMessage(main.prefix + player.getDisplayName()  +" a rejoint l'UHC");
                        }
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                // QUITTER LA PARTIE
                case "quit":
                    if (main.uhc_state.equals("new"))
                    {
                        // VERIFIACTION SI LE JOUEUR A REJOIN LA PARTIE
                        Boolean isIn = false;
                        int player_number = 0;
                        int i = -1;
                        for (Player join : main.uhc_join_players)
                        {
                            i++;
                            if (player == join)
                            {
                                isIn = true;
                                player_number = i;
                            }
                        }
                        if (isIn)
                        {
                            main.uhc_join_players[player_number] = null;
                            // ANNONCE
                            Bukkit.broadcastMessage(main.prefix + player.getDisplayName() + " a quitte l'UHC");
                        }
                        else commandSender.sendMessage(main.prefix + main.no_perm);
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                // LANCER LA PARTIE
                case "start":
                    if (player.isOp() && main.uhc_state.equals("new"))
                    {
                        // ANNONCE
                        Bukkit.broadcastMessage(main.prefix + "L'UHC START !!!");
                        // CHANGEMENT D'ETAT
                        main.uhc_state = "start";
                        // SET WORLD BORDER
                        World uhc = Bukkit.getWorld("uhc");
                        WorldBorder border = uhc.getWorldBorder();
                        border.setCenter(0, 0);
                        border.setSize(main.uhc_initial_map_size());
                        border.setDamageAmount(1);
                        border.setDamageBuffer(1);
                        border.setWarningDistance(0);
                        World uhcNether = Bukkit.getWorld("uhc_nether");
                        WorldBorder borderNether = uhcNether.getWorldBorder();
                        borderNether.setCenter(0, 0);
                        borderNether.setSize(main.uhc_initial_map_size());
                        borderNether.setDamageAmount(1);
                        borderNether.setDamageBuffer(1);
                        borderNether.setWarningDistance(0);
                        // GameRule
                        uhc.setTime(0);
                        uhc.setGameRuleValue("naturalRegeneration","false");
                        uhc.setAutoSave(false);
                        uhcNether.setTime(0);
                        uhcNether.setGameRuleValue("naturalRegeneration","false");
                        uhc.setAutoSave(false);
                        // Teleportation des joueurs
                        main.uhc_alive_players = new Player[255];
                        main.uhc_number_alive = 0;
                        for (Player playerToTp : main.uhc_join_players)
                        {
                            if (playerToTp != null)
                            {
                                if (playerToTp.isOnline())
                                {
                                    main.uhc_alive_players[main.uhc_number_alive] = playerToTp;
                                    main.uhc_number_alive++;
                                    main.allClear(playerToTp);
                                    playerToTp.setGameMode(GameMode.SURVIVAL);
                                    int map_size = (int) border.getSize();
                                    int x = map_size/2-random.nextInt(map_size);
                                    int z = map_size/2-random.nextInt(map_size);
                                    Location location = new Location(Bukkit.getWorld("uhc"), x, 255, z);
                                    playerToTp.teleport(location);
                                    playerToTp.sendMessage(main.prefix + main.teleport);
                                    if (main.uhc_mode.equals("uhc")) giveStuffUHC(playerToTp);
                                    else giveStuffUHCMeetup(playerToTp);
                                }
                            }
                        }
                        // TIMER
                        new uhcTimer(main).startTimer();
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                // FERMER LA PARTIE
                case "end":
                    if (player.isOp() && !main.uhc_state.equals("end"))
                    {
                        // ANNONCE
                        Bukkit.broadcastMessage(main.prefix + "Fin de l'UHC");
                        // CHANGEMENT DE L'ETAT
                        main.uhc_state = "end";
                        // UNLOAD
                        main.unLoadWord(Bukkit.getWorld("uhc"));
                        main.unLoadWord(Bukkit.getWorld("uhc_nether"));
                    }
                    else commandSender.sendMessage(main.prefix + main.no_perm);
                    break;

                // SPEC
                case "spec":
                    boolean isOut = true;
                    if (main.uhc_number_alive > 0)
                    {
                        for (Player playerSpec : main.uhc_alive_players)
                        {
                            if (playerSpec == player) isOut = false;
                        }
                    }
                    if (isOut && !main.uhc_state.equals("new") && !main.uhc_state.equals("end"))
                    {
                        main.allClear(player);
                        player.setGameMode(GameMode.SPECTATOR);
                        player.teleport(new Location(Bukkit.getWorld("uhc"), 0, 150, 0));
                        player.sendMessage(main.prefix + main.teleport);
                    }
                    else if (isOut && !main.uhc_state.equals("end") && player.isOp())
                    {
                        main.allClear(player);
                        player.setGameMode(GameMode.SPECTATOR);
                        player.teleport(new Location(Bukkit.getWorld("uhc"), 0, 150, 0));
                        player.sendMessage(main.prefix + main.teleport);
                    }
                    else player.sendMessage(main.prefix + main.no_perm);
                    break;

                // REVIVE
                case "revive":
                    if (strings.length > 1)
                    {
                        Player playerToRevive = null;
                        Player playerRevive = null;
                        if (main.uhc_alive_players != null)
                        {
                            for (Player playerAlive : main.uhc_alive_players)
                            {
                                if (playerAlive != null)
                                {
                                    if (playerAlive.getDisplayName().equalsIgnoreCase(strings[1]))
                                    {
                                        playerToRevive = playerAlive;
                                    }
                                }
                            }
                            for (Player playerOnline : Bukkit.getOnlinePlayers())
                            {
                                if (playerOnline.getDisplayName().equalsIgnoreCase(strings[1]) && playerOnline != playerToRevive)
                                {
                                    playerRevive = playerOnline;
                                }
                            }
                        }
                        if (playerRevive != null && player.isOp())
                        {
                            int map_size = (int) Bukkit.getWorld("uhc").getWorldBorder().getSize();
                            int x = map_size / 2 - random.nextInt(map_size);
                            int z = map_size / 2 - random.nextInt(map_size);
                            Location location = new Location(Bukkit.getWorld("uhc"), x, 255, z);
                            if (strings.length > 2)
                            {
                                if (strings[2].equalsIgnoreCase("true"))
                                {
                                    location = playerRevive.getLocation();
                                }
                            }
                            main.uhc_alive_players[main.uhc_number_revive - 1] = playerRevive;
                            main.uhc_number_alive++;
                            main.uhc_number_revive--;
                            main.allClear(playerRevive);
                            playerRevive.setGameMode(GameMode.SURVIVAL);
                            playerRevive.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30*20, 255, true, false));
                            playerRevive.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 30*20, 255, true, false));
                            playerRevive.teleport(location);
                            if (main.uhc_mode.equals("uhc")) giveStuffUHC(playerRevive);
                            else giveStuffUHCMeetup(playerRevive);
                            Bukkit.broadcastMessage(main.prefix + "§6" + playerRevive.getDisplayName() + " §7" + main.revive);
                        }
                        else commandSender.sendMessage(main.prefix + main.impossible);
                    }
                    break;

                // UHC
                case "uhc":
                    player.openInventory(new uhcMenu(main).menu());
                    break;

                // HELP
                default:
                    commandSender.sendMessage(main.prefix + help);
                    break;
            }
        }
        else Bukkit.broadcastMessage(main.prefix + main.no_perm);
        return false;
    }
}
