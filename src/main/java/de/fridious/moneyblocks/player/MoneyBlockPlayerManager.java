package de.fridious.moneyblocks.player;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.12.18 14:33
 *
 */

import org.bukkit.entity.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Manage all money block players
 */
public class MoneyBlockPlayerManager {

    private List<MoneyBlockPlayer> moneyBlockPlayers;

    public MoneyBlockPlayerManager() {
        this.moneyBlockPlayers = new LinkedList<>();
    }

    public List<MoneyBlockPlayer> getMoneyBlockPlayers() {
        return moneyBlockPlayers;
    }

    public MoneyBlockPlayer getMoneyBlockPlayer(UUID uuid) {
        for(MoneyBlockPlayer moneyBlockPlayer : this.moneyBlockPlayers) if(moneyBlockPlayer.getUUID().equals(uuid)) return moneyBlockPlayer;
        return null;
    }

    public MoneyBlockPlayer getMoneyBlockPlayer(Player player) {
        return getMoneyBlockPlayer(player.getUniqueId());
    }

    public void createMoneyBlockPlayer(Player player) {
        createMoneyBlockPlayer(player.getUniqueId());
    }

    public void createMoneyBlockPlayer(UUID uuid) {
        if(getMoneyBlockPlayer(uuid) == null)this.moneyBlockPlayers.add(new MoneyBlockPlayer(uuid));
    }
}