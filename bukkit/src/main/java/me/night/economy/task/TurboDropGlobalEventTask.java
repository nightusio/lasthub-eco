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
import me.night.economy.config.TimeConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.RequiredArgsConstructor;
import me.night.economy.utility.DateUtility;
import org.bukkit.Bukkit;

import java.time.Duration;

@Scheduler(delay = 0, interval = 20)
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class TurboDropGlobalEventTask implements Runnable {

    private final TimeConfig timeConfig;
    private final PluginConfig pluginConfig;
    private final BossbarManager bossbarManager;

    @Override
    public void run() {
        int time = timeConfig.globalEventTime;
        boolean isEvent = timeConfig.globalEvent;
        int multiplier = timeConfig.globalEventStrength;

        int timeTurbo = timeConfig.globalTurboEventTime;
        boolean isEventTurbo = timeConfig.globalTurboEvent;
        int multiplierTurbo = timeConfig.globalTurboEventStrength;

        if (isEvent) {

            timeConfig.globalEventTime = time - 1;
            timeConfig.save();

            if (time <= 0) {
                timeConfig.globalEventStrength = 0;
                timeConfig.globalEvent = false;
                timeConfig.save();
            }

            Bukkit.getOnlinePlayers().forEach(player -> {

                if (time <= 0) {
                    bossbarManager.destroyBossBar(EventBossBarType.EVENTALL, player);
                    return;
                }


                final EventBossBar eventBossBar = this.bossbarManager.getBossBar(EventBossBarType.EVENTALL, player);
                final BossBar bossBar = eventBossBar.getBossBar();

                final String title = PlaceholderContext.of(CompiledMessage.of(pluginConfig.bossBarGlobalTitle))
                        .with("multiplier", multiplier)
                        .with("time", DateUtility.secondsToString(time))
                        .apply();
                bossBar.setText(ChatUtil.fixColor(title));
                bossBar.setProgress((float) this.duration(time));


            });
        }

        if (isEventTurbo) {

            timeConfig.globalTurboEventTime = timeTurbo - 1;
            timeConfig.save();

            if (timeTurbo <= 0) {
                timeConfig.globalTurboEventStrength = 0;
                timeConfig.globalTurboEvent = false;
                timeConfig.save();
            }

            Bukkit.getOnlinePlayers().forEach(player -> {

                if (timeTurbo <= 0) {
                    bossbarManager.destroyBossBar(EventBossBarType.TURBOALL, player);
                    return;
                }


                final EventBossBar eventBossBar = this.bossbarManager.getBossBar(EventBossBarType.TURBOALL, player);
                final BossBar bossBar = eventBossBar.getBossBar();

                final String title = PlaceholderContext.of(CompiledMessage.of(pluginConfig.bossBarTurboGlobalTitle))
                        .with("multiplier", multiplier)
                        .with("time", DateUtility.secondsToString(time))
                        .apply();
                bossBar.setText(ChatUtil.fixColor(title));
                bossBar.setProgress((float) this.duration(time));


            });
        }

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