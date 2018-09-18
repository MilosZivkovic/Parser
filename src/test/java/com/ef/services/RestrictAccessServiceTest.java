package com.ef.services;

import com.ef.AbstractApplicationTest;
import com.ef.model.RestrictData;
import com.ef.model.RestrictedIp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RestrictAccessServiceTest extends AbstractApplicationTest {

    public static final String IP_ADDRESS = "192.168.234.82";
    @Autowired
    private CsvFileService csvFileService;

    @Autowired
    private RestrictAccessService restrictAccessService;

    @Test
    public void restrictIpAddressesTest() {
        String path = getResource(AbstractApplicationTest.REQUESTS_LOG_FILE);
        csvFileService.processFile(path);

        LocalDateTime startDate = LocalDateTime.of(2017, 1, 1, 0, 0);
        RestrictData.Duration duration = RestrictData.Duration.DAILY;
        int threshold = 10;
        List<RestrictedIp> restrictedIps = restrictAccessService.restrictIpAddresses(startDate, duration, threshold);

        assertTrue(restrictedIps.stream()
            .map(RestrictedIp::getIpAddress)
            .collect(Collectors.toList())
            .contains(IP_ADDRESS));

        assertEquals(11, (int) restrictedIps.stream()
            .filter(restrictedIp -> restrictedIp.getIpAddress().equals(IP_ADDRESS))
            .map(RestrictedIp::getOcc)
            .findFirst().orElse(0));
    }

    @Test
    public void restrictIpAddressesTestCase1() {
        LocalDateTime startDate = LocalDateTime.of(2017, 1, 1, 0, 0, 0);
        RestrictData.Duration duration = RestrictData.Duration.DAILY;
        int threshold = 500;
        testRestrictedIpAddresses("192.168.102.136", startDate, duration, threshold);
    }

    @Test
    public void restrictIpAddressesTestCase2() {
        LocalDateTime startDate = LocalDateTime.of(2017, 1, 1, 15, 0, 0);
        RestrictData.Duration duration = RestrictData.Duration.HOURLY;
        int threshold = 200;
        testRestrictedIpAddresses("192.168.11.231", startDate, duration, threshold);
    }

    private void testRestrictedIpAddresses(String expectedIpAddress, LocalDateTime startDate, RestrictData.Duration duration, int threshold) {
        String path = getResource(AbstractApplicationTest.ACCESS_LOG_FILE);
        csvFileService.processFile(path);

        List<RestrictedIp> restrictedIps = restrictAccessService.restrictIpAddresses(startDate, duration, threshold);

        assertTrue(restrictedIps.stream()
            .map(RestrictedIp::getIpAddress)
            .collect(Collectors.toList())
            .contains(expectedIpAddress));
    }
}