package com.ef;

import lombok.extern.slf4j.Slf4j;
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

@Component
@Slf4j
@Order(1)
public class CsvFileParser implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(args.containsOption(CliOptions.ACCESS_LOG_OPTION)) {
            List<String> files = args.getOptionValues(CliOptions.ACCESS_LOG_OPTION);
            files.forEach(this::processFile);
        }
    }

    private void processFile(String filePath) {
        if(!isValidFile(filePath)) {
            log.warn("Provided file does not exist or is not readable.");
            return;
        }

        try(Stream<String> lines = Files.lines(Paths.get(filePath))) {
            lines.forEach(System.out::println);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidFile(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile() && file.canRead();
    }

}
