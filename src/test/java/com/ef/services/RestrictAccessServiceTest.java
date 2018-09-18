package com.ef.services;

import com.ef.AbstractApplicationTest;
import com.ef.model.RestrictData;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

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
        testRestrictedIpAddresses(IP_ADDRESS, startDate, duration, threshold);
    }

    @Test
    public void noRestrictedIpsTest() {
        String path = getResource(AbstractApplicationTest.REQUESTS_LOG_FILE);
        csvFileService.processFile(path);

        LocalDateTime startDate = LocalDateTime.of(2022, 6, 1, 15, 32, 12);
        RestrictData.Duration duration = RestrictData.Duration.DAILY;
        int threshold = 500;
        List<String> restrictedIps = restrictAccessService.restrictIpAddresses(startDate, duration, threshold);
        assertTrue(restrictedIps.isEmpty());
    }

    @Test
    public void restrictIpAddressesTestCase1() {
        String path = getResource(AbstractApplicationTest.ACCESS_LOG_FILE);
        csvFileService.processFile(path);

        LocalDateTime startDate = LocalDateTime.of(2017, 1, 1, 0, 0, 0);
        RestrictData.Duration duration = RestrictData.Duration.DAILY;
        int threshold = 500;
        testRestrictedIpAddresses("192.168.102.136", startDate, duration, threshold);
    }

    @Test
    public void restrictIpAddressesTestCase2() {
        String path = getResource(AbstractApplicationTest.ACCESS_LOG_FILE);
        csvFileService.processFile(path);

        LocalDateTime startDate = LocalDateTime.of(2017, 1, 1, 15, 0, 0);
        RestrictData.Duration duration = RestrictData.Duration.HOURLY;
        int threshold = 200;
        testRestrictedIpAddresses("192.168.11.231", startDate, duration, threshold);
    }

    private void testRestrictedIpAddresses(String expectedIpAddress, LocalDateTime startDate, RestrictData.Duration duration,
                                           int threshold) {
        List<String> restrictedIps = restrictAccessService.restrictIpAddresses(startDate, duration, threshold);
        assertTrue(restrictedIps.contains(expectedIpAddress));
    }
}