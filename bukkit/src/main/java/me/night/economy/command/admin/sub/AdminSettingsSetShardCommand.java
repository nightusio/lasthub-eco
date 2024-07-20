package me.night.economy.command.admin.sub;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.annotations.RequiredPlayer;
import cc.dreamcode.command.bukkit.BukkitCommand;
import me.night.economy.config.MessageConfig;
import me.night.economy.config.PluginConfig;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@RequiredPlayer
@RequiredPermission(permission = "lasthub.eco.admin.setshard")
public class AdminSettingsSetShardCommand extends BukkitCommand {

    private final PluginConfig pluginConfig;
    private final MessageConfig messageConfig;

    @Inject
    public AdminSettingsSetShardCommand(PluginConfig pluginConfig, MessageConfig messageConfig) {
        super("setshard");
        this.pluginConfig = pluginConfig;
        this.messageConfig = messageConfig;
    }

    @Override
    public void content(@NonNull CommandSender commandSender, @NonNull String[] strings) {
        Player player = (Player) commandSender;

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            messageConfig.itemCannotBeAir.send(player);
            return;
        }

        pluginConfig.shardItem = player.getInventory().getItemInMainHand();
        pluginConfig.save();

        messageConfig.shardItemSetSuccessfully.send(player);
    }

    @Override
    public List<String> tab(@NonNull CommandSender commandSender, @NonNull String[] strings) {
        return null;
    }
}
