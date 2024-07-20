package me.night.economy.config;

import cc.dreamcode.notice.minecraft.MinecraftNoticeType;
import cc.dreamcode.notice.minecraft.bukkit.BukkitNotice;
import cc.dreamcode.platform.bukkit.component.configuration.Configuration;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.*;

@Configuration(
        child = "message.yml"
)
@Headers({
        @Header("## Dream-Template (Message-Config) ##"),
        @Header("Dostepne type: (DO_NOT_SEND, CHAT, ACTION_BAR, SUBTITLE, TITLE, TITLE_SUBTITLE)")
})
@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class MessageConfig extends OkaeriConfig {

    public BukkitNotice usage = new BukkitNotice(MinecraftNoticeType.CHAT, "&7Poprawne uzycie: &c{usage}");
    public BukkitNotice noPermission = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie posiadasz uprawnien.");
    public BukkitNotice noPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podanego gracza &cnie znaleziono.");
    public BukkitNotice playerIsOffline = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podany gracz &cjest offline.");
    public BukkitNotice notPlayer = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie jestes graczem.");
    public BukkitNotice notNumber = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Podana liczba &cnie jest cyfra.");
    public BukkitNotice playerIsMe = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Nie rob tego &cna sobie.");

    public BukkitNotice settingsActionBar = new BukkitNotice(MinecraftNoticeType.ACTION_BAR, "&fZamiana na bloki: {statusBlocks} &8| &fSprzedaz surowcow: {statusSell} &8{&6/ustawienia&8)");
    public BukkitNotice settingsActionBarEarn = new BukkitNotice(MinecraftNoticeType.ACTION_BAR, "&fZamiana na bloki: {statusBlocks} &8| &aZarobiles &2{earned}& &a(Okolo {earnedMinute}$ na minute) &8| &fSprzedaz surowcow: {statusSell} &8{&6/ustawienia&8)");


    public BukkitNotice adminSettings = new BukkitNotice(MinecraftNoticeType.CHAT, "helpcmd");

    public BukkitNotice shardDrop = new BukkitNotice(MinecraftNoticeType.TITLE, "wydropiles odlamek ilosc {amount}");

    public BukkitNotice eventOff = new BukkitNotice(MinecraftNoticeType.CHAT, "&8Turbodrop zostal wylaczony");
    public BukkitNotice eventIsAlreadyOff = new BukkitNotice(MinecraftNoticeType.CHAT, "&8Turbodrop zostal wylaczony");
    public BukkitNotice adminEventDropGlobalSuccessfullyGiven = new BukkitNotice(MinecraftNoticeType.CHAT, "&8Wlaczyles event dla calego serwera czas: {time}, multiplier: {multiplier}");
    public BukkitNotice adminEventDropPlayerSuccessfullyGiven = new BukkitNotice(MinecraftNoticeType.CHAT, "&8Dales event graczowi {playerName}, czas {time}, mnoznik {multiplier}");

    public BukkitNotice eventDropPlayerSuccessfullyGiven = new BukkitNotice(MinecraftNoticeType.CHAT, "&8Otrzymales event od gracza {playerName}, czas {time}, mnoznik {multiplier}");

    public BukkitNotice adminTurboEventGlobalSuccessfullyGiven = new BukkitNotice(MinecraftNoticeType.CHAT, "&8Wlaczyles turbodrop dla calego serwera czas: {time}, multiplier: {multiplier}");
    public BukkitNotice adminTurboEventPlayerSuccessfullyGiven = new BukkitNotice(MinecraftNoticeType.CHAT, "&8Dales turbodrop graczowi {playerName}, czas {time}, mnoznik {multiplier}");
    public BukkitNotice eventTurboPlayerSuccessfullyGiven = new BukkitNotice(MinecraftNoticeType.CHAT, "&8Otrzymales turbodrop od gracza {playerName}, czas {time}, mnoznik {multiplier}");
    public BukkitNotice itemCannotBeAir = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Itemek nie moze byc powietrzem");
    public BukkitNotice shardItemSetSuccessfully = new BukkitNotice(MinecraftNoticeType.CHAT, "&aPomyslnie ustawiles itemek!");

    public BukkitNotice materialIsNull = new BukkitNotice(MinecraftNoticeType.CHAT, "&4Blok o takiej nazwie nie istnieje!");
    public BukkitNotice materialCashChanged = new BukkitNotice(MinecraftNoticeType.CHAT, "&aZmieniles wartosc kasy dropiacej na bloku {block} na {cash}, typ: {type}");

}
