package com.ef.processors;

import com.ef.properties.CliArguments;
import com.ef.services.CsvFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CsvFileProcessor implements CommandLineRunner {

    private CsvFileService csvFileService;

    private CliArguments arguments;

    public CsvFileProcessor(CsvFileService csvFileService, CliArguments arguments) {
        this.csvFileService = csvFileService;
        this.arguments = arguments;
    }

    @Override
    public void run(String... args) throws Exception {
        String filePath = arguments.getFilePath();
        if (filePath == null) {
            log.info("Skipping file processing");
            return;
        }
        else {
            log.info("Processing File: " + filePath);
        }
        long startTime = System.nanoTime();

        csvFileService.processFile(filePath);

        long seconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
        log.info("Finished processing file in: " + seconds + " seconds");
    }
}
