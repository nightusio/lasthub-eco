package me.night.economy.task;

import cc.dreamcode.platform.bukkit.component.scheduler.Scheduler;
import cc.dreamcode.utilities.bukkit.ChatUtil;
import com.hpfxd.bossbarlib.BossBar;
import eu.okaeri.placeholders.context.PlaceholderContext;
import eu.okaeri.placeholders.message.CompiledMessage;
import me.night.economy.bossbar.BossbarManager;
import me.night.economy.bossbar.EventBossBar;
import me.night.economy.bossbar.EventBossBarType;
import me.night.economy.config.PluginConfig;
import me.night.economy.user.User;
import me.night.economy.user.UserRepository;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import me.night.economy.utility.DateUtility;
import org.bukkit.Bukkit;

import java.time.Duration;

@Scheduler(delay = 0, interval = 20)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class TurboDropUserEventTask implements Runnable {

    private final UserRepository userRepository;
    private final PluginConfig pluginConfig;
    private final BossbarManager bossbarManager;

    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            User user = this.userRepository.findOrCreateByHumanEntity(player);

            if (user.isEvent()) {
                user.setEventTime(user.getEventTime() - 1);

                if (user.getEventTime() <= 0) {
                    user.setEvent(false);
                    bossbarManager.destroyBossBar(EventBossBarType.EVENTPLAYER, player);
                    user.save();
                    return;
                }

                final EventBossBar eventBossBar = bossbarManager.getBossBar(EventBossBarType.EVENTPLAYER, player);
                final BossBar bossBar = eventBossBar.getBossBar();

                final String title = PlaceholderContext.of(CompiledMessage.of(pluginConfig.bossBarPlayerTitle))
                        .with("multiplier", user.getEventStrength())
                        .with("time", DateUtility.secondsToString(user.getEventTime()))
                        .apply();
                bossBar.setText(ChatUtil.fixColor(title));
                bossBar.setProgress((float) this.duration(user.getEventTime()));
                user.save();
            }

            if (user.isEventTurbo()) {
                user.setEventTurboTime(user.getEventTurboTime() - 1);

                if (user.getEventTurboTime() <= 0) {
                    user.setEventTurbo(false);
                    bossbarManager.destroyBossBar(EventBossBarType.TURBOPLAYER, player);
                    user.save();
                    return;
                }

                final EventBossBar eventBossBar = bossbarManager.getBossBar(EventBossBarType.TURBOPLAYER, player);
                final BossBar bossBar = eventBossBar.getBossBar();

                final String title = PlaceholderContext.of(CompiledMessage.of(pluginConfig.bossBarTurboPlayerTitle))
                        .with("multiplier", user.getEventTurboStrength())
                        .with("time", DateUtility.secondsToString(user.getEventTurboTime()))
                        .apply();
                bossBar.setText(ChatUtil.fixColor(title));
                bossBar.setProgress((float) this.duration(user.getEventTurboTime()));
                user.save();
            }
        });

    }

    private double duration(long eventDuration) {
        Duration duration = Duration.ofSeconds(eventDuration);
        if (duration.isNegative() || duration.isZero()) {
            return 0.00001;
        }

        long total = Duration.ofSeconds(eventDuration).getSeconds();
        long obtained = duration.getSeconds();

        double percent = (double) obtained / total;
        return Math.max(percent, 0.00001);
    }
}
