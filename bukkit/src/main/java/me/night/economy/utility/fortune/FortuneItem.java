package me.night.economy.utility.fortune;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@AllArgsConstructor
@Getter
@Setter
public class FortuneItem {

    private final Material material;
    private double defaultDrop;
    private double fortune1;
    private double fortune2;
    private double fortune3;

    public double[] getValues() {
        return new double[] {defaultDrop, fortune1, fortune2, fortune3};
    }
}
