package me.night.economy.utility.menuItem;

import lombok.Data;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Data
@Getter
public class MenuItem {

    private final int slot;
    private final ItemStack itemStack;

}
