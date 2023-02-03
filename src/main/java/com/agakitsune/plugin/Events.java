package com.agakitsune.plugin;

import com.google.common.collect.ImmutableMap;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.Array;
import java.util.*;

public class Events implements Listener {

    private static final ArrayList<Material> ORES = new ArrayList<>(Arrays.asList(
                Material.COAL_ORE,
                Material.IRON_ORE,
                Material.GOLD_ORE,
                Material.DIAMOND_ORE,
                Material.REDSTONE_ORE,
                Material.LAPIS_ORE,
                Material.EMERALD_ORE
    ));

    private static final Map<EntityType, Material> PASSIVE = ImmutableMap.<EntityType, Material>builder()
            .put(EntityType.SHEEP, Material.COOKED_MUTTON)
            .put(EntityType.COW, Material.COOKED_BEEF)
            .put(EntityType.PIG, Material.GRILLED_PORK)
            .put(EntityType.CHICKEN, Material.COOKED_CHICKEN)
            .build();

    private static final Map<Material, Map<Enchantment, Integer>> ENCHANT = ImmutableMap.<Material, Map<Enchantment, Integer>>builder()
            .put(Material.LEATHER_HELMET, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build())
            .put(Material.LEATHER_CHESTPLATE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build())
            .put(Material.LEATHER_LEGGINGS, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build())
            .put(Material.LEATHER_BOOTS, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 4).build())
            //---
            .put(Material.IRON_HELMET, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build())
            .put(Material.IRON_CHESTPLATE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build())
            .put(Material.IRON_LEGGINGS, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build())
            .put(Material.IRON_BOOTS, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build())
            //---
            .put(Material.GOLD_HELMET, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build())
            .put(Material.GOLD_CHESTPLATE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build())
            .put(Material.GOLD_LEGGINGS, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build())
            .put(Material.GOLD_BOOTS, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build())
            //---
            .put(Material.DIAMOND_HELMET, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build())
            .put(Material.DIAMOND_CHESTPLATE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build())
            .put(Material.DIAMOND_LEGGINGS, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build())
            .put(Material.DIAMOND_BOOTS, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build())
            //---
            .put(Material.STONE_AXE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 5).build())
            .put(Material.STONE_PICKAXE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 5).build())
            .put(Material.STONE_HOE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 5).build())
            .put(Material.STONE_SPADE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 5).build())
            //---
            .put(Material.GOLD_AXE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 4).build())
            .put(Material.GOLD_PICKAXE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 4).build())
            .put(Material.GOLD_HOE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 4).build())
            .put(Material.GOLD_SPADE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 4).build())
            //---
            .put(Material.IRON_AXE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 3).build())
            .put(Material.IRON_PICKAXE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 3).build())
            .put(Material.IRON_HOE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 3).build())
            .put(Material.IRON_SPADE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 3).build())
            //---
            .put(Material.DIAMOND_AXE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 2).build())
            .put(Material.DIAMOND_PICKAXE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 2).build())
            .put(Material.DIAMOND_HOE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 2).build())
            .put(Material.DIAMOND_SPADE, ImmutableMap.<Enchantment, Integer>builder().put(Enchantment.DIG_SPEED, 2).build())
            //---
            .build();

    private static final Random RAND = new Random();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(
                ChatColor.translateAlternateColorCodes('&', "&e" + event.getPlayer().getName() + " joined the order&r")
        );
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (ORES.contains(block.getType())) {
            if (block.getType() == Material.COAL_ORE) {
                ItemStack torch = new ItemStack(Material.TORCH);
                torch.setAmount(4);

                ItemStack coal = new ItemStack(Material.COAL);
                coal.setAmount(1);

                player.getInventory().addItem(coal);
                player.getInventory().addItem(torch);
                event.setDropItems(false);
            } else if (block.getType() == Material.IRON_ORE) {
                ItemStack iron = new ItemStack(Material.IRON_INGOT);
                iron.setAmount(1);

                player.getInventory().addItem(iron);
                event.setDropItems(false);
            } else if (block.getType() == Material.GOLD_ORE) {
                ItemStack gold = new ItemStack(Material.GOLD_INGOT);
                gold.setAmount(1);

                player.getInventory().addItem(gold);
                event.setDropItems(false);
            }
            player.giveExp(RAND.nextInt(50));
        } else if (block.getType() == Material.GRAVEL) {
            ItemStack flint = new ItemStack(Material.FLINT);
            flint.setAmount(1);

            if (RAND.nextInt(2) == 0) {
                player.getInventory().addItem(flint);
            }
            event.setDropItems(false);
        } else if (block.getType() == Material.LEAVES) {
            ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE);
            gapple.setAmount(1);

            if (RAND.nextInt(100) == 0) {
                player.getInventory().addItem(gapple);
            }
            event.setDropItems(false);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        EntityType type = event.getEntityType();

        if (PASSIVE.containsKey(type)) {
            ItemStack cooked = new ItemStack(PASSIVE.get(type));
            cooked.setAmount(1);
            //------------------
            List<ItemStack> drops = event.getDrops();
            //------------------
            drops.clear();
            drops.add(cooked);
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        ItemStack result = event.getCurrentItem();

        if (ENCHANT.containsKey(result.getType())) {
            result.addEnchantments(ENCHANT.get(result.getType()));
        }
    }
}
