package de.fridious.moneyblocks.player;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.12.18 14:32
 *  
 */

import java.util.UUID;

/**
 * MoneyBlockPlayer for every registered player
 */
public class MoneyBlockPlayer {

    private final UUID uuid;
    private long rewardCooldown;
    
    public MoneyBlockPlayer(UUID uuid) {
        this.uuid = uuid;
        this.rewardCooldown = System.currentTimeMillis();
    }

    public UUID getUUID() {
        return uuid;
    }
    
    public long getRewardCooldown() {
        return rewardCooldown;
    }

    public void setRewardCooldown(long rewardCooldown) {
        this.rewardCooldown = rewardCooldown;
    }
}