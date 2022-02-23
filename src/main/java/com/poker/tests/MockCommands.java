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
            "startGame name=jogo1 player=lj",
            "bet game=jogo1 player=lj amount=5",
            "bet game=jogo1 player=ana amount=3",
            "check game=jogo1 player=manel"
//            "bet game=jogo1 player=lj amount=50",
//            "bet game=jogo1 player=ana amount=48",
//            "bet game=jogo1 player=manel amount=46"
    ));

    public static final List<String> CREATE_COMPETITIVE_GAME_2 = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "ccg name=jogo1 creator=lj fee=10 bigBlind=5",
            "reg name=ana",
            "log name=ana",
            "reg name=manel",
            "log name=manel",
            "joinGame name=jogo1 player=ana",
            "joinGame name=jogo1 player=manel",
            "transferMoney name=lj value=20",
            "transferMoney name=ana value=20",
            "transferMoney name=manel value=20",
            "startGame name=jogo1 player=lj",
            "log name=admin"
//            "bet game=jogo1 player=lj amount=500",
//            "bet game=jogo1 player=ana amount=498",
//            "bet game=jogo1 player=manel amount=495"
//            "bet game=jogo1 player=lj amount=5",
//            "bet game=jogo1 player=ana amount=3",
//            "check game=jogo1 player=manel"
//            "bet game=jogo1 player=lj amount=50",
//            "bet game=jogo1 player=ana amount=48",
//            "bet game=jogo1 player=manel amount=46"
    ));

    public static final List<String> CREATE_COMPETITIVE_GAME = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "ccg name=jogo1 creator=lj fee=10 bigBlind=4",
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

    @Deprecated
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

    public static final List<String> CREATE_TOURNAMENT = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "reg name=lj1",
            "log name=lj1",
            "reg name=lj2",
            "log name=lj2",
            "reg name=lj3",
            "log name=lj3",
            "reg name=lj4",
            "log name=lj4",
            "reg name=lj5",
            "log name=lj5",
            "reg name=lj6",
            "log name=lj6",
            "reg name=lj7",
            "log name=lj7",
            "reg name=lj8",
            "log name=lj8",
            "createTour name=tour1 player=lj1",
            "listTour",
            "joinTour name=tour1 player=lj",
            "joinTour name=tour1 player=lj1",
            "joinTour name=tour1 player=lj2",
            "joinTour name=tour1 player=lj3",
            "joinTour name=tour1 player=lj4",
            "joinTour name=tour1 player=lj5",
            "joinTour name=tour1 player=lj6",
            "joinTour name=tour1 player=lj7",
            "joinTour name=tour1 player=lj8",
            "listTour",
            "startTour name=tour1 player=lj1",
            "listCompetitiveGames"
    ));

    public static final List<String> GAME_TO_FINNISH = new ArrayList<>(Arrays.asList(
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
            "bet game=jogo1 player=lj amount=4",
            "bet game=jogo1 player=ana amount=2",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=ana",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=lj",
            "check game=jogo1 player=ana",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=lj",
            "check game=jogo1 player=ana",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=lj"
    ));

    public static final List<String> COMPETITIVE_GAME_WINNING = new ArrayList<>(Arrays.asList(
            "reg name=lj amount=100",
            "log name=lj",
            "bpc name=lj value=60 payment=Paypal",
            "ccg name=jogo1 creator=lj",
            "reg name=ana amount=100",
            "log name=ana",
            "bpc name=ana value=60 payment=Paypal",
            "reg name=manel amount=100",
            "log name=manel",
            "bpc name=manel value=60 payment=Paypal",
            "reg name=manel1 amount=100",
            "log name=manel1",
            "joinGame name=jogo1 player=ana",
            "joinGame name=jogo1 player=manel",
            "joinGame name=jogo1 player=lj",
            "startGame name=jogo1 player=lj",
            "joinGame name=jogo1 player=manel1",
            "bet game=jogo1 player=lj amount=50",
            "bet game=jogo1 player=ana amount=48",
            "bet game=jogo1 player=manel amount=46",
            "setCards player=lj game=jogo1 c1=KS c2=KC",
            "setCards player=ana game=jogo1 c1=10S c2=10C",
            "setCards player=manel game=jogo1 c1=JS c2=JC",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=ana",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=lj",
            "check game=jogo1 player=ana",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=lj",
            "check game=jogo1 player=ana",
            "check game=jogo1 player=manel",
            "check game=jogo1 player=lj"
    ));
}
