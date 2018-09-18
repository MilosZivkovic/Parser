package com.ef.processors;

import com.beust.jcommander.JCommander;
import com.ef.config.cli.CliArguments;
import com.ef.services.RestrictAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

@Slf4j
public class RestrictAccessProcessor implements CommandLineRunner {

    private final RestrictAccessService restrictAccessService;

    public RestrictAccessProcessor(RestrictAccessService restrictAccessService) {
        this.restrictAccessService = restrictAccessService;
    }

    @Override
    public void run(String... args) {
        CliArguments arguments = new CliArguments();
        JCommander.newBuilder().addObject(arguments).build().parse(args);
        List<String> restrictedIps = restrictAccessService
            .restrictIpAddresses(arguments.getStartDate(), arguments.getDuration(), arguments.getThreshold());
        log.info("Found possible DDOS attempts: ");
        restrictedIps.forEach(restrictedIp -> log.info("ip address: " + restrictedIp));
    }
}
