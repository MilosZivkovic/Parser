package com.ef.converters;

import com.beust.jcommander.IStringConverter;
import com.ef.CliProperties;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OptionLocalDateTimeConverter implements IStringConverter<LocalDateTime> {

    @Override
    public LocalDateTime convert(String s) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(CliProperties.OPTION_DATE_FORMAT);
        return LocalDateTime.parse(s, dateTimeFormatter);
    }
}
