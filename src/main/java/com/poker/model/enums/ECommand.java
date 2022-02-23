package com.poker.model.enums;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    TRANSFER_MONEY("transferMoney", "tm", "transferMoney name=lj value=20"),
    BUY_POKER_CHIPS("buyPokerChips", "bpc", "buyPokerChips name=lj value=60 payment=Paypal"),
    LIST_PLAYERS("listPlayers", "lp", "listPlayers"),
    LIST_FRIENDS("listFriend", "lf", "listFriend name=lj"),
    LIST_BLOCKED("listBlocked", "lb", "listBlocked name=lj"),
    LIST_FRIENDLY_GAMES("listFriendlyGames", "lfg", "listFriendlyGames"),
    LIST_COMPETITIVE_GAMES("listCompetitiveGames", "lcg", "listCompetitiveGames"),
    LIST_CHAMPIONSHIPS("listTour", "lt", "listTour"),
    LIST_RANKING("listRankings", "lr", "listRankings"),
    LIST_PLAYERS_DETAILS("showPlayersDetails", "spd", "showPlayersDetails"),
    CREATE_FRIENDLY_GAME("createFriendlyGame", "cfg", "createFriendlyGame name=game1 creator=lj"),
    CREATE_COMPETITIVE_GAME("createCompetitiveGame", "ccg", "createCompetitiveGame name=game1 creator=lj fee=2 bigBlind=4 increment=2"),
    START_GAME("startGame", "sg", "startGame name=game1 player=lj"),
    JOIN_GAME("joinGame", "jg", "joinGame name=game1 player=ana"),
    LEAVE_GAME("leaveGame", "lg", "leaveGame name=game1 player=ana"),
    CREATE_TOURNAMENT("createTour", "ct", "createTournament name=tour1 player=ana"),
    JOIN_TOURNAMENT("joinTour", "jt", "joinTour name=tour1 player=ana"),
    START_TOURNAMENT("startTour", "sto", "startTour name=tour1 player=ana"),
    START_FINAL_TOURNAMENT("startTourFinal", "stf", "startTourFinal name=tour1"),

    //DEBUG COMMANDS
    ADD_CARDS_TO_USER("setCards", "st", "setCards player=lj game=game1 c1=KS c2=KC"),
    ADD_CUSTOM_RANKINGS("setNewRanking", "snr", "setNewRanking player=lj wins=5"),
    SET_TABLE_CARDS("setTableCards", "stc", "setTableCards game=game1 c1=AS c2=AC c3=AD c4=AH c5=KS"),
    REMOVE_CUSTOM_RANKING("removeRanking", "rr", "removeRanking player=lj"),

    // In-Game commands
    BET("bet", "b", "bet game=game1 player=lj amount=5"),
    CHECK("check", "c", "check game=game1 player=lj"),
    FOLD("fold", "f", "fold game=game1 player=lj"),
    SHOW_GAME_INFO("showGameInfo", "sgi", "showGameInfo game=game1"),

    // Admin commands
    CREATE_USER("createPlayer", "cp", "createPlayer name=vitor"),
    EDIT_USER("editPlayer", "ep", "editPlayer name=vitor newName=vh"),
    KICK_USER("kick", "k", "kick name=vitor"),
    KICK_FROM_GAME("kickFromGame", "kfg", "kickFromGame name=lj game=jogo1"),
    CHECK_USER_ACTIVITIES("checkActivities", "ca", "checkActivities name=vitor"),
    SEE_GAME("seeGame", "sga", "seeGame game=game1"),
    ADD_GAME("addGame", "ag", "addGame name=game1"),
    REMOVE_GAME("removeGame", "rg", "removeGame name=game1"),

    HELP("help", "h", ""),

    // Debug commands


    // default value
    UNKNOWN("Unknown command ... try again", "UNKNOWN", "");

    private static final Map<String, ECommand> ENUM_COMMANDS;
    private static final Map<String, ECommand> ENUM_SHORTCUTS;
    private static final Map<String, String> ENUM_COMMANDS_EXAMPLE = new LinkedHashMap<>();

    static {
        ENUM_COMMANDS = Arrays.stream(ECommand.values())
                .collect(Collectors.toMap(ECommand::getCommand, Function.identity()));
        ENUM_SHORTCUTS = Arrays.stream(ECommand.values())
                .collect(Collectors.toMap(ECommand::getShortCut, Function.identity()));
        for(ECommand e : ECommand.values()) {
            ENUM_COMMANDS_EXAMPLE.put(e.getShortCut(), e.getExample());
        }
        ENUM_COMMANDS_EXAMPLE.remove(UNKNOWN.getShortCut());
        ENUM_COMMANDS_EXAMPLE.remove(HELP.getShortCut());
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

    public static void getCommandsExample() {
        StringBuilder str = new StringBuilder("# Commands example:");
        ENUM_COMMANDS_EXAMPLE.forEach((shortCut, example) -> str.append("\n ").append("[").append(shortCut).append("]\t").append(example));
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
