package com.ef.services;

import com.ef.model.AccessIp;
import com.ef.model.RestrictData;
import com.ef.model.RestrictData.Duration;
import com.ef.model.RestrictedIp;
import com.ef.repository.AccessLogRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestrictAccessService {

    private AccessLogRepository accessLogRepository;

    public RestrictAccessService(AccessLogRepository accessLogRepository) {
        this.accessLogRepository = accessLogRepository;
    }

    public List<RestrictedIp> restrictIpAddresses(LocalDateTime startDate, Duration duration, Integer threshold) {
        RestrictData restrictData = new RestrictData(startDate, duration, threshold);
        List<RestrictedIp> restrictedIps = accessLogRepository.findDDOSAttempts(restrictData);
        accessLogRepository.restrictIpAddresses(restrictedIps.stream()
            .map(restrictedIp -> new AccessIp(restrictedIp.getIpAddress(), true, "Suspected DDOS attack"))
            .collect(Collectors.toList()));
        return restrictedIps;
    }

}
