package com.ef;

import com.ef.mappers.AccessLogMapper;
import com.ef.model.AccessIp;
import com.ef.model.AccessLog;
import com.ef.model.RestrictData;
import com.ef.model.RestrictedIp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
@Order(2)
public class RestrictAccessProcessor implements ApplicationRunner {

    @Autowired
    private AccessLogMapper accessLogMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!validateArguments(args)) {
            log.error("Some Arguments are missing");
            return;
        }

        try {
            String startDateValue = args.getOptionValues(CliProperties.START_DATE_OPTION).get(0);
            String duration = args.getOptionValues(CliProperties.DURATION_OPTION).get(0);
            String threshold = args.getOptionValues(CliProperties.THRESHOLD_OPTION).get(0);

            RestrictData restrictData = new RestrictData();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CliProperties.OPTION_DATE_FORMAT);
            restrictData.setStartDate(LocalDateTime.parse(startDateValue, formatter));
            restrictData.setDuration(RestrictData.Duration.valueOf(duration.toUpperCase()));
            restrictData.setThreshold(Integer.valueOf(threshold));

            List<RestrictedIp> restrictedIps = accessLogMapper.findDDOSAttempts(restrictData);
            restrictedIps.forEach(restrictedIp -> {
                log.info("Found ddos attempt, ip address: " + restrictedIp.getIpAddress());
                AccessIp accessIp = new AccessIp();
                accessIp.setIpAddress(restrictedIp.getIpAddress());
                accessIp.setRestricted(true);
                accessIp.setCause("Possible DDOS attempt");
                accessLogMapper.restrictIpAddress(accessIp);
            });
        } catch (Exception e) {
            log.error("Could not process request: " + e.getMessage(), e);
        }
    }

    private boolean validateArguments(ApplicationArguments args) {
        boolean state = true;
        if (!args.containsOption(CliProperties.START_DATE_OPTION)) {
            log.error("Please provide `startDate` argument");
            state = false;
        }
        if (!args.containsOption(CliProperties.DURATION_OPTION)) {
            log.error("Please provide `duration` argument");
            state = false;
        }
        if (!args.containsOption(CliProperties.THRESHOLD_OPTION)) {
            log.error("Please provide `threshold` argument");
            state = false;
        }

        return state;
    }
}
