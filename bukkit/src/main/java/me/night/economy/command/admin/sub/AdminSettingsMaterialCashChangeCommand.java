package me.night.economy.command.admin.sub;

import cc.dreamcode.command.annotations.RequiredPermission;
import cc.dreamcode.command.bukkit.BukkitCommand;
import cc.dreamcode.utilities.builder.MapBuilder;
import eu.okaeri.injector.annotation.Inject;
import lombok.NonNull;
import me.night.economy.config.MessageConfig;
import me.night.economy.config.PluginConfig;
import me.night.economy.utility.fortune.FortuneItem;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

@RequiredPermission(permission = "lasthub.eco.admin.setcash")
public class AdminSettingsMaterialCashChangeCommand extends BukkitCommand {

    private final MessageConfig messageConfig;
    private final PluginConfig pluginConfig;

    @Inject
    public AdminSettingsMaterialCashChangeCommand(MessageConfig messageConfig, PluginConfig pluginConfig) {
        super("setcash");
        this.messageConfig = messageConfig;
        this.pluginConfig = pluginConfig;
    }

    @Override
    public void content(@NonNull CommandSender commandSender, @NonNull String[] strings) {
        if (strings.length <= 3|| strings[0].isEmpty() || strings[1].isEmpty() || strings[2].isEmpty()) {
            messageConfig.usage.send(commandSender, new MapBuilder<String, Object>()
                    .put("usage", "/austawienia setcash <materialName> <amount> <default, fortune1, fortune2, fortune3>")
                    .build());
            return;
        }

        Material material = Material.getMaterial(strings[0].toUpperCase());

        if (material == null) {
            messageConfig.materialIsNull.send(commandSender);
            return;
        }

        try {
            double cash = Double.parseDouble(strings[1]);
            String valueType = strings[2];

            FortuneItem fortuneItem = getFortuneItem(pluginConfig.blockMap, material);
            if (fortuneItem != null) {
                updateFortuneItemValue(commandSender, fortuneItem, valueType, cash);
                pluginConfig.save();

                messageConfig.materialCashChanged.send(commandSender, new MapBuilder<String, Object>()
                        .put("block", strings[0])
                        .put("cash", cash)
                        .put("type", valueType)
                        .build());
            } else {
                messageConfig.materialIsNull.send(commandSender);
            }
        } catch (NumberFormatException exception) {
            messageConfig.notNumber.send(commandSender);
        }
    }

    @Override
    public List<String> tab(@NonNull CommandSender commandSender, @NonNull String[] strings) {
        if (strings.length == 1) return pluginConfig.oreBlocks;
        if (strings.length == 2) return Arrays.asList("1.00", "2.00", "3.00", "4.00", "5.00");
        if (strings.length == 3) return Arrays.asList("default", "fortune1", "fortune2", "fortune3");
        return null;
    }

    private FortuneItem getFortuneItem(List<FortuneItem> blockMap, Material material) {
        for (FortuneItem item : blockMap) {
            if (item.getMaterial() == material) {
                return item;
            }
        }
        return null;
    }

    private void updateFortuneItemValue(CommandSender commandSender, FortuneItem fortuneItem, String valueType, double newValue) {
        switch (valueType) {
            case "default":
                fortuneItem.setDefaultDrop(newValue);
                break;
            case "fortune1":
                fortuneItem.setFortune1(newValue);
                break;
            case "fortune2":
                fortuneItem.setFortune2(newValue);
                break;
            case "fortune3":
                fortuneItem.setFortune3(newValue);
                break;
            default:
                messageConfig.usage.send(commandSender, new MapBuilder<String, Object>()
                        .put("usage", "/austawienia setcash <materialName> <amount> <default, fortune1, fortune2, fortune3>")
                        .build());
                break;
        }
        pluginConfig.save();
    }
}
