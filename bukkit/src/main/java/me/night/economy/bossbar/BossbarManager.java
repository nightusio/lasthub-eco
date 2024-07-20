package me.night.economy.bossbar;

import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import me.night.economy.EconomyPlugin;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BossbarManager {

    @Inject
    private EconomyPlugin economyPlugin;

    @Inject
    private BossbarBuilder bossBarBuilder;

    private final List<EventBossBar> bossBars = new ArrayList<>();

    public EventBossBar getBossBar(@NonNull EventBossBarType eventBossBarType, @NonNull Player player) {
        Optional<EventBossBar> bossBarOptional = this.bossBars.stream()
                .filter(eventBossBar -> eventBossBar.getPlayerUuid().equals(player.getUniqueId()))
                .filter(eventBossBar -> eventBossBar.getEventBossBarType().equals(eventBossBarType))
                .findAny();

        if (bossBarOptional.isPresent()) {
            return bossBarOptional.get();
        }

        final EventBossBar eventBossBar = this.bossBarBuilder.createBossbar(eventBossBarType, player);
        this.bossBars.add(eventBossBar);

        return eventBossBar;
    }

    public void destroyBossBar(@NonNull EventBossBarType eventBossBarType, @NonNull Player player) {
        this.bossBars.removeIf(eventBossBar -> {
            if (eventBossBar.getPlayerUuid().equals(player.getUniqueId()) &&
                    eventBossBar.getEventBossBarType().equals(eventBossBarType)) {
                eventBossBar.getBossBar().destroy();
                return true;
            }

            return false;
        });
    }

}