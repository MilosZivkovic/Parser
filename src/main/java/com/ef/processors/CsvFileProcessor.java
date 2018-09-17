package com.ef.processors;

import com.ef.CliArguments;
import com.ef.services.CsvFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Order(1)
@Component
public class CsvFileProcessor implements CommandLineRunner {

    private CsvFileService csvFileService;

    private CliArguments arguments;

    public CsvFileProcessor(CsvFileService csvFileService, CliArguments arguments) {
        this.csvFileService = csvFileService;
        this.arguments = arguments;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Processing File: " + arguments.getFilePath());
        long startTime = System.nanoTime();

        csvFileService.processFile(arguments.getFilePath());

        long seconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
        log.info("Finished processing file in: " + seconds + " seconds");
    }
}
