package com.ef.processors;

import com.ef.CliProperties;
import com.ef.mappers.AccessLogMapper;
import com.ef.model.AccessIp;
import com.ef.model.RestrictData;
import com.ef.model.RestrictedIp;
import com.ef.services.RestrictAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
@Slf4j
@Order(2)
public class RestrictAccessProcessor implements ApplicationRunner {

    private RestrictAccessService restrictAccessService;

    public RestrictAccessProcessor(RestrictAccessService restrictAccessService) {
        this.restrictAccessService = restrictAccessService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!validateArguments(args)) {
            log.error("Some Arguments are missing");
            return;
        }

        String startDateValue = args.getOptionValues(CliProperties.START_DATE_OPTION).get(0);
        String durationValue = args.getOptionValues(CliProperties.DURATION_OPTION).get(0);
        String thresholdValue = args.getOptionValues(CliProperties.THRESHOLD_OPTION).get(0);

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CliProperties.OPTION_DATE_FORMAT);
            LocalDateTime startDate = LocalDateTime.parse(startDateValue, formatter);
            RestrictData.Duration duration = RestrictData.Duration.valueOf(durationValue.toUpperCase());
            Integer threshold = Integer.valueOf(thresholdValue);

            List<RestrictedIp> restrictedIps = restrictAccessService.restrictIpAddresses(startDate, duration, threshold);
            restrictedIps.forEach(restrictedIp -> log.info("Found possible DDOS attempt, ip address: " + restrictedIp.getIpAddress()));
        } catch (DateTimeParseException e) {
            log.error("Invalid startDate format: " + startDateValue + ". please provide date in format " + CliProperties.OPTION_DATE_FORMAT);
        }
        catch (NumberFormatException e) {
            log.error("Invalid threshold value: " + thresholdValue + ". Please provide valid integer value.");
        }
        catch (IllegalArgumentException e) {
            log.error("Invalid value for duration argument: " + durationValue + ". Please ");
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
