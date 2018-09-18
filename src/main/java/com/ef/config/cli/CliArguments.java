package com.ef.config.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.ef.config.cli.converters.DurationConverter;
import com.ef.config.cli.converters.OptionLocalDateTimeConverter;
import com.ef.model.RestrictData;
import lombok.Data;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Parameters(separators = "=")
public class CliArguments {

    @Parameter(names = "--startDate", required = true, converter = OptionLocalDateTimeConverter.class)
    private LocalDateTime startDate;

    @Parameter(names = "--duration", required = true, converter = DurationConverter.class)
    private RestrictData.Duration duration;

    @Parameter(names = "--threshold", required = true)
    private int threshold;

    @Parameter(names = "--accesslog")
    private String filePath;

    public Optional<Path> file() {
        if (filePath == null) {
            return Optional.empty();
        }

        final Path path = Paths.get(filePath);
        return Files.exists(path) ? Optional.of(path) : Optional.empty();
    }
}
