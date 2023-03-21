package ru.liga.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
    класс для вывода результата работы, ошибок и прочих сообщений пользователю
 */
public class WriteOutput {
    public static void WriteOutputMessage(String message){
        System.out.println(message);
    }

    //форматирование даты для вывода
    public static String dateFormatToOutput(LocalDate date) {
        String dayOfWeek = switch (date.getDayOfWeek()) {
            case MONDAY -> "Пн";
            case TUESDAY -> "Вт";
            case WEDNESDAY -> "Ср";
            case THURSDAY -> "Чт";
            case FRIDAY -> "Пт";
            case SATURDAY -> "Сб";
            case SUNDAY -> "Вс";
        };
        return dayOfWeek + " " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }
}
