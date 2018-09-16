package com.ef;

import com.ef.mappers.AccessLogMapper;
import com.ef.model.AccessLog;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Order(1)
@Component
public class CsvFileProcessor implements ApplicationRunner {

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (args.containsOption(CliProperties.ACCESS_LOG_OPTION)) {
            List<String> files = args.getOptionValues(CliProperties.ACCESS_LOG_OPTION);
            files.forEach(this::processFile);
        }
    }

    private void processFile(String filePath) {
        if (!isValidFile(filePath)) {
            log.warn("Provided file does not exist or is not readable.");
            return;
        }
        log.info("Processing File: " + filePath);

        long startTime = System.nanoTime();
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.parallel().forEach(this::processLine);
        } catch (IOException e) {
            e.printStackTrace();
        }
        long executionTime = System.nanoTime() - startTime;
        log.info("Execution Time: " + executionTime);
    }

    private void processLine(String line) {
        try {
            AccessLog logData = parseAccessLog(line);
            accessLogMapper.insertAccessLog(logData);
        } catch (Exception e) {
            log.error("Could not save line: " + line, e);
        }
    }

    private AccessLog parseAccessLog(String line) {
        CsvMapper mapper = new CsvMapper();
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        CsvSchema schema = mapper.schemaFor(AccessLog.class);
        schema = schema.withColumnSeparator('|');

        try {
            return mapper.readerFor(AccessLog.class).with(schema).readValue(line);
        } catch (IOException e) {
            throw new RuntimeException("Invalid log line: " + e.getMessage(), e);
        }
    }

    private boolean isValidFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }
}
