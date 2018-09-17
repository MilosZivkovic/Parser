package com.ef.processors;

import com.ef.CliProperties;
import com.ef.services.CsvFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Order(1)
@Component
public class CsvFileProcessor implements ApplicationRunner {

    private CsvFileService csvFileService;

    public CsvFileProcessor(CsvFileService csvFileService) {
        this.csvFileService = csvFileService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption(CliProperties.ACCESS_LOG_OPTION)) {
            List<String> files = args.getOptionValues(CliProperties.ACCESS_LOG_OPTION);
            files.forEach(csvFileService::processFile);
        }
    }
}
