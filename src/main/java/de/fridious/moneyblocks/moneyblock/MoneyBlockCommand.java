package de.fridious.moneyblocks.moneyblock;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.12.18 19:08
 *
 */

public class MoneyBlockCommand {

    private final String name;
    private final String command;
    private final CommandRunner commandRunner;
    private final int chance;

    public MoneyBlockCommand(String name, String command, CommandRunner commandRunner, int chance) {
        this.name = name;
        this.command = command;
        this.commandRunner = commandRunner;
        this.chance = chance;
    }

    public String getName() {
        return name;
    }

    public String getCommand() {
        return command;
    }

    public CommandRunner getCommandRunner() {
        return commandRunner;
    }

    public int getChance() {
        return chance;
    }

    public enum CommandRunner {
        PLAYER,
        CONSOLE;
    }
}