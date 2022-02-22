package com.poker.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ECommand {
    // Common commands
    LOGIN("login", "log", "login name=lj"),
    REGISTER("register", "reg", "register name=lj"),
    SHUTDOWN("shutdown", "std", "shutdown"),
    LOGOUT("logout", "lout", "logout name=joaquim"),
    UNDO("undo", "u", "undo"),
    REDO("redo", "r", "redo"),

    // User commands
    SEND_MESSAGE("sendMessage", "sm", "sendMessage from=joaquim to=ana Olá, o meu nome é joaquim!"),
    ADD_FRIEND("addFriend", "af", "addFriend player=joaquim add=antonio"),
    BLOCK_PLAYER("blockPlayer", "bp", "blockPlayer player=joaquim block=antonio"),
    BUY_POKER_CHIPS("comprarPokerChips", "cpc", ""),
    LIST_PLAYERS("listPlayers", "lp", "listPlayers"),
    LIST_FRIENDS("listFriend", "lf", "listFriend name=lj"),
    LIST_BLOCKED("listBlocked", "lb", "listBlocked"),
    LIST_FRIENDLY_GAMES("listFriendlyGames", "lfg", "listFriendlyGames"),
    LIST_COMPETITIVE_GAMES("listCompetitiveGames", "lcg", "listCompetitiveGames"),
    LIST_CHAMPIONSHIPS("listTour", "lt", "listTour"),
    LIST_RANKING("listRankings", "lr", "listRankings"),
    LIST_PLAYERS_DETAILS("showPlayersDetails", "spd", "showPlayersDetails"),
    CREATE_FRIENDLY_GAME("createFriendlyGame", "cfg", "createFriendlyGame name=jogo1 creator=lj"),
    CREATE_COMPETITIVE_GAME("createCompetitiveGame", "ccg", "ccg name=jogo1 creator=lj fee=2 bigBlind=4 increment=2"),
    START_GAME("startGame", "sg", "startGame name=jogo1 player=lj"),
    JOIN_GAME("joinGame", "jg", "joinGame name=jogo1 player=ana"),
    CREATE_TOURNAMENT("createTour", "ct", "createTournament name=tour1 player=ana"),
    JOIN_TOURNAMENT("joinTour", "jt", "joinTour name=tour1 player=ana"),
    START_TOURNAMENT("startTour", "sto", "startTour name=tour1 player=ana"),
    START_FINAL_TOURNAMENT("startTourFinal", "stf", "startTourFinal name=tour1"),

    //DEBUG COMMANDS
    ADD_CARDS_TO_USER("setCards", "st", "setCards player=lj game=jogo1 c1=KS c2=KC"),
    ADD_CUSTOM_RANKINGS("setNewRanking", "snr", "setNewRanking player=lj wins=5"),
    REMOVE_CUSTOM_RANKING("removeRanking", "rr", "removeRanking player=lj"),

    // In-Game commands
    BET("bet", "b", "bet game=jogo1 player=lj amount=5"),
    CHECK("check", "c", "check game=jogo1 player=lj"),
    FOLD("fold", "f", "fold game=jogo1 player=lj"),
    SHOW_GAME_INFO("showGameInfo", "sgi", "showGameInfo game=jogo1"),

    // Admin commands
    CREATE_USER("createPlayer", "cp", "cp name=vitor"),
    EDIT_USER("editPlayer", "ep", "ep name=vitor newName=vh"),
    KICK_USER("kick", "k", "kk name=vitor"),
    CHECK_USER_ACTIVITIES("checkActivities", "ca", "ca name=vitor"),
    SEE_GAME("seeGame", "sga", "sga game=jogo1"),
    ADD_GAME("addGame", "ag", "ag name=jogo1"),
    REMOVE_GAME("removeGame", "rg", "rg name=jogo1"),

    HELP("help", "h", "1"),

    // Debug commands


    // default value
    UNKNOWN("Comando desconhecido ... tente outra vez", "UNKNOWN", "2");

    private static final Map<String, ECommand> ENUM_COMMANDS;
    private static final Map<String, ECommand> ENUM_SHORTCUTS;
    private static final List<String> COMMANDS_EXAMPLE;

    static {
        ENUM_COMMANDS = Arrays.stream(ECommand.values())
                .collect(Collectors.toMap(ECommand::getCommand, Function.identity()));
        ENUM_SHORTCUTS = Arrays.stream(ECommand.values())
                .collect(Collectors.toMap(ECommand::getShortCut, Function.identity()));
        COMMANDS_EXAMPLE = Stream.of(ECommand.values())
                .map(ECommand::getExample)
                .collect(Collectors.toList());
    }

    private final String command;
    private final String shortCut;
    private final String example;

    ECommand(String command, String shortCut, String example) {
        this.command = command;
        this.shortCut = shortCut;
        this.example = example;
    }

    public static ECommand fromString(String command) {
        return ENUM_COMMANDS.containsKey(command) ?
                ENUM_COMMANDS.get(command) :
                ENUM_SHORTCUTS.getOrDefault(command, UNKNOWN);
    }

    public static String commandsToString() {
        StringBuilder commands = new StringBuilder();
        commands.append("\n## Comandos:");
        ENUM_COMMANDS.forEach((key, value) -> {
            if (value != UNKNOWN) {
                int shortCutLength = value.getShortCut().length();
                commands.append("\n[").append(value.getShortCut())
                        .append(shortCutLength == 1 ? "]    " :
                                shortCutLength == 2 ? "]   " :
                                        shortCutLength == 3 ? "]  " : "] ")
                        .append(key);
            }
        });
        return commands.toString();
    }

    public static void getCommandsExample() {
        StringBuilder str = new StringBuilder("# Commands example:");
        COMMANDS_EXAMPLE.forEach((s) -> str.append("\n ").append(s));
        str.setLength(str.length() - 4); // remove the last 4 characters
        System.out.println(str);
    }

    public String getCommand() {
        return command;
    }

    public String getShortCut() {
        return shortCut;
    }

    public String getExample() {
        return example;
    }

    @Override
    public String toString() {
        return "Command{" +
                "command='" + command + '\'' +
                ", shortCut='" + shortCut + '\'' +
                '}';
    }
}
