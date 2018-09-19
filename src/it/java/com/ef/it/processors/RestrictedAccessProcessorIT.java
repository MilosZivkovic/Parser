package com.ef.it.processors;

import com.ef.it.AbstractE2ETest;
import com.ef.model.RestrictData;
import com.ef.processors.CsvFileProcessor;
import com.ef.processors.RestrictAccessProcessor;
import com.ef.repository.AccessLogRepository;
import com.ef.services.CsvFileService;
import com.ef.services.RestrictAccessService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RestrictedAccessProcessorIT extends AbstractE2ETest {

    @Autowired
    private CsvFileService csvFileService;

    @Autowired
    private RestrictAccessService restrictAccessService;

    @Autowired
    private AccessLogRepository accessLogRepository;

    private RestrictAccessProcessor restrictAccessProcessor;

    @Before
    public void setUp() {
        Path accessFile = Paths.get(getResource(ACCESS_LOG_FILE));
        CsvFileProcessor csvFileProcessor = new CsvFileProcessor(csvFileService);
        csvFileProcessor.run("--accesslog=" + accessFile.toString());

        restrictAccessProcessor = new RestrictAccessProcessor(restrictAccessService);

    }

    @Test
    public void testDailyThreshold() {
        String startDate = "2017-01-01.00:00:00";
        RestrictData.Duration duration = RestrictData.Duration.DAILY;
        int threshold = 500;

        RestrictData restrictData = buildRestrictData(startDate, duration, threshold);
        String[] args = buildArgs(restrictData.getStartDate(), duration, threshold, null);
        restrictAccessProcessor.run(args);

        List<String> restrictedIps = accessLogRepository.findRestrictedIps(restrictData);
        assertTrue(restrictedIps.contains(IP_ADDRESS_DAILY));
    }

    @Test
    public void testHourlyThreshold() {
        String startDate = "2017-01-01.15:00:00";
        RestrictData.Duration duration = RestrictData.Duration.DAILY;
        int threshold = 200;

        RestrictData restrictData = buildRestrictData(startDate, duration, threshold);
        String[] args = buildArgs(restrictData.getStartDate(), duration, threshold, null);
        restrictAccessProcessor.run(args);

        List<String> restrictedIps = accessLogRepository.findRestrictedIps(restrictData);
        assertTrue(restrictedIps.contains(IP_ADDRESS_HOURLY));
    }

    private RestrictData buildRestrictData(String startDateValue, RestrictData.Duration duration, int threshold) {
        LocalDateTime startDate = LocalDateTime.parse(startDateValue, formatter);
        LocalDateTime endDate = duration == RestrictData.Duration.DAILY ? startDate.plusDays(1) : startDate.plusHours(1);
        return new RestrictData(startDate, endDate, threshold);
    }
}
