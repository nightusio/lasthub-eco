package me.night.economy.config;

import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

@Configuration(
        child = "times.yml"
)
@Header("## LatHub-Eco | NIE RUSZAJ JESLI NIE WIESZ CO ROBISZ ##")
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class TimeConfig extends OkaeriConfig {

    public int globalEventTime;
    public boolean globalEvent;
    public int globalEventStrength;

    public int globalTurboEventTime;
    public boolean globalTurboEvent;
    public int globalTurboEventStrength;

}
