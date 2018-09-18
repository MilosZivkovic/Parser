package com.ef.services;

import com.ef.model.AccessIp;
import com.ef.model.RestrictData;
import com.ef.model.RestrictData.Duration;
import com.ef.repository.AccessLogRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RestrictAccessService {

    private final AccessLogRepository accessLogRepository;

    public RestrictAccessService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public List<String> restrictIpAddresses(LocalDateTime startDate, Duration duration, Integer threshold) {
        LocalDateTime endDate = calculateEndDate(startDate, duration);
        RestrictData restrictData = new RestrictData(startDate, endDate, threshold);
        List<String> restrictedIps = accessLogRepository.findRestrictedIps(restrictData);
        if (restrictedIps.isEmpty()) {
            return Collections.emptyList();
        }

        accessLogRepository.restrictIpAddresses(restrictedIps.stream()
            .map(restrictedIp -> new AccessIp(restrictedIp, true, "Suspected DDOS attack"))
            .collect(Collectors.toList()));
        return restrictedIps;
    }

    private LocalDateTime calculateEndDate(LocalDateTime startDate, Duration duration) {
        switch (duration) {
            case DAILY:
                return startDate.plusDays(1);
            case HOURLY:
                return startDate.plusHours(1);
            default:
                throw new IllegalArgumentException("Invalid duration provided: " + duration);
        }
    }

}
