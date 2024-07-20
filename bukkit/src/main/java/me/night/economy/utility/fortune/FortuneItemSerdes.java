package me.night.economy.utility.fortune;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;
import org.bukkit.Material;


public class FortuneItemSerdes implements ObjectSerializer<FortuneItem> {

    @Override
    public boolean supports(@NonNull Class<? super FortuneItem> type) {
        return FortuneItem.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull FortuneItem object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("material", object.getMaterial());
        data.add("defaultDrop", object.getDefaultDrop());
        data.add("fortune1", object.getFortune1());
        data.add("fortune2", object.getFortune2());
        data.add("fortune3", object.getFortune3());
    }

    @Override
    public FortuneItem deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new FortuneItem(
                data.get("material", Material.class),
                data.get("defaultDrop", Double.class),
                data.get("fortune1", Double.class),
                data.get("fortune2", Double.class),
                data.get("fortune3", Double.class)
        );
    }
}