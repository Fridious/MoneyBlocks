package de.fridious.moneyblocks.events;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 16.12.18 21:15
 *
 */

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MoneyBlocksMoneyEarnedEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private boolean cancelled;
    private final Player player;
    private final Block block;
    private final int money;

    public MoneyBlocksMoneyEarnedEvent(Player player, Block block, int money) {
        this.cancelled = false;
        this.player = player;
        this.block = block;
        this.money = money;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}