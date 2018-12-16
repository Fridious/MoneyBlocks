package de.fridious.moneyblocks.listeners;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.12.18 14:39
 *
 */

import de.fridious.moneyblocks.MoneyBlocks;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * Listen on player login
 */
public class PlayerLoginListener implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        /*
         * Load MoneyBlockPlayer
         */
        MoneyBlocks.getInstance().getMoneyBlockPlayerManager().createMoneyBlockPlayer(event.getPlayer());
    }
}