package com.poker.utils;

import com.poker.model.constants.Constants;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringUtils {

    private static final Scanner sc = new Scanner(System.in);

    public static String readString() {
        return sc.nextLine();
    }

    /**
     * Convert a string into a string array regarding the space character
     */
    public static String[] tokenizeString(String str) {
        return tokenizeString(str, " ");
    }

    public static String[] tokenizeString(String str, String regex) {
        return str.split(regex);
    }

    /**
     * This method will map all command line to a Map<String,String>.
     * <p><p>
     * Example of use:<p>
     * Command: "sendMessage from=leandro to=andre Hello my name is Leandro!"<p>
     * commandMapped.get(Constants.COMMAND) = sendMessage<p>
     * commandMapped.get("from") = leandro<p>
     * commandMapped.get("to") = andre<p>
     * // this one will provide the text message<p>
     * commandMapped.get(Constants.COMMAND_LAST_DIVISION) = "Hello my name is Leandro!"<p><p>
     *
     * @param commandLine
     * @return
     */
    public static Map<String, String> mapCommand(String commandLine) {
        List<String> originalSplit = new ArrayList<>((List.of(tokenizeString(commandLine, " "))));
        // LinkedHashMap to have a map with the same order as inserted (necessary for setTableCards)
        Map<String, String> commandMapped = new LinkedHashMap<>();
        StringBuilder aux = new StringBuilder();
        commandMapped.put(Constants.COMMAND, originalSplit.remove(0));
        originalSplit.stream()
                .map(s -> tokenizeString(s, "="))
                .forEach(tokens -> {
                    if (tokens.length > 1) {
                        commandMapped.put(tokens[0], tokens[1]);
                    } else {
                        aux.append(tokens[0]).append(" ");
                    }
                });
        commandMapped.put(Constants.COMMAND_LAST_DIVISION, aux.toString());
        return commandMapped;
    }

    public static ArrayList<Integer> findStringIndex(String[] strArr, String str) {
        return (ArrayList<Integer>) IntStream.range(0, strArr.length).filter(x -> strArr[x].equals(str)).boxed().collect(Collectors.toList());
    }
}
