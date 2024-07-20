package me.night.economy.bossbar;

import com.hpfxd.bossbarlib.BossBar;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
public class EventBossBar {

    private final UUID playerUuid;
    private final EventBossBarType eventBossBarType;
    private final BossBar bossBar;

}