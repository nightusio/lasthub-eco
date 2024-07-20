package me.night.economy.utility;

import java.util.Random;

public class RandomUtility {

    private static final Random rand = new Random();;

    public static int getRandInt(int min, int max) throws IllegalArgumentException {
        return rand.nextInt(max - min + 1) + min;
    }

    public static Double getRandDouble(double min, double max) throws IllegalArgumentException {
        return rand.nextDouble() * (max - min) + min;
    }

    public static Float getRandFloat(float min, float max) throws IllegalArgumentException {
        return rand.nextFloat() * (max - min) + min;
    }

    public static boolean getChance(double chance) {
        return chance >= 100.0 || chance >= getRandDouble(0.0, 100.0);
    }

    public static double round(double value, int decimals) {
        double p = Math.pow(10.0, decimals);
        return Math.round(value * p) / p;
    }

}
