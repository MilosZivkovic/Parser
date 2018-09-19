package com.ef.processors;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import com.ef.config.cli.RestrictionCliArguments;
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
        try {
            RestrictionCliArguments arguments = parseArguments(args);
            List<String> restrictedIps = restrictAccessService
                .restrictIpAddresses(arguments.getStartDate(), arguments.getDuration(), arguments.getThreshold());

            if (!restrictedIps.isEmpty()) {
                log.info("Found possible DDOS attempts: ");
                restrictedIps.forEach(restrictedIp -> log.info("ip address: " + restrictedIp));
            }
        } catch (ParameterException e) {
            log.error("Could not process arguments: " + e.getMessage());

            StringBuilder builder = new StringBuilder();
            e.getJCommander().usage(builder);
            log.error("Please see usage:" + builder.toString());
        }
    }

    private RestrictionCliArguments parseArguments(String[] args) {
        RestrictionCliArguments arguments = new RestrictionCliArguments();
        JCommander.newBuilder()
            .addObject(arguments)
            .acceptUnknownOptions(true)
            .build().parse(args);
        return arguments;
    }
}
