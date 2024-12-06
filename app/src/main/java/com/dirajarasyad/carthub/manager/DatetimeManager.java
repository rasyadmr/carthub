package com.dirajarasyad.carthub.manager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatetimeManager {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter DATE = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final LocalDateTime datetime;

    public DatetimeManager(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    public DatetimeManager(String datetime) {
        this.datetime = this.convertLDT(datetime);
    }

    public LocalDateTime getLDT() {
        return this.datetime;
    }

    public String getDatetime() {
        return this.datetime.format(FORMATTER);
    }

    public String getDate() {
        return this.datetime.format(DATE);
    }

    public String getTime() {
        return this.datetime.format(TIME);
    }

    private LocalDateTime convertLDT(String datetime) {
        return LocalDateTime.parse(datetime, FORMATTER);
    }
}
