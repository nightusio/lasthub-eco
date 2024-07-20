package me.night.economy.utility.status;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import lombok.NonNull;

public class StatusViewSerdes implements ObjectSerializer<StatusView> {

    @Override
    public boolean supports(@NonNull Class<? super StatusView> type) {
        return StatusView.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NonNull StatusView object, @NonNull SerializationData data, @NonNull GenericsDeclaration generics) {
        data.add("statusOff", object.getStatusOff());
        data.add("statusOn", object.getStatusOn());

    }

    @Override
    public StatusView deserialize(@NonNull DeserializationData data, @NonNull GenericsDeclaration generics) {
        return new StatusView(
                data.get("statusOff", String.class),
                data.get("statusOn", String.class)
        );
    }
}