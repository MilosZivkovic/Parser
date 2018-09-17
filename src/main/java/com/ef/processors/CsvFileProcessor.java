package com.ef.processors;

import com.ef.CliArguments;
import com.ef.services.CsvFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
        String file = arguments.getFilePath();
        csvFileService.processFile(file);
    }
}
