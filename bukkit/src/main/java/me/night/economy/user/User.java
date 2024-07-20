package me.night.economy.user;

import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class User extends Document {

    private String name;
    private boolean materialsToBlocks = false;
    private boolean showOnActionBar = false;
    private boolean materialsToCash = false;

    private int eventTime;
    private boolean event;
    private int eventStrength;

    private int eventTurboTime;
    private boolean eventTurbo;
    private int eventTurboStrength;
    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}
