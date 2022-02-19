package com.poker.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.poker.model.enums.ECommand.LIST_FRIENDLY_GAMES;
import static com.poker.model.enums.ECommand.LIST_FRIENDS;

public class MockCommands {
    public static final List<String> CREATE_GAME = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "cfg name=jogo1 creator=lj",
            "reg name=ana",
            "log name=ana",
            "reg name=manel",
            "log name=manel",
            "joinGame name=jogo1 player=ana",
            "joinGame name=jogo1 player=manel",
            "startGame name=jogo1 player=lj"
    ));

    public static final List<String> CREATE_COMPETITIVE_GAME = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "cjc name=jogo1 creator=lj fee=1.5 bigBlind=50 increment=5",
            "log name=ana",
            "joinGame name=jogo1 player=ana"
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

    public static final List<String> CREATE_GAME_WINNING = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "cfg name=jogo1 creator=lj",
            "reg name=ana",
            "log name=ana",
            "reg name=manel",
            "log name=manel",
            "joinGame name=jogo1 player=ana",
            "joinGame name=jogo1 player=manel",
            "startGame name=jogo1 player=lj",
            "check game=jogo1 player=lj",
            "check game=jogo1 player=ana",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=lj",
            "check game=jogo1 player=ana",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=lj",
            "check game=jogo1 player=ana",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=lj",
            "check game=jogo1 player=ana",
            "check game=jogo1 player=manel"
    ));

    public static final List<String> CREATE_FRIEND_REQUEST = new ArrayList<>(Arrays.asList(
            "log name=lj",
            "log name=ana",
            "addFriend player=lj add=ana",
            LIST_FRIENDS.getExample()
    ));

    public static final List<String> DEBUG_COMMAND_TEST = new ArrayList<>(Arrays.asList(
            "reg name=ana",
            "reg name=lj",
            "log name=lj",
            "cfg name=jogo1 creator=lj",
            "log name=ana",
            "joinGame name=jogo1 player=ana",
            "startGame name=jogo1 player=lj",
            "setCards player=lj game=jogo1 c1=KS c2=KC",
            "setCards player=ana game=jogo1 c1=KH c2=KD",
            "setCards player=lj game=jogo1 c1=KS c2=8C"
    ));

    public static final List<String> SEE_GAME_COMMAND_TEST = new ArrayList<>(Arrays.asList(
            "reg name=ana",
            "reg name=lj",
            "reg name=admin",
            "log name=admin",
            "log name=lj",
            "cfg name=jogo1 creator=lj",
            "log name=ana",
            "joinGame name=jogo1 player=ana",
            "startGame name=jogo1 player=lj",
            "sga game=jogo1"
    ));

    public static final List<String> ADD_GAME_COMMAND_TEST = new ArrayList<>(Arrays.asList(
            "reg name=ana",
            "reg name=lj",
            "reg name=admin",
            "log name=admin",
            "log name=lj",
            "cfg name=jogo1 creator=lj",
            "log name=ana",
            "joinGame name=jogo1 player=ana",
            "startGame name=jogo1 player=lj",
            "ag name=jogo2",
            "listFriendlyGames",
            "rg name=jogo1",
            "listFriendlyGames"
    ));

    public static final List<String> RANKING_COMMAND_TEST = new ArrayList<>(Arrays.asList(
            "reg name=ana",
            "reg name=lj",
            "reg name=admin",
            "log name=admin",
            "log name=lj",
            "cfg name=jogo1 creator=lj",
            "log name=ana",
            "listRankings",
            "setNewRanking player=lj wins=6",
            "listRankings",
            "setNewRanking player=ana wins=2",
            "listRankings",
            "removeRanking player=ana",
            "listRankings",
            "std"
    ));

    public static final List<String> KICK_COMMAND_TEST = new ArrayList<>(Arrays.asList(
            "reg name=ana",
            "reg name=lj",
            "reg name=admin",
            "log name=admin",
            "log name=lj",
            "log name=ana",
            "listarJogadores",
            "kick name=ana",
            "listarJogadores"
    ));

    public static final List<String> CREATE_GAME_BETTING = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "cfg name=jogo1 creator=lj",
            "reg name=ana",
            "log name=ana",
            "reg name=manel",
            "log name=manel",
            "joinGame name=jogo1 player=ana",
            "joinGame name=jogo1 player=manel",
            "startGame name=jogo1 player=lj",
            "bet game=jogo1 player=lj amount=5",
            "bet game=jogo1 player=ana amount=5",
            "bet game=jogo1 player=manel amount=5",
            "bet game=jogo1 player=lj amount=5",
            "bet game=jogo1 player=ana amount=5",
            "bet game=jogo1 player=manel amount=5",
            "bet game=jogo1 player=lj amount=5",
            "bet game=jogo1 player=ana amount=5",
            "bet game=jogo1 player=manel amount=5",
            "bet game=jogo1 player=lj amount=5",
            "bet game=jogo1 player=ana amount=5",
            "bet game=jogo1 player=manel amount=5"
    ));
}
