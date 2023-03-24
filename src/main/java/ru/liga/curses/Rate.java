package ru.liga.curses;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Rate {
    private Currency currency;
    private LocalDate date;
    private int nominal;
    private double rate;

    public Rate(Currency currency, LocalDate date, int nominal, double rate) {
        this.currency = currency;
        this.date = date;
        this.nominal = nominal;
        this.rate = rate;
    }

    public Rate(Currency currency, int nominal, double rate) {
        this.currency = currency;
        this.nominal = nominal;
        this.rate = rate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return date.format(DateTimeFormatter.ofPattern("EE dd.MM.yyyy",
                Locale.getDefault())) + " " + new BigDecimal(rate / nominal).setScale(2, RoundingMode.HALF_UP);
    }
}
