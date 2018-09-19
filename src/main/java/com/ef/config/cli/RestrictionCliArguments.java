package com.ef.config.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.ef.config.cli.converters.DurationConverter;
import com.ef.config.cli.converters.OptionLocalDateTimeConverter;
import com.ef.model.RestrictData;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Parameters(separators = "=")
public class RestrictionCliArguments {

    @Parameter(names = "--startDate", required = true, converter = OptionLocalDateTimeConverter.class, description = "Start date of the database search")
    private LocalDateTime startDate;

    @Parameter(names = "--duration", required = true, converter = DurationConverter.class, description = "Duration of the range of search")
    private RestrictData.Duration duration;

    @Parameter(names = "--threshold", required = true, description = "Threshold of the restriction")
    private int threshold;
}
