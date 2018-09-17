package com.ef.config;

import com.beust.jcommander.JCommander;
import com.ef.Args;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Args args(ApplicationArguments applicationArguments) {
        Args args = new Args();
        JCommander.newBuilder().addObject(args).build().parse(applicationArguments.getSourceArgs());
        return args;
    }
}
