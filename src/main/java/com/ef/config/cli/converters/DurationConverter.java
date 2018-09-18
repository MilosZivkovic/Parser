package com.ef.config.cli.converters;

import com.beust.jcommander.IStringConverter;
import com.ef.model.RestrictData;

public class DurationConverter implements IStringConverter<RestrictData.Duration> {
    @Override
    public RestrictData.Duration convert(String s) {
        return RestrictData.Duration.valueOf(s.toUpperCase());
    }
}
