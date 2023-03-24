package ru.liga.utils;

import ru.liga.curses.Currency;
import ru.liga.curses.Rate;

import java.util.ArrayList;
import java.util.List;

/*
класс, чтобы разложить считанные из .csv данные в структуру курса для дальнейшей обработки
 */
public class ParseCsv {
    public static List<Rate> parseRateFromCsv(List<String> scvRate, Currency currency) {
        List<Rate> parsedRates = new ArrayList<>();
        for (String csvString : scvRate) {
            String[] splitCsvString = csvString.split(";");
            parsedRates.add(new Rate(currency,
                    Integer.parseInt(splitCsvString[0]),
                    Double.parseDouble(splitCsvString[2].replace(',', '.'))
            ));
        }
        return parsedRates;
    }
}
