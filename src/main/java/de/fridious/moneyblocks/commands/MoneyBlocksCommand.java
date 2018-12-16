package de.fridious.moneyblocks.commands;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.12.18 21:10
 *
 */

import de.fridious.moneyblocks.MoneyBlocks;
import de.fridious.moneyblocks.config.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Advertisement command
 */
public class MoneyBlocksCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Config config = MoneyBlocks.getInstance().getPluginConfig();
        /*
         * Send advertisement message to command sender. If commandSender is player with chat prefix, else with console prefix
         */
        commandSender.sendMessage((commandSender instanceof Player ? config.getChatPrefix() : config.getConsolePrefix()) + "This plugin was developed by Fridious(GitHub: https://github.com/fridious)");
        return true;
    }
}