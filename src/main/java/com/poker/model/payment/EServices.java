package com.poker.model.payment;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum EServices {
    PAYPAL("Paypal"),
    UNKNOWN("Unknown");

    private final String name;
    private static final Map<String, EServices> ENUM_SERVICES;

    static {
        ENUM_SERVICES = Arrays.stream(EServices.values())
                .collect(Collectors.toMap(EServices::getName, Function.identity()));
    }

    EServices(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EServices fromString(String command) {
        return ENUM_SERVICES.containsKey(command) ?
                ENUM_SERVICES.get(command) :
                ENUM_SERVICES.getOrDefault(command, UNKNOWN);
    }
}
