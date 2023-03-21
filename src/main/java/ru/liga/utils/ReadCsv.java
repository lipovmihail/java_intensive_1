package ru.liga.utils;

import ru.liga.curses.CURRENCY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/*
    класс для чтения .csv
    для первого дз читает первые 7 строк, кроме заголовка
    файл для чтения определяется по валюте
 */
public class ReadCsv {
    public static final String FILE_NOT_FOUND = "Не найден файл с данными для валюты ";

    public static ArrayList<String> readLast7Strings(CURRENCY currency) throws CsvException {
        ArrayList<String> result = new ArrayList<>();
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream isFile = classLoader.getResourceAsStream(currency.name() + ".csv")) {
            if (isFile == null) throw new CsvException(FILE_NOT_FOUND + currency.name());
            try (InputStreamReader isFileReader = new InputStreamReader(isFile);
                 BufferedReader csvReader = new BufferedReader(isFileReader)) {
                for (int i = 0; i < 8; i++) {
                    String readedString = csvReader.readLine();
                    if (i > 0) {
                        result.add(readedString);
                    }
                }
            }
        } catch (IOException e) {
            throw new CsvException(FILE_NOT_FOUND + currency.name());
        }
        return result;
    }
}
