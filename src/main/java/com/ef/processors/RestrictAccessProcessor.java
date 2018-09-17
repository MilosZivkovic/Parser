package com.ef.processors;

import com.ef.Args;
import com.ef.model.RestrictedIp;
import com.ef.services.RestrictAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@Order(2)
public class RestrictAccessProcessor implements CommandLineRunner {

    private RestrictAccessService restrictAccessService;

    private Args arguments;

    public RestrictAccessProcessor(RestrictAccessService restrictAccessService, Args arguments) {
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
