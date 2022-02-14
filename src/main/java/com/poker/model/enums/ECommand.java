package com.poker.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ECommand {
    // Common commands
    LOGIN("login", "log", ""),
    REGISTER("register", "reg", ""),
    SHUTDOWN("shutdown", "std", ""),
    LOGOUT("logout", "lout", "logout name=joaquim"),
    UNDO("undo", "u", ""),
    REDO("redo", "r", ""),

    // User commands
    SEND_MESSAGE("sendMessage", "sm", "sendMessage from=joaquim to=ana Olá, o meu nome é joaquim!"),
    ADD_FRIEND("addFriend", "af", "addFriend player=joaquim add=antonio"),
    BLOCK_PLAYER("blockPlayer", "bp", "blockPlayer player=joaquim block=antonio"),
    BUY_POKER_CHIPS("comprarPokerChips", "cpc", ""),
    LIST_PLAYERS("listarJogadores", "lj", ""),
    LIST_FRIENDS("listFriend", "lf", "listFriend"),
    LIST_BLOCKED("listBlocked", "lb", "listBlocked"),
    LIST_FRIENDLY_GAMES("listarJogosAmigáveis", "lja", ""),
    LIST_COMPETITIVE_GAMES("listarJogosCompetitivos", "ljc", ""),
    LIST_CHAMPIONSHIPS("listarCampeonatos", "lc", ""),
    LIST_RANKING("listarPontuações", "lp", ""),
    CREATE_FRIENDLY_GAME("createFriendlyGame", "cfg", "createFriendlyGame name=jogo1 creator=lj"),
    CREATE_COMPETITIVE_GAME("createCompetitiveGame", "cjc", "cjc name=jogo1 creator=lj"),
    START_GAME("startGame", "sg", "startGame name=jogo1 player=lj"),
    START_TURN("iniciarTurno", "it", ""),
    JOIN_GAME("joinGame", "jg", "joinGame name=jogo1 player=ana"),

    // In-Game commands
    BET("bet", "b", "bet game=jogo1 player=lj amount=5"),
    CHECK("passar", "p", ""),
    FOLD("desistir", "d", ""),
    SHOW_GAME_INFO("mostrarDados", "md", ""),

    // Admin commands
    CREATE_USER("criarJogador", "cj", ""),
    EDIT_USER("editarJogador", "ej", ""),
    KICK_USER("expulsarJogador", "exj", ""),
    CHECK_USER_ACTIVITIES("verificarAtividadesJogador", "vaj", ""),
    SEE_GAME("verJogo", "vj", ""),
    ADD_GAME("adicionarJogo", "aj", ""),
    REMOVE_GAME("removerJogo", "rj", ""),

    HELP("help", "h", "1"),

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
        str.setLength(str.length() - 4); // remove the last 4 appends
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
