package com.ef.processors;

import com.beust.jcommander.JCommander;
import com.ef.properties.CliArguments;
import com.ef.services.CsvFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CsvFileProcessor implements CommandLineRunner {

    private CsvFileService csvFileService;

    public CsvFileProcessor(CsvFileService csvFileService) {
        this.csvFileService = csvFileService;
    }

    @Override
    public void run(String... args) throws Exception {
        CliArguments arguments = new CliArguments();
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        String filePath = arguments.getFilePath();
        if (filePath == null) {
            log.info("Skipping file processing");
            return;
        }
        else {
            log.info("Processing File: " + filePath);
        }

        try {
            long startTime = System.nanoTime();
            csvFileService.processFile(filePath);
            long seconds = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
            log.info("Finished processing file in: " + seconds + " seconds");
        }
        catch (IllegalArgumentException e) {
            log.error("Could not process file: " + filePath);
        }
    }
}
