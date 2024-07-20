package me.night.economy.hook;

import me.night.economy.EconomyPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {
    private Economy economy;

    public void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = EconomyPlugin.getEconomyPlugin().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            EconomyPlugin.getEconomyPlugin().getDreamLogger().error("Vault not found. Some functionality won't work!");
            return;
        }
        economy = rsp.getProvider();
    }


    public boolean deposit(Player player, double amount) {
        if (economy != null) {
            return economy.depositPlayer(player, amount).transactionSuccess();
        }
        return false;
    }

    public double getBalance(Player player) {
        if (economy != null) {
            return economy.getBalance(player);
        }
        return 0.0;
    }
}
