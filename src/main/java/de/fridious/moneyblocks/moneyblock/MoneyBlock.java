package de.fridious.moneyblocks.moneyblock;

import de.fridious.moneyblocks.utils.GeneralUtil;
import org.bukkit.Material;
import org.bukkit.World;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 15.12.18 20:36
 *
 */

/**
 * MoneyBlock class for every registered material in config
 */
public class MoneyBlock {

    private final Material material;
    private final int rewardChance, minimumMoney, maximumMoney;
    private final List<String> disabledWorlds;

    public MoneyBlock(Material material, int rewardChance, int minimumMoney, int maximumMoney, List<String> disabledWorlds) {
        this.material = material;
        this.rewardChance = rewardChance;
        this.minimumMoney = minimumMoney;
        this.maximumMoney = maximumMoney;
        this.disabledWorlds = disabledWorlds;
    }

    public Material getMaterial() {
        return material;
    }

    public int getRewardChance() {
        return rewardChance;
    }

    public int getMinimumMoney() {
        return minimumMoney;
    }

    public int getMaximumMoney() {
        return maximumMoney;
    }

    public List<String> getDisabledWorlds() {
        return disabledWorlds;
    }

    public boolean isDisabledWorld(String world) {
        return getDisabledWorlds().contains(world);
    }

    public boolean isDisabledWorld(World world) {
        return isDisabledWorld(world.getName());
    }

    @Override
    public String toString() {
        return GeneralUtil.GSON.toJson(this);
    }
}