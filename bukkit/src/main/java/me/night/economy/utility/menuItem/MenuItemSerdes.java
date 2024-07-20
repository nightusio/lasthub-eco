package me.night.economy.utility.menuItem;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

public class MenuItemSerdes implements ObjectSerializer<MenuItem> {

    @Override
    public boolean supports(@NonNull Class<? super MenuItem> type) {
        return MenuItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull MenuItem object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("slot", object.getSlot());
        data.add("itemStack", object.getItemStack());

    }

    @Override
    public MenuItem deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new MenuItem(
                data.get("slot", Integer.class),
                data.get("itemStack", ItemStack.class)
        );
    }
}