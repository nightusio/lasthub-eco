package me.night.economy.command.admin;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import me.night.economy.command.admin.sub.AdminSettingsEventTurboCommand;
import me.night.economy.command.admin.sub.AdminSettingsMaterialCashChangeCommand;
import me.night.economy.command.admin.sub.AdminSettingsSetShardCommand;
import me.night.economy.command.admin.sub.AdminSettingsEventCommand;
import me.night.economy.config.MessageConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.command.CommandSender;

import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "lasthub.eco.admin")
public class AdminSettingsCommand extends BukkitCommand {

    public final MessageConfig messageConfig;

    @Inject
    public AdminSettingsCommand(MessageConfig messageConfig) {
        super("austawienia", "asettings");
        this.messageConfig = messageConfig;

        this.registerSubcommand(AdminSettingsEventCommand.class);
        this.registerSubcommand(AdminSettingsSetShardCommand.class);
        this.registerSubcommand(AdminSettingsMaterialCashChangeCommand.class);
        this.registerSubcommand(AdminSettingsEventTurboCommand.class);
        this.setApplySubcommandsToTabCompleter(true);
    }

    @Override
    public void content(@NonNull CommandSender commandSender, @NonNull String[] strings) {
        messageConfig.adminSettings.send(commandSender);
    }

    @Override
    public List<String> tab(@NonNull CommandSender commandSender, @NonNull String[] strings) {
        return null;
    }
}
