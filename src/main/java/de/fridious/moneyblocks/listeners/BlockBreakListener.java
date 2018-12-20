package de.fridious.moneyblocks.listeners;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 14.12.18 16:09
 *
 */

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import de.fridious.moneyblocks.MoneyBlocks;
import de.fridious.moneyblocks.config.Config;
import de.fridious.moneyblocks.events.MoneyBlocksMoneyEarnedEvent;
import de.fridious.moneyblocks.moneyblock.MoneyBlock;
import de.fridious.moneyblocks.moneyblock.MoneyBlockCommand;
import de.fridious.moneyblocks.player.MoneyBlockPlayer;
import de.fridious.moneyblocks.utils.GeneralUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Listen on block break
 */
public class BlockBreakListener implements Listener {

    @EventHandler(priority=EventPriority.HIGHEST)
    public void onBlockBreak(final BlockBreakEvent event) {
        /*
         * If event is canceled, return
         */
        if(event.isCancelled()) return;
        final Block block = event.getBlock();
        /*
         * Check, if block on the location is already breaked
         */
        if(MoneyBlocks.getInstance().getMoneyBlockManager().isBreakedBlockLocation(block))return;
        MoneyBlocks.getInstance().getMoneyBlockManager().addBreakedBlockLocation(block);
        final Player player = event.getPlayer();
        MoneyBlock moneyBlock = MoneyBlocks.getInstance().getMoneyBlockManager().getMoneyBlock(block.getType());
        /*
         * Check if money block is not null and if world is not disabled
         */
        if(moneyBlock != null && !moneyBlock.isDisabledWorld(block.getWorld())) {
            /*
             * Calculate chance
             */
            final int chance = GeneralUtil.RANDOM.nextInt(100);
            if(chance <= moneyBlock.getRewardChance()) {
                /*
                 * Calculate money
                 */
                final int money = GeneralUtil.RANDOM.nextInt(moneyBlock.getMaximumMoney()-moneyBlock.getMinimumMoney())+moneyBlock.getMinimumMoney();
                if(money > 0) {
                    MoneyBlocksMoneyEarnedEvent moneyEarnedEvent = new MoneyBlocksMoneyEarnedEvent(player, block, moneyBlock, money);
                    Bukkit.getPluginManager().callEvent(moneyEarnedEvent);
                    if(moneyEarnedEvent.isCancelled()) return;
                    Config config = MoneyBlocks.getInstance().getPluginConfig();
                    /*
                     Check for cool down
                     */
                    if(config.isCooldownEnabled() && !moneyEarnedEvent.isRemoveCooldown()) {
                        MoneyBlockPlayer moneyBlockPlayer = MoneyBlocks.getInstance().getMoneyBlockPlayerManager().getMoneyBlockPlayer(player);
                        if((moneyBlockPlayer.getRewardCooldown()+config.getCooldown()) >= System.currentTimeMillis()) return;
                        moneyBlockPlayer.setRewardCooldown(System.currentTimeMillis());
                    }
                    /*
                     * Check if holograms are enabled and if HolographicDisplays is enabled
                     */
                    if(config.isHologramEnabled() && Bukkit.getPluginManager().isPluginEnabled("HolographicDisplays")) {
                        Hologram hologram = HologramsAPI.createHologram(MoneyBlocks.getInstance(), block.getLocation().add(0.5, 0.5, 0.5));
                        hologram.getVisibilityManager().setVisibleByDefault(false);
                        config.getReplacedHologramLines(player,block, money).forEach(hologram::appendTextLine);
                        hologram.getVisibilityManager().showTo(player);
                        /*
                         * Remove hologram, after 3 seconds
                         */
                        Bukkit.getScheduler().runTaskLater(MoneyBlocks.getInstance(), hologram::delete, 20L*3);
                    }
                    /*
                     * Play sound, if enabled
                     */
                    if(config.isSoundEnabled())player.playSound(player.getLocation(), config.getSound(), config.getSoundVolume(), config.getSoundPitch());
                    /*
                     * Send message, if enabled
                     */
                    if(config.isMessageEnabled())player.sendMessage(config.getBlockBreak(player, block, money));
                    /*
                     * Add money to player
                     */
                    MoneyBlocks.getInstance().getEconomy().depositPlayer(player, moneyEarnedEvent.getMoney());
                    /*
                     * Runs all commands of MoneyBlock with a chance
                     */
                    int runs = 0;
                    for (MoneyBlockCommand command : moneyBlock.getCommands()) {
                        if(runs <= config.getMaxCommandRuns() || config.getMaxCommandRuns() == -1) {
                            final int commandChance = GeneralUtil.RANDOM.nextInt(100);
                            if(commandChance <= command.getChance()) {
                                String commandString = command.getCommand()
                                        .replace("[player]", player.getName())
                                        .replace("[money]", String.valueOf(moneyEarnedEvent.getMoney()))
                                        .replace("[block]", event.getBlock().getType().toString().toLowerCase());
                                runs++;
                                Bukkit.dispatchCommand(command.getCommandRunner() == MoneyBlockCommand.CommandRunner.CONSOLE ? Bukkit.getConsoleSender() : player, commandString);
                            }
                        }
                    }
                }
            }
        }
    }
}