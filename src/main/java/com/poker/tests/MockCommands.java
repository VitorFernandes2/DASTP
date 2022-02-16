package com.poker.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.poker.model.enums.ECommand.LIST_FRIENDLY_GAMES;

public class MockCommands {
    public static final List<String> CREATE_GAME = new ArrayList<>(Arrays.asList(
            "log name=lj",
            "cfg name=jogo1 creator=lj",
            "log name=ana",
            "joinGame name=jogo1 player=ana",
            "startGame name=jogo1 player=lj",
            "std"
    ));

    public static final List<String> CREATE_COMPETITIVE_GAME = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "cjc name=jogo1 creator=lj",
            "log name=ana",
            "joinGame name=jogo1 player=ana",
            "startGame name=jogo1 player=lj"
    ));
    public static final List<String> BET_IN_GAME = new ArrayList<>(Arrays.asList(
            "log name=lj",
            "cfg name=jogo1 creator=lj",
            "log name=ana",
            "joinGame name=jogo1 player=ana",
            "startGame name=jogo1 player=lj",
            "bet game=jogo1 player=lj amount=5",
            "undo",
            "redo"
    ));

    public static final List<String> CREATE_PLAYER = new ArrayList<>(Arrays.asList(
            "reg name=lj12",
            "log name=lj"
    ));

    public static final List<String> LOGIN_PLAYER = new ArrayList<>(Arrays.asList(
            "log name=lj"
    ));

    public static final List<String> LIST_GAMES = new ArrayList<>(Arrays.asList(
            "log name=lj",
            "log name=ana",
            "cfg name=jogo1 creator=lj",
            "cfg name=jogo2 creator=ana",
            LIST_FRIENDLY_GAMES.getExample()
    ));
}
