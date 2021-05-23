package com.erhan.domain;

import java.util.List;
import java.util.Random;

public enum CarColor {
    RED,
    BLUE,
    BLACK,
    WHITE,
    GREEN;

    private static final List<CarColor> VALUES = List.of(values());
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static CarColor getRandom()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
