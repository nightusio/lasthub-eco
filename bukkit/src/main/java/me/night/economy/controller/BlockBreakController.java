package me.night.economy.controller;

import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import me.night.economy.config.MessageConfig;
import me.night.economy.config.PluginConfig;
import me.night.economy.config.TimeConfig;
import me.night.economy.hook.VaultHook;
import me.night.economy.user.User;
import me.night.economy.user.UserRepository;
import me.night.economy.utility.RandomUtility;
import me.night.economy.utility.fortune.FortuneItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class BlockBreakController implements Listener {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;
    private final TimeConfig timeConfig;
    private final UserRepository userRepository;
    private final VaultHook vaultHook;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        User user = userRepository.findOrCreateByHumanEntity(player);

        if (RandomUtility.getChance(pluginConfig.shardChance) && pluginConfig.oreBlocks.contains(event.getBlock().getType().name())) {
            if (timeConfig.globalEvent) {
                int itemAmount = RandomUtility.getRandInt(1, timeConfig.globalEventStrength);
                ItemStack fixedShardItem = ItemBuilder.of(pluginConfig.shardItem).fixColors().setAmount(itemAmount).toItemStack();
                player.getInventory().addItem(fixedShardItem);

                messageConfig.shardDrop.send(player, new MapBuilder<String, Object>()
                        .put("amount", itemAmount)
                        .build());

            } else if (user.isEvent()) {
                int itemAmount = RandomUtility.getRandInt(1, user.getEventStrength());
                ItemStack fixedShardItem = ItemBuilder.of(pluginConfig.shardItem).fixColors().setAmount(itemAmount).toItemStack();
                player.getInventory().addItem(fixedShardItem);

                messageConfig.shardDrop.send(player, new MapBuilder<String, Object>()
                        .put("amount", itemAmount)
                        .build());
            }
        }


    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRawMaterialBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        User user = userRepository.findOrCreateByHumanEntity(player);

        if (event.isCancelled()) return;

        Material brokenBlock = event.getBlock().getType();

        if (user.isMaterialsToCash()) {
            FortuneItem fortuneItem = getFortuneItem(pluginConfig.blockMap, brokenBlock);

            if (fortuneItem != null) {
                event.setDropItems(false);

                String blocksStatusCharFixed = ChatUtil.fixColor(user.isMaterialsToBlocks() ? pluginConfig.statusViewChar.getStatusOn() : pluginConfig.statusViewChar.getStatusOff());
                String sellStatusCharFixed = ChatUtil.fixColor(user.isMaterialsToCash() ? pluginConfig.statusViewChar.getStatusOn() : pluginConfig.statusViewChar.getStatusOff());

                ItemStack tool = player.getInventory().getItemInMainHand();
                int fortuneLevel = tool.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                double value = getValueBasedOnFortune(fortuneItem, fortuneLevel);

                messageConfig.settingsActionBarEarn.send(player, new MapBuilder<String, Object>()
                        .put("statusBlocks" , blocksStatusCharFixed)
                        .put("statusSell", sellStatusCharFixed)
                        .put("earned", value )
                        .put("earnedMinute", value * 60)
                        .build());

                if (timeConfig.globalTurboEvent) {
                    int moneyMultiplier = RandomUtility.getRandInt(1, timeConfig.globalTurboEventStrength);

                    if (user.isShowOnActionBar()) {
                        messageConfig.settingsActionBarEarn.send(player, new MapBuilder<String, Object>()
                                .put("statusBlocks" , blocksStatusCharFixed)
                                .put("statusSell", sellStatusCharFixed)
                                .put("earned", value * moneyMultiplier)
                                .put("earnedMinute", value * moneyMultiplier * 60)
                                .build());
                    }

                    vaultHook.deposit(player, value * moneyMultiplier);
                    return;
                }

                if (user.isEventTurbo()) {
                    int moneyMultiplier = RandomUtility.getRandInt(1, user.getEventTurboStrength());

                    if (user.isShowOnActionBar()) {
                        messageConfig.settingsActionBarEarn.send(player, new MapBuilder<String, Object>()
                                .put("statusBlocks" , blocksStatusCharFixed)
                                .put("statusSell", sellStatusCharFixed)
                                .put("earned", value * moneyMultiplier)
                                .put("earnedMinute", value * moneyMultiplier * 60)
                                .build());
                    }

                    vaultHook.deposit(player, value * moneyMultiplier);
                    return;
                }

                vaultHook.deposit(player, value);
                return;
            }
        }

        if (user.isShowOnActionBar()) {
            String blocksStatusCharFixed = ChatUtil.fixColor(user.isMaterialsToBlocks() ? pluginConfig.statusViewChar.getStatusOn() : pluginConfig.statusViewChar.getStatusOff());
            String sellStatusCharFixed = ChatUtil.fixColor(user.isMaterialsToCash() ? pluginConfig.statusViewChar.getStatusOn() : pluginConfig.statusViewChar.getStatusOff());

            messageConfig.settingsActionBar.send(player, new MapBuilder<String, Object>()
                    .put("statusBlocks" , blocksStatusCharFixed)
                    .put("statusSell", sellStatusCharFixed)
                    .build());
        }

        if (!user.isMaterialsToBlocks()) return;

        if (pluginConfig.oreBlocks.contains(brokenBlock.name())) {
            event.setDropItems(false);

            Material smeltedMaterial = getSmeltedMaterial(brokenBlock);

            if (smeltedMaterial != null) {
                if (timeConfig.globalTurboEvent) {
                    int itemAmount = RandomUtility.getRandInt(1, timeConfig.globalTurboEventStrength);
                    player.getInventory().addItem(ItemBuilder.of(smeltedMaterial).setAmount(itemAmount + 1).toItemStack());
                } else if (user.isEventTurbo()) {
                    int itemAmount = RandomUtility.getRandInt(1, user.getEventTurboStrength());
                    player.getInventory().addItem(ItemBuilder.of(smeltedMaterial).setAmount(itemAmount + 1).toItemStack());
                } else {
                    player.getInventory().addItem(new ItemStack(smeltedMaterial));
                }
            }
        }

    }

    private Material getSmeltedMaterial(Material ore) {
        switch (ore) {
            case COAL_ORE:
            case DEEPSLATE_COAL_ORE:
                return Material.COAL;
            case IRON_ORE:
            case DEEPSLATE_IRON_ORE:
                return Material.IRON_INGOT;
            case GOLD_ORE:
            case DEEPSLATE_GOLD_ORE:
            case NETHER_GOLD_ORE:
                return Material.GOLD_INGOT;
            case DIAMOND_ORE:
            case DEEPSLATE_DIAMOND_ORE:
                return Material.DIAMOND;
            case EMERALD_ORE:
            case DEEPSLATE_EMERALD_ORE:
                return Material.EMERALD;
            case LAPIS_ORE:
            case DEEPSLATE_LAPIS_ORE:
                return Material.LAPIS_LAZULI;
            case REDSTONE_ORE:
            case DEEPSLATE_REDSTONE_ORE:
                return Material.REDSTONE;
            case NETHER_QUARTZ_ORE:
                return Material.QUARTZ;
            case COPPER_ORE:
            case DEEPSLATE_COPPER_ORE:
                return Material.COPPER_INGOT;
            default:
                return null;
        }
    }


    private FortuneItem getFortuneItem(List<FortuneItem> blockMap, Material material) {
        for (FortuneItem item : blockMap) {
            if (item.getMaterial() == material) {
                return item;
            }
        }
        return null;
    }

    private double getValueBasedOnFortune(FortuneItem item, int fortuneLevel) {
        double[] values = item.getValues();

        if (fortuneLevel < 0 || fortuneLevel >= values.length) {
            fortuneLevel = 0;
        }

        return values[fortuneLevel];
    }
}
