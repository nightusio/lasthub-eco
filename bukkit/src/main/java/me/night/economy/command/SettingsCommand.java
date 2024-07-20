package me.night.economy.command;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import me.night.economy.menu.SettingsMenu;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "lasthub.eco.use")
public class SettingsCommand extends BukkitCommand {

    private final SettingsMenu settingsMenu;

    @Inject
    public SettingsCommand(SettingsMenu settingsMenu) {
        super("ustawienia", "settings");
        this.settingsMenu = settingsMenu;
    }

    @Override
    public void content(@NonNull CommandSender commandSender, @NonNull String[] strings) {
        settingsMenu.openMainSettingsMenu((HumanEntity) commandSender);
    }

    @Override
    public List<String> tab(@NonNull CommandSender commandSender, @NonNull String[] strings) {
        return null;
    }
}
