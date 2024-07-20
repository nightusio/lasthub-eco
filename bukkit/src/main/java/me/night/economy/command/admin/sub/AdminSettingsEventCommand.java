package me.night.economy.command.admin.sub;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import me.night.economy.EconomyPlugin;
import me.night.economy.config.MessageConfig;
import me.night.economy.config.TimeConfig;
import me.night.economy.user.User;
import me.night.economy.user.UserRepository;
import me.night.economy.utility.DateUtility;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RequiredPermission(permission = "lasthub.eco.admin.event")
public class AdminSettingsEventCommand extends BukkitCommand {

    private final MessageConfig messageConfig;
    private final TimeConfig timeConfig;
    private final UserRepository userRepository;

    @Inject
    public AdminSettingsEventCommand(MessageConfig messageConfig, TimeConfig timeConfig, UserRepository userRepository) {
        super("event");
        this.messageConfig = messageConfig;
        this.timeConfig = timeConfig;
        this.userRepository = userRepository;
    }

    @Override
    public void content(@NonNull CommandSender commandSender, @NonNull String[] strings) {
        if (strings.length == 0 || strings[0].isEmpty()) {
            messageConfig.usage.send(commandSender, new MapBuilder<String, Object>()
                    .put("usage", "/austawienia event [gracz/all/off] [czas w sekundach] [mnożnik]").build());
            return;
        }

        if (strings[0].equalsIgnoreCase("off")) {
            if (timeConfig.globalEvent) {
                timeConfig.globalEventTime = 0;
                timeConfig.globalEvent = false;
                timeConfig.save();

                messageConfig.eventOff.send(commandSender);
            } else {
                messageConfig.eventIsAlreadyOff.send(commandSender);
            }
            return;
        }

        if (!strings[1].isEmpty() && !strings[2].isEmpty() || !strings[3].isEmpty()) {

            try {
                final int time = Integer.parseInt(strings[1]);

                final int multiplier = Integer.parseInt(strings[2]);

                if (strings[0].equalsIgnoreCase("all")) {
                    timeConfig.globalEvent = true;
                    timeConfig.globalEventTime = time;
                    timeConfig.globalEventStrength = multiplier;
                    timeConfig.save();

                    messageConfig.adminEventDropGlobalSuccessfullyGiven.send(commandSender, new MapBuilder<String, Object>()
                            .put("time", DateUtility.secondsToString(time))
                            .put("multiplier", multiplier)
                            .build());
                } else {
                    Player targetPlayer = Bukkit.getPlayerExact(strings[0]);

                    if (targetPlayer == null) {
                        messageConfig.noPlayer.send(commandSender);
                        return;
                    }

                    User targetUser = userRepository.findOrCreateByHumanEntity(targetPlayer);

                    targetUser.setEvent(true);
                    targetUser.setEventStrength(multiplier);
                    targetUser.setEventTime(time);
                    targetUser.save();

                    this.messageConfig.adminEventDropPlayerSuccessfullyGiven.send(commandSender, new MapBuilder<String, Object>()
                            .put("playerName", targetPlayer.getName())
                            .put("multiplier", multiplier)
                            .put("time", DateUtility.secondsToString(time))
                            .build());
                    this.messageConfig.eventDropPlayerSuccessfullyGiven.send(targetPlayer, new MapBuilder<String, Object>()
                            .put("playerName", commandSender.getName())
                            .put("multiplier", multiplier)
                            .put("time", DateUtility.secondsToString(time))
                            .build());
                }
            } catch (NumberFormatException exception) {
                messageConfig.notNumber.send(commandSender);
            }

        } else {
            messageConfig.usage.send(commandSender, new MapBuilder<String, Object>()
                    .put("usage", "/austawienia event [gracz/all/off] [czas w sekundach] [mnożnik]").build());
        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender commandSender, @NonNull String[] args) {
        if(args.length == 1) {
            return EconomyPlugin.getEconomyPlugin().getServer().getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .collect(Collectors.toList());
        }

        if(args.length == 2) {
            return Arrays.asList("10", "20", "30", "40", "50", "60", "70", "80", "90", "100");
        }

        if(args.length == 3) {
            return Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8");
        }
        return null;
    }
}
