package ru.liga.curses;

import ru.liga.utils.ParseCsv;
import ru.liga.utils.ReadCsv;
import ru.liga.utils.ValidateCommands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*
    самый главный класс :-)
 */
public class MakeFutureCurse {
    //текст ошибки с описанием поддерживаемых команд
    private static final String ERROR_INFORM_TEXT = "Supported commands: rate CURRENCY tomorrow/rate CURRENCY week. "
            + "Supported CURRENCY: EUR, USD, TRY. Case insensitive.";
    //сколько курсов используется для прогноза
    public static final int RATES_FOR_FUTURE_CURSE = 7;
    //на сколько дней вперед считать курсы, когда пришла команда считать на неделю
    private static final int RATES_CALC_FOR_WEEK = 7;
    //курс на завтра
    public static final String TOMORROW = "tomorrow";
    //курс на неделю
    public static final String WEEK = "week";

    public static void MakeNewRate(String[] args) {
        if (!ValidateCommands.isValidInputParams(args)) {
            System.out.println(ERROR_INFORM_TEXT);
        }
        Currency currency = Currency.valueOf(args[1].toUpperCase(Locale.ROOT));
        if (args[2].equalsIgnoreCase(TOMORROW)) {
            System.out.println(getTomorrowRate(currency,
                    ParseCsv.parseRateFromCsv(ReadCsv.readLast7Strings(currency), currency),
                    LocalDate.now()));
        } else {
            for (Rate rate : getNextWeekRate(currency,
                    ParseCsv.parseRateFromCsv(ReadCsv.readLast7Strings(currency), currency),
                    LocalDate.now())) {
                System.out.println(rate);
            }
        }
    }

    //расчет курса на следующий день после переданной даты
    private static Rate getTomorrowRate(Currency currency, List<Rate> rateForCalc, LocalDate CurrentDate) {
        double finalRate = 0.00;
        for (int i = 0; i < RATES_FOR_FUTURE_CURSE; i++) {
            finalRate += rateForCalc.get(i).getRate() / rateForCalc.get(i).getNominal();
        }
        return new Rate(currency, CurrentDate.plusDays(1), 1, finalRate / 7);
    }

    //расчет курса на следующую неделю после переданной даты
    private static List<Rate> getNextWeekRate(Currency currency, List<Rate> rateForCalc, LocalDate currentDate) {
        List<Rate> rateForCalcWithCalculatedRates = new ArrayList<>(rateForCalc);
        List<Rate> resultCourses = new ArrayList<>();
        for (int i = 0; i < RATES_CALC_FOR_WEEK; i++) {
            Rate nextRate = getTomorrowRate(currency, rateForCalcWithCalculatedRates, currentDate.plusDays(i));
            resultCourses.add(nextRate);
            rateForCalcWithCalculatedRates.add(0, nextRate);
        }
        return resultCourses;
    }
}
