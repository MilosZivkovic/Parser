package com.ef.processors;

import com.ef.model.RestrictedIp;
import com.ef.properties.CliArguments;
import com.ef.services.RestrictAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import java.util.List;

@Slf4j
public class RestrictAccessProcessor implements CommandLineRunner {

    private RestrictAccessService restrictAccessService;

    private CliArguments arguments;

    public RestrictAccessProcessor(RestrictAccessService restrictAccessService, CliArguments arguments) {
        this.restrictAccessService = restrictAccessService;
        this.arguments = arguments;
    }

    @Override
    public void run(String... args) throws Exception {
        List<RestrictedIp> restrictedIps = restrictAccessService
            .restrictIpAddresses(arguments.getStartDate(), arguments.getDuration(), arguments.getThreshold());
        restrictedIps.forEach(restrictedIp -> log.info("Found possible DDOS attempt, ip address: " + restrictedIp.getIpAddress()));
    }
}
