package com.poker;

import com.poker.logic.ApplicationData;
import com.poker.ui.TextUI;

public class Main {
    public static void main(String[] args) {
        ApplicationData data = ApplicationData.getInstance();
        TextUI ui = new TextUI(data);
        ui.start();
    }
}
