package ru.liga;

import ru.liga.curses.Currency;
import ru.liga.curses.MakeFutureCurse;
import ru.liga.utils.ValidateCommands;

import java.time.LocalDate;
import java.util.Locale;

public class App {
    public static void main(String[] args) {
        MakeFutureCurse.MakeNewRate(args);
    }
}
