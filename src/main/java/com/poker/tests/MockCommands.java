package com.poker.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockCommands {
    public static final List<String> CREATE_GAME = new ArrayList<>(Arrays.asList(
            "log name=lj",
            "cfg name=jogo1 creator=lj",
            "log name=ana",
            "joinGame name=jogo1 player=ana",
            "startGame name=jogo1 player=lj"
    ));
}
