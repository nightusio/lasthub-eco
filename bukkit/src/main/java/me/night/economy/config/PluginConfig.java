package me.night.economy.config;

import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import cc.dreamcode.platform.persistence.StorageConfig;
import cc.dreamcode.utilities.builder.ListBuilder;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import com.hpfxd.bossbarlib.BossBarColor;
import com.hpfxd.bossbarlib.BossBarStyle;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;
import me.night.economy.utility.fortune.FortuneItem;
import me.night.economy.utility.menuItem.MenuItem;
import me.night.economy.utility.status.StatusView;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

@Configuration(
        child = "config.yml"
)
@Header("## Dream-Template (Main-Config) ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {
    @Comment("Debug pokazuje dodatkowe informacje do konsoli. Lepiej wylaczyc. :P")
    public boolean debug = true;

    @Comment("Uzupelnij ponizsze menu danymi.")
    public StorageConfig storageConfig = new StorageConfig("dreamtemplate");

    @Comment("Main settings menu")
    public BukkitMenuBuilder mainSettingsMenu = new BukkitMenuBuilder("&7Ustawienia", 4, new MapBuilder<Integer, ItemStack>()
            .build());

    @Comment("MaterialsToBlocks item: ")
    public MenuItem materialsToBlocks = new MenuItem(11,
            ItemBuilder.of(Material.DIAMOND_BLOCK)
                    .setName("&6Automatyczna wymiana surowcow na bloki")
                    .setLore(
                            "&8>> &7Status: {status} ",
                            "&8>> &aKliknij LEWYM, aby przelaczyc!"

                    )
                    .toItemStack());

    @Comment("ShowOnActionBar item: ")
    public MenuItem showOnActionBar = new MenuItem(31,
            ItemBuilder.of(Material.REPEATER)
                    .setName("&6Wyswietlanie sie actionbara")
                    .setLore(
                            "&8>> &7Status: {status} ",
                            "&8>> &aKliknij LEWYM, aby przelaczyc!"

                    )
                    .toItemStack());

    @Comment("MaterialsToCash item: ")
    public MenuItem materialsToCash = new MenuItem(15,
            ItemBuilder.of(Material.IRON_NUGGET)
                    .setName("&6Automatyczna wymiana surowcow na pieniadze")
                    .setLore(
                            "&8>> &7Status: {status} ",
                            "&8>> &aKliknij LEWYM, aby przelaczyc!"

                    )
                    .toItemStack());

    @Comment("Status text view:")
    public StatusView statusViewText = new StatusView("&4OFF", "&aON");

    @Comment("Status char view:")
    public StatusView statusViewChar = new StatusView("&4✘", "&a✔");

    @Comment("Shard drop chance")
    public double shardChance = 5.00;

    @Comment("Shard item:")
    public ItemStack shardItem = ItemBuilder.of(Material.PRISMARINE_SHARD)
            .setName("&6wyhu cfel")
            .setLore("&6wyhu robi loda za 3 zl")
            .addEnchant(Enchantment.LUCK, 1)
            .addFlags(ItemFlag.HIDE_ENCHANTS)
            .toItemStack();

    @Comment("BlocksToMoney list:")
    public List<FortuneItem> blockMap = new ListBuilder<FortuneItem>()
            .add(new FortuneItem(Material.COAL_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.IRON_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.GOLD_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.DIAMOND_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.EMERALD_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.LAPIS_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.REDSTONE_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.NETHER_QUARTZ_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.NETHER_GOLD_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.COPPER_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.DEEPSLATE_COAL_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.DEEPSLATE_IRON_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.DEEPSLATE_GOLD_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.DEEPSLATE_DIAMOND_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.DEEPSLATE_EMERALD_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.DEEPSLATE_LAPIS_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.DEEPSLATE_REDSTONE_ORE, 1.0, 2.0, 3.0, 4.0))
            .add(new FortuneItem(Material.DEEPSLATE_COPPER_ORE, 1.0, 2.0, 3.0, 4.0))
            .build();

    @Comment("Jaki bossbar eventu ma miec kolor?")
    public BossBarColor bossBarColor = BossBarColor.YELLOW;

    @Comment("Jaki bossbar eventu ma miec style?")
    public BossBarStyle bossBarStyle = BossBarStyle.SEGMENTED_6;

    @Comment("Jakie title boosbar ma miec bossbar gdy jest event?")
    public String bossBarGlobalTitle = "&Event jest aktywny jeszcze &6{time} &fz moca &e&l{multiplier}";

    @Comment("Jakie title boosbar ma miec bossbar gdy jest event?")
    public String bossBarPlayerTitle = "&fMasz Event jest aktywny jeszcze &6{time} &fz moca &e&l{multiplier}";

    @Comment("Jakie title boosbar ma miec bossbar gdy jest event turbodrop?")
    public String bossBarTurboGlobalTitle = "&fTurbodrop jest aktywny jeszcze &6{time} &fz moca &e&l{multiplier}";

    @Comment("Jakie title boosbar ma miec bossbar gdy jest event turbodrop?")
    public String bossBarTurboPlayerTitle = "&fMasz Turbodrop jest aktywny jeszcze &6{time} &fz moca &e&l{multiplier}";


    @Comment("Ore blocks list:")
    public List<String> oreBlocks = Arrays.asList(
            "COAL_ORE",
            "IRON_ORE",
            "GOLD_ORE",
            "DIAMOND_ORE",
            "EMERALD_ORE",
            "LAPIS_ORE",
            "REDSTONE_ORE",
            "NETHER_QUARTZ_ORE",
            "NETHER_GOLD_ORE",
            "COPPER_ORE",
            "DEEPSLATE_COAL_ORE",
            "DEEPSLATE_IRON_ORE",
            "DEEPSLATE_GOLD_ORE",
            "DEEPSLATE_DIAMOND_ORE",
            "DEEPSLATE_EMERALD_ORE",
            "DEEPSLATE_LAPIS_ORE",
            "DEEPSLATE_REDSTONE_ORE",
            "DEEPSLATE_COPPER_ORE"
    );
}
