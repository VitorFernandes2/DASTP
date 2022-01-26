package com.poker.tests;

import com.poker.model.enums.ECommand;

import java.util.Scanner;

public class CommandsTest {
    public static void main(String[] args) {

        // List commands
        System.out.println(ECommand.commandsToString());

        // Test commands for an specific state
        do {
            System.out.print("Command: ");
            Scanner s = new Scanner(System.in);
            ECommand x = ECommand.fromString(s.nextLine());

            switch (x) {
                case LIST_FRIENDS:
                    System.out.println(x.getCommand());
                    break;
                case LIST_PLAYERS:
                    System.out.println(x.getCommand());
                    break;
                case LIST_BLOCKED:
                    System.out.println(x.getCommand());
                    break;
                case ADD_FRIEND:
                    System.out.println(x.getCommand());
                    break;
                default:
                    System.out.println(ECommand.UNKNOWN.getCommand());
            }
        } while (true);
    }
}
