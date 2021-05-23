package com.erhan.domain;

import java.util.*;


public enum DestinationCity {
    LOS_ANGELES("Los Angeles"),
    HOUSTON("Houston"),
    MIAMI("Miami"),
    NEW_YORK("New York");

    private String text;

    DestinationCity(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static Optional<DestinationCity> fromText(String text) {
        return Arrays.stream(values())
                .filter(city -> city.text.equalsIgnoreCase(text))
                .findFirst();
    }

    private static final List<DestinationCity> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static DestinationCity getRandom()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
