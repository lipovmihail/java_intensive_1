package ru.liga.utils;

import ru.liga.curses.Currency;
import ru.liga.curses.MakeFutureCurse;

import java.util.Locale;

public class ValidateCommands {
    //валидация входящих параметров
    public static boolean isValidInputParams(String[] args) {
        if (args.length != 3 || !args[0].equalsIgnoreCase("rate")
                || (!args[2].equalsIgnoreCase(MakeFutureCurse.TOMORROW)
                && !args[2].equalsIgnoreCase(MakeFutureCurse.WEEK))) {
            return false;
        }
        try {
            Currency.valueOf(args[1].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
