package com.ef.config;

import com.beust.jcommander.JCommander;
import com.ef.CliArguments;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public CliArguments arugments(ApplicationArguments applicationArguments) {
        CliArguments arguments = new CliArguments();
        JCommander.newBuilder().addObject(arguments).build().parse(applicationArguments.getSourceArgs());
        return arguments;
    }
}
