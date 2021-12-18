package com.poker.utils;

import java.util.ArrayList;
import java.util.Scanner;
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
        return str.split(" ");
    }

    public static ArrayList<Integer> findStringIndex(String[] strArr, String str) {
        return (ArrayList<Integer>) IntStream.range(0, strArr.length).filter(x -> strArr[x].equals(str)).boxed().collect(Collectors.toList());
    }
}
