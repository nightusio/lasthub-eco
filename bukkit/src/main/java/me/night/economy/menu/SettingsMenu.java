package me.night.economy.menu;

import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.menu.bukkit.BukkitMenuBuilder;
import cc.dreamcode.menu.bukkit.base.BukkitMenu;
import cc.dreamcode.menu.bukkit.setup.BukkitMenuPlayerSetup;
import me.night.economy.EconomyPlugin;
import me.night.economy.config.PluginConfig;
import me.night.economy.user.User;
import me.night.economy.user.UserRepository;
import cc.dreamcode.utilities.builder.MapBuilder;
import cc.dreamcode.utilities.bukkit.builder.ItemBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class SettingsMenu {

    private final PluginConfig pluginConfig;

    public void openMainSettingsMenu(HumanEntity entity) {
        SettingsMenuMainSetup craftingMenuSetup = EconomyPlugin.getEconomyPlugin().createInstance(SettingsMenuMainSetup.class);
        craftingMenuSetup.build(entity).open(entity);
    }


    @RequiredPlayer
    @RequiredArgsConstructor(onConstructor_ = @Inject)
    class SettingsMenuMainSetup implements BukkitMenuPlayerSetup {

        private final PluginConfig pluginConfig;
        private final UserRepository userRepository;
        @Override
        public BukkitMenu build(@NonNull HumanEntity humanEntity) {
            BukkitMenuBuilder bukkitMenuBuilder = pluginConfig.mainSettingsMenu;
            final BukkitMenu bukkitMenu = bukkitMenuBuilder.buildWithItems();
            User user = userRepository.findOrCreateByHumanEntity(humanEntity);

            ItemStack fixedMaterialsToBlock = createItemStack(pluginConfig.materialsToBlocks.getItemStack(), user.isMaterialsToBlocks());
            ItemStack fixedShowOnActionBar = createItemStack(pluginConfig.showOnActionBar.getItemStack(), user.isShowOnActionBar());
            ItemStack fixedMaterialsToCash = createItemStack(pluginConfig.materialsToCash.getItemStack(), user.isMaterialsToCash());

            bukkitMenu.setItem(pluginConfig.materialsToBlocks.getSlot(), fixedMaterialsToBlock, event -> {
                user.setMaterialsToBlocks(!user.isMaterialsToBlocks());
                user.save();
                openMainSettingsMenu(humanEntity);
            });

            bukkitMenu.setItem(pluginConfig.showOnActionBar.getSlot(), fixedShowOnActionBar, event -> {
                user.setShowOnActionBar(!user.isShowOnActionBar());
                user.save();
                openMainSettingsMenu(humanEntity);
            });

            bukkitMenu.setItem(pluginConfig.materialsToCash.getSlot(), fixedMaterialsToCash, event -> {
                user.setMaterialsToCash(!user.isMaterialsToCash());
                user.save();
                openMainSettingsMenu(humanEntity);
            });

            return bukkitMenu;
        }
    }

    private ItemStack createItemStack(ItemStack itemStack, boolean status) {
        return ItemBuilder.of(itemStack)
                .fixColors()
                .fixColors(new MapBuilder<String, Object>()
                .put("status", status ? pluginConfig.statusViewText.getStatusOn() : pluginConfig.statusViewText.getStatusOff())
                .build())
                .toItemStack();
    }
}
