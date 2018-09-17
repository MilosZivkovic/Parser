package com.ef;

import com.beust.jcommander.Parameter;
import com.ef.converters.DurationConverter;
import com.ef.converters.OptionLocalDateTimeConverter;
import com.ef.model.RestrictData;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Args {

    @Parameter(names = "--startDate", required = true, converter = OptionLocalDateTimeConverter.class)
    private LocalDateTime startDate;

    @Parameter(names = "--duration", required = true, converter = DurationConverter.class)
    private RestrictData.Duration duration;

    @Parameter(names = "--threshold", required = true)
    private int threshold;

    @Parameter(names = "--accesslog")
    private String filePath;
}
