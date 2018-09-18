package com.ef.processors;

import com.beust.jcommander.JCommander;
import com.ef.config.cli.CliArguments;
import com.ef.services.CsvFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CsvFileProcessor implements CommandLineRunner {

    private final CsvFileService csvFileService;

    public CsvFileProcessor(CsvFileService csvFileService) {
        this.csvFileService = csvFileService;
    }

    @Override
    public void run(String... args) {
        CliArguments arguments = getArguments(args);
        if (arguments.file().isPresent())
        {
            processFile(arguments.file().get());
        }
    }

    private void processFile(Path file) {
        try {
            log.info("Processing File: " + file);
            long startTime = System.nanoTime();
            csvFileService.processFile(file);
            long seconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
            log.info("Finished processing file in: " + seconds + " seconds");
        } catch (IllegalArgumentException e) {
            log.error("Could not process file: " + file);
        }
    }

    private CliArguments getArguments(String[] args) {
        CliArguments arguments = new CliArguments();
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        return arguments;
    }
}
