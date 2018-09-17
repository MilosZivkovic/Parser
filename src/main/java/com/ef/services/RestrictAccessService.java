package com.ef.services;

import com.ef.mappers.AccessLogMapper;
import com.ef.model.AccessIp;
import com.ef.model.RestrictData;
import com.ef.model.RestrictData.Duration;
import com.ef.model.RestrictedIp;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestrictAccessService {

    private AccessLogMapper accessLogMapper;

    public RestrictAccessService(AccessLogMapper accessLogMapper) {
        this.accessLogMapper = accessLogMapper;
    }

    public List<RestrictedIp> restrictIpAddresses(LocalDateTime startDate, Duration duration, Integer threshold) {
        RestrictData restrictData = new RestrictData(startDate, duration, threshold);
        List<RestrictedIp> restrictedIps = accessLogMapper.findDDOSAttempts(restrictData);
        accessLogMapper.restrictIpAddresses(restrictedIps.stream()
            .map(restrictedIp -> new AccessIp(restrictedIp.getIpAddress(), true, "Suspected DDOS attack"))
            .collect(Collectors.toList()));
        return restrictedIps;
    }


}
