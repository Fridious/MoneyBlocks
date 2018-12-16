package de.fridious.moneyblocks.config;

import de.fridious.moneyblocks.MoneyBlocks;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 14.12.18 15:56
 *
 */

/**
 * Abstract config
 */
public abstract class SimpleConfig {

    /*
     * Fields for config location and configuration
     */
    private final File file;
    private Configuration configuration;

    public SimpleConfig(File file) {
        this.file = file;
        try{
            file.getParentFile().mkdirs();
            if(!file.exists()) this.file.createNewFile();
        }catch (Exception exception) {
            System.out.println(MoneyBlocks.getInstance().getPluginConfig().getConsolePrefix() + "Could not create config file.");
            System.out.println(MoneyBlocks.getInstance().getPluginConfig().getConsolePrefix() + "Error: "+exception.getMessage());
            onFailed();
        }
    }

    public File getFile() {
        return file;
    }

    public void reloadConfig(){
        loadConfig();
    }

    /**
     * Load, register all defaults and save config
     */
    public void loadConfig() {
        load();
        registerDefaults();
        save();
        onLoad();
    }

    /**
     * Saves the config
     */
    public void save() {
        if(file == null || !file.exists()) return;
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.configuration,file);
        }catch (Exception exception) {
            System.out.println(MoneyBlocks.getInstance().getPluginConfig().getConsolePrefix() + "Could not save config file.");
            System.out.println(MoneyBlocks.getInstance().getPluginConfig().getConsolePrefix() + "Error: "+exception.getMessage());
            onFailed();
        }
    }

    /**
     * Load the config
     */
    private void load() {
        if(file == null) return;
        try{
            if(file.exists()) file.createNewFile();
            this.configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        }catch (Exception exception){
            System.out.println(MoneyBlocks.getInstance().getPluginConfig().getConsolePrefix() + "Could not load config file.");
            System.out.println(MoneyBlocks.getInstance().getPluginConfig().getConsolePrefix() + "Error: "+exception.getMessage());
            onFailed();
        }
    }

    /**
     * Return the configuration of the config
     * @return configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * Get a object
     * @param path
     * @return Object
     */
    public Object getValue(String path){
        return this.configuration.getStringList(path);
    }

    /**
     * Get a value as String
     * @param path
     * @return String
     */
    public String getStringValue(String path){
        return this.configuration.getString(path);
    }

    /**
     * Get a value as String with translating color codes
     * @param path
     * @return String
     */
    public String getMessageValue(String path){
        return ChatColor.translateAlternateColorCodes('&', getStringValue(path));
    }

    /**
     * Get a value as int
     * @param path
     * @return int
     */
    public int getIntValue(String path){
        return this.configuration.getInt(path);
    }

    /**
     * Get a value as double
     * @param path
     * @return double
     */
    public double getDoubleValue(String path){
        return this.configuration.getDouble(path);
    }

    /**
     * Get a value as float
     * @param path
     * @return float
     */
    public float getFloatValue(String path) {
        return this.configuration.getFloat(path);
    }

    /**
     * Get a value as long
     * @param path
     * @return long
     */
    public long getLongValue(String path){
        return this.configuration.getLong(path);
    }

    /**
     * Get a value as boolean
     * @param path
     * @return boolean
     */
    public boolean getBooleanValue(String path){
        return this.configuration.getBoolean(path);
    }

    /**
     * Get a list with strings
     * @param path
     * @return List<String>
     */
    public List<String> getStringListValue(String path){
        return this.configuration.getStringList(path);
    }

    public List<String> getMessageListValue(String path) {
        List<String> messages = new LinkedList<>();
        getStringListValue(path).forEach((message)-> messages.add(ChatColor.translateAlternateColorCodes('&', message)));
        return messages;
    }

    /**
     * Get a list with ints
     * @param path
     * @return List<Integer>
     */
    public List<Integer> getIntListValue(String path){
        return this.configuration.getIntList(path);
    }

    /**
     * Get a list with doubles
     * @param path
     * @return List<Double>
     */
    public List<Double> getDoubleListValue(String path){
        return this.configuration.getDoubleList(path);
    }

    /**
     * Get a list with longs
     * @param path
     * @return List<Long>
     */
    public List<Long> getLongListValue(String path){
        return this.configuration.getLongList(path);
    }

    /**
     * Get a list with booleans
     * @param path
     * @return List<Boolean>
     */
    public List<Boolean> getBooleanListValue(String path){
        return this.configuration.getBooleanList(path);
    }

    /**
     * Get all keys of a path
     * @param path
     * @return Collection<String>
     */
    public Collection<String> getKeys(String path){
        Configuration config = this.configuration.getSection(path);
        if(config != null) return config.getKeys();
        else return new LinkedList<>();
    }

    /**
     * Check if path contains in config
     * @param path
     * @return boolean
     */
    public boolean contains(String path){
        return this.configuration.contains(path);
    }

    /**
     * Set a value in config, if path already exists, the value of the part will override
     * @param path
     * @param value
     */
    public void setValue(String path, Object value){
        this.configuration.set(path,value);
    }

    /**
     * Add a value to config
     * @param path
     * @param value
     */
    public void addValue(String path, Object value){
        if(!this.configuration.contains(path) )this.configuration.set(path,value);
    }

    /**
     * Called on load of config
     */
    protected abstract void onLoad();

    /**
     * Called to register defaults of config
     */
    protected abstract void registerDefaults();

    /**
     * Called on load failed
     */
    protected abstract void onFailed();
}
