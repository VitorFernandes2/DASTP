package com.poker.tests;

import com.poker.service.enums.Command;

import java.util.Scanner;

public class CommandsTest {
    public static void main(String[] args) {

        // List commands
        System.out.println(Command.commandsToString());

        // Test commands for an specific state
        do {
            System.out.print("Command: ");
            Scanner s = new Scanner(System.in);
            Command x = Command.fromString(s.nextLine());

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
                    System.out.println(Command.UNKNOWN.getCommand());
            }
        } while (true);
    }
}
