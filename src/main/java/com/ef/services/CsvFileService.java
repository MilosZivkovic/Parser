package com.ef.services;

import com.ef.model.AccessLog;
import com.ef.repository.AccessLogRepository;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
public class CsvFileService {

    private AccessLogRepository accessLogRepository;

    public CsvFileService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public void processFile(String filePath) {
        validateFile(filePath);
        try(Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(this::processLine);
        }
        catch (IOException e) {
            throw new RuntimeException("Could not process file: " + filePath, e);
        }
    }

    public void processLine(String line) {
        AccessLog logData = parseAccessLog(line);
        accessLogRepository.insertAccessLog(logData);
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

    private void validateFile(String filePath) {
        File file = new File(filePath);
        if(!isValidFile(file)) {
            throw new IllegalArgumentException("Provided file does not exist or does not have right access privilege");
        }
    }

    private boolean isValidFile(File file) {
        return file.exists() && file.isFile() && file.canRead();
    }

}
