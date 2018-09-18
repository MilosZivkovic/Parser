package com.ef.config.cli.converters;

import com.beust.jcommander.IStringConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OptionLocalDateTimeConverter implements IStringConverter<LocalDateTime> {

    @Override
    public LocalDateTime convert(String s) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");
        return LocalDateTime.parse(s, dateTimeFormatter);
    }
}
