package com.ef.config;

import com.beust.jcommander.JCommander;
import com.ef.processors.CsvFileProcessor;
import com.ef.processors.RestrictAccessProcessor;
import com.ef.properties.CliArguments;
import com.ef.repository.AccessLogRepository;
import com.ef.services.CsvFileService;
import com.ef.services.RestrictAccessService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Configuration
public class ApplicationConfiguration {

    @Bean
    @Profile("!test")
    public CliArguments cliArguments(ApplicationArguments applicationArguments) {
        CliArguments arguments = new CliArguments();
        JCommander.newBuilder().addObject(arguments).build().parse(applicationArguments.getSourceArgs());
        return arguments;
    }

    @Bean
    @Profile("!test")
    @Order(1)
    public CsvFileProcessor csvFileProcessor(CsvFileService csvFileService, CliArguments cliArguments) {
        return new CsvFileProcessor(csvFileService, cliArguments);
    }

    @Bean
    @Profile("!test")
    @Order(2)
    public RestrictAccessProcessor restrictAccessService(RestrictAccessService restrictAccessService, CliArguments cliArguments) {
        return new RestrictAccessProcessor(restrictAccessService, cliArguments);
    }

    @Bean
    public CsvFileService csvFileService(AccessLogRepository accessLogRepository) {
        return new CsvFileService(accessLogRepository);
    }

    @Bean
    public RestrictAccessService restrictAccessService(AccessLogRepository accessLogRepository) {
        return new RestrictAccessService(accessLogRepository);
    }
}