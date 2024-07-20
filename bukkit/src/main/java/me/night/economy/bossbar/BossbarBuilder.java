package me.night.economy.bossbar;

import com.hpfxd.bossbarlib.BossBars;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import me.night.economy.EconomyPlugin;
import me.night.economy.config.PluginConfig;
import org.bukkit.entity.Player;

public class BossbarBuilder {

    @Inject
    private EconomyPlugin economyPlugin;

    @Inject
    private PluginConfig pluginConfig;

    public EventBossBar createBossbar(@NonNull EventBossBarType eventBossBarType, @NonNull Player player) {

        return new EventBossBar(
                player.getUniqueId(),
                eventBossBarType,
                BossBars.create(
                        economyPlugin,
                        player,
                        "Wait...",
                        1.0F,
                        pluginConfig.bossBarColor,
                        pluginConfig.bossBarStyle
                )
        );
    }
}