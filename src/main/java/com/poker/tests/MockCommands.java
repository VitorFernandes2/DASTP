package com.poker.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.poker.model.enums.ECommand.LIST_FRIENDLY_GAMES;
import static com.poker.model.enums.ECommand.LIST_FRIENDS;

public class MockCommands {
    //<editor-fold defaultstate="collapsed" desc="Debug tests for develop">
    @Deprecated
    public static final List<String> CREATE_GAME = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "cfg name=game1 creator=lj",
            "reg name=ana",
            "log name=ana",
            "reg name=manel",
            "log name=manel",
            "joinGame name=game1 player=ana",
            "joinGame name=game1 player=manel",
            "startGame name=game1 player=lj",
            "bet game=game1 player=lj amount=5",
            "bet game=game1 player=ana amount=3",
            "check game=game1 player=manel"
//            "bet game=game1 player=lj amount=50",
//            "bet game=game1 player=ana amount=48",
//            "bet game=game1 player=manel amount=46"
    ));

    @Deprecated
    public static final List<String> CREATE_COMPETITIVE_GAME_2 = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "ccg name=game1 creator=lj fee=10 bigBlind=5",
            "reg name=ana",
            "log name=ana",
            "reg name=manel",
            "log name=manel",
            "joinGame name=game1 player=ana",
            "joinGame name=game1 player=manel",
            "transferMoney name=lj value=20",
            "transferMoney name=ana value=20",
            "transferMoney name=manel value=20",
            "startGame name=game1 player=lj",
            "log name=admin"
//            "bet game=game1 player=lj amount=500",
//            "bet game=game1 player=ana amount=498",
//            "bet game=game1 player=manel amount=495"
//            "bet game=game1 player=lj amount=5",
//            "bet game=game1 player=ana amount=3",
//            "check game=game1 player=manel"
//            "bet game=game1 player=lj amount=50",
//            "bet game=game1 player=ana amount=48",
//            "bet game=game1 player=manel amount=46"
    ));

    @Deprecated
    public static final List<String> CREATE_COMPETITIVE_GAME = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "ccg name=game1 creator=lj fee=10 bigBlind=4",
            "log name=ana",
            "joinGame name=game1 player=ana"
    ));

    @Deprecated
    public static final List<String> BET_IN_GAME = new ArrayList<>(Arrays.asList(
            "log name=lj",
            "cfg name=game1 creator=lj",
            "log name=ana",
            "joinGame name=game1 player=ana",
            "startGame name=game1 player=lj",
            "bet game=game1 player=lj amount=5",
            "undo",
            "redo"
    ));

    @Deprecated
    public static final List<String> CREATE_PLAYER = new ArrayList<>(Arrays.asList(
            "reg name=lj12",
            "log name=lj"
    ));

    @Deprecated
    public static final List<String> LOGIN_PLAYER = new ArrayList<>(Arrays.asList(
            "log name=lj"
    ));

    @Deprecated
    public static final List<String> LIST_GAMES = new ArrayList<>(Arrays.asList(
            "log name=lj",
            "log name=ana",
            "cfg name=game1 creator=lj",
            "cfg name=jogo2 creator=ana",
            LIST_FRIENDLY_GAMES.getExample()
    ));

    @Deprecated
    public static final List<String> CREATE_GAME_WINNING = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "cfg name=game1 creator=lj",
            "reg name=ana",
            "log name=ana",
            "reg name=manel",
            "log name=manel",
            "joinGame name=game1 player=ana",
            "joinGame name=game1 player=manel",
            "startGame name=game1 player=lj",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel"
    ));

    @Deprecated
    public static final List<String> CREATE_FRIEND_REQUEST = new ArrayList<>(Arrays.asList(
            "log name=lj",
            "log name=ana",
            "addFriend player=lj add=ana",
            LIST_FRIENDS.getExample()
    ));

    @Deprecated
    public static final List<String> DEBUG_COMMAND_TEST = new ArrayList<>(Arrays.asList(
            "reg name=ana",
            "reg name=lj",
            "log name=lj",
            "cfg name=game1 creator=lj",
            "log name=ana",
            "joinGame name=game1 player=ana",
            "startGame name=game1 player=lj",
            "setCards player=lj game=game1 c1=KS c2=KC",
            "setCards player=ana game=game1 c1=KH c2=KD",
            "setCards player=lj game=game1 c1=KS c2=8C"
    ));

    @Deprecated
    public static final List<String> SEE_GAME_COMMAND_TEST = new ArrayList<>(Arrays.asList(
            "reg name=ana",
            "reg name=lj",
            "reg name=admin",
            "log name=admin",
            "log name=lj",
            "cfg name=game1 creator=lj",
            "log name=ana",
            "joinGame name=game1 player=ana",
            "startGame name=game1 player=lj",
            "sga game=game1"
    ));

    @Deprecated
    public static final List<String> ADD_GAME_COMMAND_TEST = new ArrayList<>(Arrays.asList(
            "reg name=ana",
            "reg name=lj",
            "reg name=admin",
            "log name=admin",
            "log name=lj",
            "cfg name=game1 creator=lj",
            "log name=ana",
            "joinGame name=game1 player=ana",
            "startGame name=game1 player=lj",
            "ag name=jogo2",
            "listFriendlyGames",
            "rg name=game1",
            "listFriendlyGames"
    ));

    @Deprecated
    public static final List<String> RANKING_COMMAND_TEST = new ArrayList<>(Arrays.asList(
            "reg name=ana",
            "reg name=lj",
            "reg name=admin",
            "log name=admin",
            "log name=lj",
            "cfg name=game1 creator=lj",
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

    @Deprecated
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

    @Deprecated
    public static final List<String> CREATE_GAME_BETTING = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "cfg name=game1 creator=lj",
            "reg name=ana",
            "log name=ana",
            "reg name=manel",
            "log name=manel",
            "joinGame name=game1 player=ana",
            "joinGame name=game1 player=manel",
            "startGame name=game1 player=lj",
            "bet game=game1 player=lj amount=5",
            "bet game=game1 player=ana amount=5",
            "bet game=game1 player=manel amount=5",
            "bet game=game1 player=lj amount=5",
            "bet game=game1 player=ana amount=5",
            "bet game=game1 player=manel amount=5",
            "bet game=game1 player=lj amount=5",
            "bet game=game1 player=ana amount=5",
            "bet game=game1 player=manel amount=5",
            "bet game=game1 player=lj amount=5",
            "bet game=game1 player=ana amount=5",
            "bet game=game1 player=manel amount=5"
    ));

    @Deprecated
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

    @Deprecated
    public static final List<String> GAME_TO_FINNISH = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "cfg name=game1 creator=lj",
            "reg name=ana",
            "log name=ana",
            "reg name=manel",
            "log name=manel",
            "joinGame name=game1 player=ana",
            "joinGame name=game1 player=manel",
            "startGame name=game1 player=lj",
            "bet game=game1 player=lj amount=4",
            "bet game=game1 player=ana amount=2",
            "check game=game1 player=manel",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj"
    ));

    @Deprecated
    public static final List<String> COMPETITIVE_GAME_WINNING = new ArrayList<>(Arrays.asList(
            "reg name=lj amount=9000",
            "log name=lj",
            "bpc name=lj value=60 payment=Paypal",
            "ccg name=game1 creator=lj",
            "reg name=ana amount=100",
            "bpc name=lj value=60 payment=Paypal",
            "ccg name=game1 creator=lj",
            "reg name=ana amount=9000",
            "log name=ana",
            "bpc name=ana value=60 payment=Paypal",
            "reg name=manel amount=100",
            "bpc name=ana value=60 payment=Paypal",
            "reg name=manel amount=9000",
            "log name=manel",
            "bpc name=manel value=60 payment=Paypal",
            "reg name=manel1 amount=100",
            "log name=manel1",
            "joinGame name=game1 player=ana",
            "joinGame name=game1 player=manel",
            "joinGame name=game1 player=lj",
            "startGame name=game1 player=lj",
            "joinGame name=game1 player=manel1",
            "bet game=game1 player=lj amount=50",
            "bet game=game1 player=ana amount=48",
            "bet game=game1 player=manel amount=46",
            "setCards player=lj game=game1 c1=KS c2=KC",
            "setCards player=ana game=game1 c1=10S c2=10C",
            "setCards player=manel game=game1 c1=JS c2=JC",
            "check game=game1 player=manel",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "bpc name=manel value=60 payment=Paypal",
            "spd",
            "joinGame name=game1 player=ana",
            "joinGame name=game1 player=manel",
            "startGame name=game1 player=lj",
            "bet game=game1 player=lj amount=450",
            "bet game=game1 player=ana amount=448",
            "bet game=game1 player=manel amount=446",
            "setCards player=lj game=game1 c1=KS c2=KC",
            "setCards player=ana game=game1 c1=2S c2=10C",
            "setCards player=manel game=game1 c1=QS c2=9C",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "bet game=game1 player=ana amount=48",
            "bet game=game1 player=manel amount=46",
            "bet game=game1 player=lj amount=44",
            "setCards player=lj game=game1 c1=KS c2=KC",
            "setCards player=ana game=game1 c1=2S c2=10C",
            "setCards player=manel game=game1 c1=QS c2=9C",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana",
            "check game=game1 player=manel",
            "check game=game1 player=lj",
            "check game=game1 player=ana"
    ));

    public static final List<String> TEST_END_GAME_OF_A_COMPETITIVE = new ArrayList<>(Arrays.asList(
            "reg name=lj",
            "log name=lj",
            "reg name=ana",
            "log name=ana",
            "reg name=manel",
            "log name=manel",
            "transferMoney name=lj value=20",
            "transferMoney name=ana value=20",
            "transferMoney name=manel value=20",
            "buyPokerChips name=lj value=19 payment=Paypal",
            "buyPokerChips name=ana value=19 payment=Paypal",
            "buyPokerChips name=manel value=19 payment=Paypal",
            "ccg name=game1 creator=lj fee=10 bigBlind=6",
            "joinGame name=game1 player=ana",
            "joinGame name=game1 player=manel",
            "startGame name=game1 player=lj",
            "bet game=game1 player=lj amount=500",
            "bet game=game1 player=ana amount=497",
            "bet game=game1 player=manel amount=494"
    ));
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Mock commands for 23/02">

    /**
     * Mock commands:
     * - MOCK_FRIENDLY_GAME - Create a friendly game with 3 players where shows the bet, check, call and fold logic
     * - MOCK_ALLIN_COMPETITIVE_GAME - Create a competitive game with 3 players where all 3 made all-in in the setup state (before the first 3 cards are turned up)
     * - MOCK_SET_TABLE_AND_PLAYER_CARDS_COMPETITIVE_GAME - Create a competitive game with 3 players where the cards are change on the table and on one player hand
     * - MOCK_KICK_PLAYER_FROM_APP -
     * - MOCK_KICK_PLAYER_FROM_GAME -
     * - MOCK_LIST_GAMES_AND_PLAYERS -
     * - MOCK_CHAT_MESSAGES -
     *
     */

    public static final List<String> MOCK_FRIENDLY_GAME = new ArrayList<>(Arrays.asList(
            "register name=joao",
            "login name=joao",
            "createCompetitiveGame name=game2 creator=joao fee=10 bigBlind=6",
            "createFriendlyGame name=game2 creator=joao",
            "register name=henrique",
            "login name=henrique",
            "register name=anabela",
            "login name=anabela",
            "joinGame name=game2 player=henrique",
            "joinGame name=game2 player=anabela",
            "startGame name=game2 player=joao",
            "bet game=game2 player=joao amount=4",
            "bet game=game2 player=henrique amount=2",
            "check game=game2 player=anabela",
            "showGameInfo game=game2",
            "bet game=game2 player=henrique amount=2",
            "bet game=game2 player=henrique amount=4",
            "bet game=game2 player=anabela amount=8",
            "bet game=game2 player=joao amount=8",
            "bet game=game2 player=henrique amount=4",
            "showGameInfo game=game2",
            "fold game=game2 player=henrique",
            "check game=game2 player=anabela",
            "check game=game2 player=joao"

    ));

    public static final List<String> MOCK_ALLIN_COMPETITIVE_GAME = new ArrayList<>(Arrays.asList(
            "register name=lj",
            "login name=lj",
            "transferMoney name=lj value=20",
            "buyPokerChips name=lj value=19 payment=Paypal",
            "createCompetitiveGame name=game1 creator=lj fee=10 bigBlind=6",
            "register name=ana",
            "login name=ana",
            "register name=manel",
            "login name=manel",
            "transferMoney name=ana value=20",
            "transferMoney name=manel value=20",
            "buyPokerChips name=ana value=19 payment=Paypal",
            "buyPokerChips name=manel value=19 payment=Paypal",
            "joinGame name=game1 player=ana",
            "joinGame name=game1 player=manel",
            "startGame name=game1 player=lj",
            "bet game=game1 player=lj amount=500",
            "bet game=game1 player=ana amount=497",
            "bet game=game1 player=manel amount=494",
            "showGameInfo game=game1"
    ));

    public static final List<String> MOCK_SET_TABLE_AND_PLAYER_CARDS_COMPETITIVE_GAME = new ArrayList<>(Arrays.asList(
            "register name=lj",
            "login name=lj",
            "transferMoney name=lj value=20",
            "buyPokerChips name=lj value=19 payment=Paypal",
            "createCompetitiveGame name=game1 creator=lj fee=10 bigBlind=6",
            "register name=ana",
            "login name=ana",
            "register name=manel",
            "login name=manel",
            "transferMoney name=ana value=20",
            "transferMoney name=manel value=20",
            "buyPokerChips name=ana value=19 payment=Paypal",
            "buyPokerChips name=manel value=19 payment=Paypal",
            "joinGame name=game1 player=ana",
            "joinGame name=game1 player=manel",
            "startGame name=game1 player=lj",
            "bet game=game1 player=lj amount=6",
            "bet game=game1 player=ana amount=3",
            "check game=game1 player=manel",
            "showGameInfo game=game1",
            "reg name=admin",
            "log name=admin",
            "setTableCards game=game1 c1=AS c2=AC c3=AD c4=AH",
            "setTableCards game=game1 c1=AS c2=AC c3=AD",
            "showGameInfo game=game1",
            "setCards player=lj game=game1 c1=KS c2=KC",
            "showGameInfo game=game1"
    ));

    public static final List<String> MOCK_KICK_PLAYER_FROM_APP = new ArrayList<>(Arrays.asList(
            "register name=admin",
            "log name=admin",
            "register name=jose",
            "login name=jose"
//            "kick name=jose"
    ));

    public static final List<String> MOCK_KICK_PLAYER_FROM_GAME = new ArrayList<>(Arrays.asList(

    ));

    public static final List<String> MOCK_LIST_GAMES_AND_PLAYERS = new ArrayList<>(Arrays.asList(

    ));

    public static final List<String> MOCK_CHAT_MESSAGES = new ArrayList<>(Arrays.asList(

    ));

    //</editor-fold>
}
