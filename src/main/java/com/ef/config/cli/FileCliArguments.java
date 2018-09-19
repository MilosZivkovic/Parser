package com.ef.config.cli;

import com.beust.jcommander.Parameter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileCliArguments {
    @Parameter(names = "--accesslog", required = true, description = "Path to the access log file to be proccessed")
    private String filePath;

    public Optional<Path> file() {
        if (filePath == null) {
            return Optional.empty();
        }

        final Path path = Paths.get(filePath);
        return Files.exists(path) ? Optional.of(path) : Optional.empty();
    }
}
