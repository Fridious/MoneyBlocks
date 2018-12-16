package de.fridious.moneyblocks.config;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 14.12.18 15:59
 *
 */

import de.fridious.moneyblocks.MoneyBlocks;
import de.fridious.moneyblocks.moneyblock.MoneyBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Config extends SimpleConfig {

    private String chatPrefix, consolePrefix, blockBreak;
    private Sound sound;
    private float soundVolume, soundPitch;
    private boolean soundEnabled, hologramEnabled, messageEnabled, cooldownEnabled;
    private List<String> hologramLines;
    private long cooldown;

    public Config() {
        super(new File(MoneyBlocks.getInstance().getDataFolder(), "config.yml"));
        this.chatPrefix = "&8[&6MoneyBlocks&8] ";
        this.consolePrefix = "[MoneyBlocks] ";
        this.messageEnabled = true;
        this.blockBreak = "[prefix]&7You have earned &e[money] &7for the block [block].";
        this.soundEnabled = true;
        /*
         * Set default sound for the given server version
         */
        if(MoneyBlocks.getInstance().getServerVersion().contains("1.8")) this.sound = Sound.valueOf("SUCCESSFUL_HIT");
        else this.sound = Sound.valueOf("ENTITY_ARROW_HIT_PLAYER");
        this.soundVolume = 100;
        this.soundPitch = 100;
        this.hologramEnabled = true;
        this.hologramLines = Arrays.asList("Congratulation!", "You get [money] money");
        this.cooldownEnabled = true;
        this.cooldown = 5000;
    }

    @Override
    protected void onLoad() {
        this.chatPrefix = getMessageValue("messages.prefix.chat");
        this.consolePrefix = getMessageValue("messages.prefix.console");
        this.messageEnabled = getBooleanValue("messages.blockbreak.enabled");
        this.blockBreak = getMessageValue("messages.blockbreak.message").replace("[prefix]", this.chatPrefix);
        this.cooldownEnabled = getBooleanValue("cooldown.enabled");
        this.cooldown = getLongValue("cooldown.length");
        this.soundEnabled = getBooleanValue("sound.enabled");
        this.sound = Sound.valueOf(getStringValue("sound.name"));
        this.soundVolume = getFloatValue("sound.volume");
        this.soundPitch = getFloatValue("sound.pitch");
        getKeys("moneyblocks").forEach((key)-> MoneyBlocks.getInstance().getMoneyBlockManager().getMoneyBlocks().add(getMoneyBlockFromConfig(key)));
        this.hologramEnabled = getBooleanValue("hologram.enabled");
        this.hologramLines = getMessageListValue("hologram.lines");
    }

    @Override
    protected void registerDefaults() {
        addValue("messages.prefix.chat", this.chatPrefix);
        addValue("messages.prefix.console", this.consolePrefix);
        addValue("messages.blockbreak.enabled", this.messageEnabled);
        addValue("messages.blockbreak.message", this.blockBreak);
        addValue("cooldown.enabled", this.cooldownEnabled);
        addValue("cooldown.length", this.cooldown);
        addValue("sound.enabled", this.soundEnabled);
        addValue("sound.name", this.sound.toString());
        addValue("sound.volume", this.soundVolume);
        addValue("sound.pitch", this.soundPitch);
        addValue("hologram.enabled", this.hologramEnabled);
        addValue("hologram.lines", this.hologramLines);
        addMoneyBlockToConfig(Material.GOLD_ORE, 5, 15, 65, "world_nether", "world_end");
        addMoneyBlockToConfig(Material.IRON_ORE, 90, 5, 25, "world1", "world8");
    }

    @Override
    protected void onFailed() {
        System.out.println(getConsolePrefix()+"Error occurred while loading Config");
        Bukkit.getPluginManager().disablePlugin(MoneyBlocks.getInstance());
    }

    public String getChatPrefix() {
        return chatPrefix;
    }

    public String getConsolePrefix() {
        return consolePrefix;
    }

    public Sound getSound() {
        return sound;
    }

    public float getSoundVolume() {
        return soundVolume;
    }

    public float getSoundPitch() {
        return soundPitch;
    }

    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    public boolean isHologramEnabled() {
        return hologramEnabled;
    }

    private List<String> getHologramLines() {
        return hologramLines;
    }

    /**
     * Get all hologram lines with replaced variables
     * @param player
     * @param block
     * @param money
     * @return a list of all replaced lines
     */
    public List<String> getReplacedHologramLines(Player player, Block block, int money) {
        List<String> lines = new LinkedList<>();
        for(String line : getHologramLines()) lines.add(getReplacedMessage(line, player, block, money));
        return lines;
    }

    public boolean isMessageEnabled() {
        return messageEnabled;
    }

    public String getBlockBreak(Player player, Block block, int money) {
        return getReplacedMessage(this.blockBreak, player, block, money);
    }

    /**
     * Replace all variables in a string
     * @param message
     * @param player
     * @param block
     * @param money
     * @return String
     */
    private String getReplacedMessage(String message, Player player, Block block, int money) {
        return message.replace("[block]", block.getType().toString().toLowerCase()
                .replace("_", " "))
                .replace("[money]", String.valueOf(money))
                .replace("[player]", player.getName());
    }

    /**
     * Get a money block from config
     * @param path
     * @return MoneyBlock
     */
    private MoneyBlock getMoneyBlockFromConfig(String path) {
        return new MoneyBlock(Material.getMaterial(path),
                getIntValue("moneyblocks."+path+".rewardchance"),
                getIntValue("moneyblocks."+path+".minimummoney"),
                getIntValue("moneyblocks."+path+".maximummoney"),
                getStringListValue("moneyblocks."+path+".disabledworlds"));
    }

    public boolean isCooldownEnabled() {
        return cooldownEnabled;
    }

    public long getCooldown() {
        return cooldown;
    }

    /**
     * Add a predefined money block to config
     * @param material
     * @param rewardChance
     * @param minimumMoney
     * @param maximumMoney
     * @param disabledWorlds
     */
    private void addMoneyBlockToConfig(Material material, int rewardChance, int minimumMoney, int maximumMoney, String... disabledWorlds) {
        String subPath = "moneyblocks." + material.toString()+".";
        addValue(subPath+"rewardchance", rewardChance);
        addValue(subPath+"minimummoney", minimumMoney);
        addValue(subPath+"maximummoney", maximumMoney);
        addValue(subPath+"disabledworlds", disabledWorlds);
    }
}