package ru.liga.curses;

import ru.liga.utils.CsvException;
import ru.liga.utils.ReadCsv;
import ru.liga.utils.WriteOutput;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

/*
    самый главный класс :-)
 */
    public class MakeFutureCurse {
    //текст ошибки с описанием поддерживаемых команд
    private static final String ERROR_INFORM_TEXT = "Supported commands: rate CURRENCY tomorrow/rate CURRENCY week. "
            + "Case insensitive.";
    //текст ошибки, если команда в целом верная, но валюта пока не поддерживается
    private static final String ERROR_CURRENCY_TEXT = "Supported CURRENCY: EUR, USD, TRY. Case insensitive.";

    public static void main(String[] args) {
        String messageToUser = "";
        messageToUser = validateInputParams(args);
        if (!messageToUser.equals("SUCCESS")) {
            WriteOutput.WriteOutputMessage(messageToUser);
            return;
        }
        LocalDate currentDate = LocalDate.now();
        currentDate = currentDate.plusDays(1);
        if (args[2].equalsIgnoreCase("tomorrow")) {
            WriteOutput.WriteOutputMessage(WriteOutput.dateFormatToOutput(currentDate) + " - "
                    + getTomorrowRate(CURRENCY.valueOf(args[1].toUpperCase(Locale.ROOT))));
        } else {
            for(String weekDayCource : getNextWeekRate(CURRENCY.valueOf(args[1].toUpperCase(Locale.ROOT)), currentDate)){
                WriteOutput.WriteOutputMessage(weekDayCource);
            }
        }
    }

    //валидация входящих параметров
    private static String validateInputParams(String[] args) {

        if (args.length != 3 || !args[0].equalsIgnoreCase("rate")
                || (!args[2].equalsIgnoreCase("tomorrow")
                && !args[2].equalsIgnoreCase("week"))) {
            return ERROR_INFORM_TEXT;
        }
        try {
            CURRENCY.valueOf(args[1].toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            return ERROR_CURRENCY_TEXT;
        }
        return "SUCCESS";
    }

    //расчет курса на завтра
    private static double getTomorrowRate(CURRENCY currency) {
        ArrayList<String> bankLast7Cources = new ArrayList<>();
        try {
            bankLast7Cources = ReadCsv.readLast7Strings(currency);
        } catch (CsvException e) {
            WriteOutput.WriteOutputMessage(e.getMessage());
        }
        return getOneRate(bankLast7Cources);
    }

    //расчет курса на следующую неделю
    private static ArrayList<String> getNextWeekRate(CURRENCY currency, LocalDate futureDate) {
        ArrayList<String> bankLast7Courses = new ArrayList<>();
        ArrayList<String> resultCourses = new ArrayList<>();
        LocalDate nextDate = futureDate;
        try {
            bankLast7Courses = ReadCsv.readLast7Strings(currency);
        } catch (CsvException e) {
            WriteOutput.WriteOutputMessage(e.getMessage());
        }
        for (int i = 0; i < 7; i++) {
            double rate = getOneRate(bankLast7Courses);
            resultCourses.add(WriteOutput.dateFormatToOutput(nextDate) + " - " + rate);
            bankLast7Courses.remove(6);
            nextDate = nextDate.plusDays(1);
            bankLast7Courses.add(0, "1;" + nextDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                    + ";" + rate + ";" + currency.name());
        }
        return resultCourses;
    }

    //получение одного курса на следукющий день по последним 7 курсам
    private static double getOneRate(ArrayList<String> bankCsvCourses) {
        double finalRate = 0.00;
        for (String csvString : bankCsvCourses) {
            String[] splitCsvString = csvString.split(";");
            double nominal = Double.parseDouble(splitCsvString[0]);
            double rate = Double.parseDouble(splitCsvString[2].replace(',', '.'));
            finalRate += rate / nominal;
        }
        finalRate = finalRate / 7.0;
        BigDecimal result = new BigDecimal(finalRate);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return Double.parseDouble(result.toString());
    }
}
