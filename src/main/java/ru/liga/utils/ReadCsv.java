package ru.liga.utils;

import ru.liga.curses.Currency;
import ru.liga.curses.MakeFutureCurse;
import ru.liga.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/*
    класс для чтения .csv
    файл для чтения определяется по валюте
 */
public class ReadCsv {
    //текст ошибки, если файл с курсами валют не найден
    private static final String FILE_NOT_FOUND = "Не найден файл с данными для валюты ";
    //расширение файла
    private static final String CSV = ".csv";

    public static List<String> readLast7Strings(Currency currency) throws CsvException {

        List<String> result = new ArrayList<>();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream fileInputStream = classLoader.getResourceAsStream(currency.name() + CSV)) {
            if (fileInputStream == null) {
                throw new CsvException(FILE_NOT_FOUND + currency.name());
            }
            try (InputStreamReader fileReader = new InputStreamReader(fileInputStream);
                 BufferedReader csvReader = new BufferedReader(fileReader)) {
                for (int i = 0; i <= MakeFutureCurse.RATES_FOR_FUTURE_CURSE; i++) {
                    String readString = csvReader.readLine();
                    //первая строка - заголовок, пропускаем
                    if (i > 0) {
                        result.add(readString);
                    }
                }
            }
        } catch (IOException e) {
            throw new CsvException(FILE_NOT_FOUND + currency.name());
        }
        return result;
    }
}
