package me.night.economy.controller;

import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import me.night.economy.bossbar.BossbarManager;
import me.night.economy.bossbar.EventBossBarType;
import me.night.economy.config.TimeConfig;
import me.night.economy.user.User;
import me.night.economy.user.UserRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class PlayerQuitController implements Listener {

    private final TimeConfig timeConfig;
    private final BossbarManager bossbarManager;
    private final UserRepository userRepository;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        User user = userRepository.findOrCreateByHumanEntity(player);

        if (user.isEvent()) {
            this.bossbarManager.destroyBossBar(EventBossBarType.EVENTPLAYER, player);
        }

        if (user.isEventTurbo()) {
            this.bossbarManager.destroyBossBar(EventBossBarType.TURBOPLAYER, player);
        }

        if (timeConfig.globalEventTime > 0) {
            this.bossbarManager.destroyBossBar(EventBossBarType.EVENTALL, player);
        }

        if (timeConfig.globalTurboEventTime > 0) {
            this.bossbarManager.destroyBossBar(EventBossBarType.TURBOALL, player);
        }
    }
}
