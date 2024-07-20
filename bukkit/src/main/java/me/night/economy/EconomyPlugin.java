package me.night.economy;

import cc.dreamcode.command.bukkit.BukkitCommandProvider;
import cc.dreamcode.menu.bukkit.BukkitMenuProvider;
import cc.dreamcode.menu.bukkit.okaeri.MenuBuilderSerdes;
import cc.dreamcode.notice.minecraft.bukkit.serdes.BukkitNoticeSerdes;
import cc.dreamcode.platform.DreamVersion;
import cc.dreamcode.platform.bukkit.DreamBukkitConfig;
import cc.dreamcode.platform.bukkit.DreamBukkitPlatform;
import cc.dreamcode.platform.bukkit.component.CommandComponentResolver;
import cc.dreamcode.platform.bukkit.component.ConfigurationComponentResolver;
import cc.dreamcode.platform.bukkit.component.ListenerComponentResolver;
import cc.dreamcode.platform.bukkit.component.RunnableComponentResolver;
import cc.dreamcode.platform.component.ComponentManager;
import cc.dreamcode.platform.persistence.DreamPersistence;
import cc.dreamcode.platform.persistence.component.DocumentPersistenceComponentResolver;
import cc.dreamcode.platform.persistence.component.DocumentRepositoryComponentResolver;
import me.night.economy.bossbar.BossbarBuilder;
import me.night.economy.bossbar.BossbarManager;
import me.night.economy.command.SettingsCommand;
import me.night.economy.command.admin.AdminSettingsCommand;
import me.night.economy.config.MessageConfig;
import me.night.economy.config.PluginConfig;
import me.night.economy.config.TimeConfig;
import me.night.economy.controller.BlockBreakController;
import me.night.economy.controller.PlayerJoinController;
import me.night.economy.controller.PlayerQuitController;
import me.night.economy.hook.VaultHook;
import me.night.economy.menu.SettingsMenu;
import me.night.economy.task.TurboDropGlobalEventTask;
import me.night.economy.task.TurboDropUserEventTask;
import me.night.economy.utility.fortune.FortuneItemSerdes;
import me.night.economy.utility.menuItem.MenuItemSerdes;
import me.night.economy.user.UserRepository;
import me.night.economy.utility.status.StatusViewSerdes;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import eu.okaeri.persistence.document.DocumentPersistence;
import eu.okaeri.tasker.bukkit.BukkitTasker;
import lombok.Getter;
import lombok.NonNull;

public final class EconomyPlugin extends DreamBukkitPlatform implements DreamBukkitConfig, DreamPersistence {

    @Getter private static EconomyPlugin economyPlugin;

    @Override
    public void load(@NonNull ComponentManager componentManager) {
        economyPlugin = this;
    }

    @Override
    public void enable(@NonNull ComponentManager componentManager) {
        this.registerInjectable(BukkitTasker.newPool(this));
        this.registerInjectable(BukkitMenuProvider.create(this));
        this.registerInjectable(BukkitCommandProvider.create(this, this.getInjector()));

        componentManager.registerResolver(CommandComponentResolver.class);
        componentManager.registerResolver(ListenerComponentResolver.class);
        componentManager.registerResolver(RunnableComponentResolver.class);

        componentManager.registerResolver(ConfigurationComponentResolver.class);
        componentManager.registerComponent(MessageConfig.class, messageConfig ->
                this.getInject(BukkitCommandProvider.class).ifPresent(bukkitCommandProvider -> {
                    bukkitCommandProvider.setRequiredPermissionMessage(messageConfig.noPermission.getText());
                    bukkitCommandProvider.setRequiredPlayerMessage(messageConfig.notPlayer.getText());
                }));

        componentManager.registerComponent(PluginConfig.class, pluginConfig -> {
            componentManager.setDebug(pluginConfig.debug);

            this.registerInjectable(pluginConfig.storageConfig);

            componentManager.registerResolver(DocumentPersistenceComponentResolver.class);
            componentManager.registerResolver(DocumentRepositoryComponentResolver.class);

            componentManager.registerComponent(DocumentPersistence.class);
            componentManager.registerComponent(UserRepository.class);
        });

        componentManager.registerComponent(TimeConfig.class);

        if (EconomyPlugin.getEconomyPlugin().getServer().getPluginManager().getPlugin("Vault") != null) {
            componentManager.registerComponent(VaultHook.class, VaultHook::setupEconomy);
        }
        componentManager.registerComponent(SettingsMenu.class);

        componentManager.registerComponent(BossbarBuilder.class);
        componentManager.registerComponent(BossbarManager.class);

        componentManager.registerComponent(SettingsCommand.class);
        componentManager.registerComponent(AdminSettingsCommand.class);

        componentManager.registerComponent(BlockBreakController.class);
        componentManager.registerComponent(PlayerJoinController.class);
        componentManager.registerComponent(PlayerQuitController.class);

        componentManager.registerComponent(TurboDropGlobalEventTask.class);
        componentManager.registerComponent(TurboDropUserEventTask.class);
    }

    @Override
    public void disable() {
        getServer().getScheduler().cancelTasks(getEconomyPlugin());
    }

    @Override
    public @NonNull DreamVersion getDreamVersion() {
        return DreamVersion.create("LastHub-Economy", "1.0", "nightusio");
    }

    @Override
    public @NonNull OkaeriSerdesPack getConfigSerdesPack() {
        return registry -> {
            registry.register(new BukkitNoticeSerdes());
            registry.register(new MenuBuilderSerdes());
            registry.register(new MenuItemSerdes());
            registry.register(new StatusViewSerdes());
            registry.register(new FortuneItemSerdes());
        };
    }

    @Override
    public @NonNull OkaeriSerdesPack getPersistenceSerdesPack() {
        return registry -> {
            registry.register(new SerdesBukkit());
        };
    }

}
