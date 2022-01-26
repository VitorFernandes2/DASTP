package com.poker.tests;

import com.poker.model.filter.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterTest {
    public static void main(String[] args) {
        List<String> logs = new ArrayList<>(Arrays.asList("createGame gameName=game1 host=joao",
                "createGame gameName=game56 host=manuel",
                "buyCredits name=ana value=1000 payment=paypal",
                "joinGame gameName=game1 user=ana",
                "joinGame gameName=game56 user=mauricio",
                "joinGame gameName=game56 user=selena",
                "joinGame gameName=game56 user=picasso",
                "joinGame gameName=game56 user=pythagoras",
                "startGame gameName=game1 user=joao",
                "bet gameName=game1 user=joao value=10",
                "fold gameName=game1 user=ana",
                "pass gameName=game1 user=ana",
                "pass gameName=game1 user=joao",
                "startGame gameName=game56 user=manuel",
                "changeCards gameName=game1 user=ana which=random",
                "setCards gameName=game1 user=mesa cards=10H,KH,KD,",
                "setCards gameName=game1 user=ana cards=KS,KC",
                "setCards gameName=game1 user=joao cards=2S,2H",
                "bet gameName=game1 user=ana value=10",
                "msg from=joao to=ana msg=boa!",
                "bet gameName=game56 user=mauricio value=20",
                "bet gameName=game56 user=selena value=20",
                "bet gameName=game56 user=picasso value=30",
                "bet gameName=game56 user=pythagoras value=30",
                "bet gameName=game56 user=manuel value=30",
                "bet gameName=game56 user=mauricio value=30",
                "bet gameName=game56 user=selena value=30",
                "bet gameName=game1 user=joao value=20",
                "bet gameName=game1 user=ana value=40",
                "quit gameName=game1 user=joao"));
        FilterDecorator search1 = new UserFilter(new BetFilter(new Log(logs), 10), "ana");
        FilterDecorator search2 = new UserFilter(new GameFilter(new Log(logs), "game56"), "manuel");
        FilterDecorator search3 = new JoinGameFilter(new GameFilter(new Log(logs), "game56"));
        System.out.println("Searches for bets made by the user ana with the value of 10 PCJs inside the logs: " + search1.filter());
        System.out.println("Searches for actions made by the user manuel in the game game56 inside the logs: " + search2.filter());
        System.out.println("Searches for users that joined in the game game56 inside the logs: " + search3.filter());
    }
}
