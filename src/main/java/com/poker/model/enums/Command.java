package com.poker.model.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Command {
    // Common commands
    LOGIN("login", "lin"),
    LOGOUT("logout", "lout"),
    UNDO("undo", "u"),
    REDO("redo", "r"),

    // User commands
    SEND_MESSAGE("enviarMensagem", "em"),
    ADD_FRIEND("adicionarAmigo", "aa"),
    BLOCK_FRIEND("bloquearAmigo", "ba"),
    BUY_POKER_CHIPS("comprarPokerChips", "cpc"),
    LIST_PLAYERS("listarJogadores", "lj"),
    LIST_FRIENDS("listarAmigos", "la"),
    LIST_BLOCKED("listarBloqueados", "lb"),
    LIST_FRIENDLY_GAMES("listarJogosAmigáveis", "lja"),
    LIST_COMPETITIVE_GAMES("listarJogosCompetitivos", "ljc"),
    LIST_CHAMPIONSHIPS("listarCampeonatos", "lc"),
    LIST_RANKING("listarPontuações", "lp"),
    CREATE_FRIENDLY_GAME("criarJogoAmigável", "cja"),
    CREATE_COMPETITIVE_GAME("criarJogoCompetitivo", "cjc"),
    START_GAME("iniciarJogo", "ij"),
    JOIN_GAME("juntarJogo", "jj"),

    // Admin commands
    CREATE_USER("criarJogador", "cj"),
    EDIT_USER("editarJogador", "ej"),
    KICK_USER("expulsarJogador", "exj"),
    CHECK_USER_ACTIVITIES("verificarAtividadesJogador", "vaj"),
    SEE_GAME("verJogo", "vj"),
    ADD_GAME("adicionarJogo", "aj"),
    REMOVE_GAME("removerJogo", "rj"),

    // Game commands
    BET("apostar", "a"),
    SKIP("passar", "p"),
    GIVE_UP("desistir", "d"),
    SHOW_GAME_INFO("mostrarDados", "md"),

    // default value
    UNKNOWN("Comando desconhecido ... tente outra vez", "UNKNOWN");

    private static final Map<String, Command> ENUM_COMMANDS;
    private static final Map<String, Command> ENUM_SHORTCUTS;

    static {
        ENUM_COMMANDS = Arrays.stream(Command.values())
                .collect(Collectors.toMap(Command::getCommand, Function.identity()));
        ENUM_SHORTCUTS = Arrays.stream(Command.values())
                .collect(Collectors.toMap(Command::getShortCut, Function.identity()));
    }

    private final String command;
    private final String shortCut;

    Command(String command, String shortCut) {
        this.command = command;
        this.shortCut = shortCut;
    }

    public static Command fromString(String command) {
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

    public String getCommand() {
        return command;
    }

    public String getShortCut() {
        return shortCut;
    }

    @Override
    public String toString() {
        return "Command{" +
                "command='" + command + '\'' +
                ", shortCut='" + shortCut + '\'' +
                '}';
    }
}
