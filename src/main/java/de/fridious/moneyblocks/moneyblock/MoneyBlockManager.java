package de.fridious.moneyblocks.moneyblock;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 15.12.18 20:37
 *
 */

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.LinkedList;
import java.util.List;

/**
 * Manage all money blocks and breaked blocks
 */
public class MoneyBlockManager {

    private List<MoneyBlock> moneyBlocks;
    private List<Location> breakedBlocksLocation;

    public MoneyBlockManager() {
        this.moneyBlocks = new LinkedList<>();
        this.breakedBlocksLocation = new LinkedList<>();
    }

    public List<MoneyBlock> getMoneyBlocks() {
        return moneyBlocks;
    }

    public MoneyBlock getMoneyBlock(Material material) {
        for (MoneyBlock moneyBlock : getMoneyBlocks()) if (moneyBlock.getMaterial().equals(material)) return moneyBlock;
        return null;
    }

    public boolean isBreakedBlockLocation(Block block) {
        return this.breakedBlocksLocation.contains(block.getLocation());
    }

    public void addBreakedBlockLocation(Block block) {
        this.breakedBlocksLocation.add(block.getLocation());
    }
}