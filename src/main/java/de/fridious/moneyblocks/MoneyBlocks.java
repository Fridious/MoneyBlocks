package de.fridious.moneyblocks;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 14.12.18 15:52
 *
 */

import de.fridious.moneyblocks.commands.MoneyBlocksCommand;
import de.fridious.moneyblocks.config.Config;
import de.fridious.moneyblocks.listeners.BlockBreakListener;
import de.fridious.moneyblocks.listeners.PlayerLoginListener;
import de.fridious.moneyblocks.moneyblock.MoneyBlockManager;
import de.fridious.moneyblocks.player.MoneyBlockPlayerManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MoneyBlocks extends JavaPlugin {

    /**
     * Instance of this class
     */
    private static MoneyBlocks instance;
    /**
     * Field of plugin config
     */
    private Config config;
    /**
     * The economy field for vault
     */
    private Economy economy;
    /**
     * The MoneyBlockManager field
     */
    private MoneyBlockManager moneyBlockManager;

    /**
     * The MoneyBlockPlayerManager field
     */
    private MoneyBlockPlayerManager moneyBlockPlayerManager;

    /**
     * Server version field
     */
    private String serverVersion, version;

    @Override
    public void onLoad() {
        /*
         * Set the instance of this class
         */
        instance = this;
        /*
         * Set the server version of this plugin
         */
        this.serverVersion = Bukkit.getVersion().replace(")", "").split(" ")[2];
        /*
         * Set the plugin version
         */
        this.version = getDescription().getVersion();
        /*
         * Set instance of MoneyBlockManager
         */
        this.moneyBlockManager = new MoneyBlockManager();
        /*
         * Create new instance of config and load
         */
        this.config = new Config();
        this.config.loadConfig();
        /*
         * Create new instance of MoneyBlockPlayerManager
         */
        this.moneyBlockPlayerManager = new MoneyBlockPlayerManager();
    }

    @Override
    public void onEnable() {
        /*
         * Register listeners and commands
         */
        Bukkit.getPluginManager().registerEvents(new BlockBreakListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLoginListener(), this);
        getCommand("moneyblocks").setExecutor(new MoneyBlocksCommand());
        /*
         * Setup vault
         */
        setupVault();
        System.out.println(getPluginConfig().getConsolePrefix() + "MoneyBlocks " + this.version + " is starting...");
        System.out.println(getPluginConfig().getConsolePrefix() + "Plugin is developed by Fridious");
        System.out.println(getPluginConfig().getConsolePrefix() + "GitHub: https://github.com/fridious");
    }

    @Override
    public void onDisable() {
        System.out.println(getPluginConfig().getConsolePrefix() + "MoneyBlocks " + this.version + " is stopping...");
        System.out.println(getPluginConfig().getConsolePrefix() + "Plugin is developed by Fridious");
        System.out.println(getPluginConfig().getConsolePrefix() + "GitHub: https://github.com/fridious");
    }

    private void setupVault() {
        if(getServer().getPluginManager().isPluginEnabled("Vault")) {
            RegisteredServiceProvider<Economy> serviceProvider = getServer().getServicesManager().getRegistration(Economy.class);
            if (serviceProvider == null) return;
            this.economy = serviceProvider.getProvider();
            System.out.println(getPluginConfig().getConsolePrefix() + "Vault found");
        } else {
            System.out.println(getPluginConfig().getConsolePrefix() + "Vault not found");
            System.out.println(getPluginConfig().getConsolePrefix() + "Plugin shutdown...");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public Config getPluginConfig() {
        return config;
    }

    public Economy getEconomy() {
        return economy;
    }

    public MoneyBlockManager getMoneyBlockManager() {
        return moneyBlockManager;
    }

    public MoneyBlockPlayerManager getMoneyBlockPlayerManager() {
        return moneyBlockPlayerManager;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public static MoneyBlocks getInstance() {
        return instance;
    }
}