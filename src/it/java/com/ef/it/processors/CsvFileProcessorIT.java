package com.ef.it.processors;

import com.ef.it.AbstractE2ETest;
import com.ef.model.AccessLog;
import com.ef.processors.CsvFileProcessor;
import com.ef.repository.AccessLogRepository;
import com.ef.services.CsvFileService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class CsvFileProcessorIT extends AbstractE2ETest {

    @Autowired
    private CsvFileService csvFileService;

    @Autowired
    private AccessLogRepository accessLogRepository;

    private CsvFileProcessor csvFileProcessor;

    private Path accessFile;

    @Before
    public void setUp() {
        accessFile = Paths.get(getResource(ACCESS_LOG_FILE));
        csvFileProcessor = new CsvFileProcessor(csvFileService);
    }

    @Test
    public void csvFileProcessorTest() {
        String argument = "--accesslog=" + accessFile.toString();
        csvFileProcessor.run(argument);

        List<String> ipAddresses = getIpAddresses();
        List<AccessLog> accessLogs = accessLogRepository.getAccessLog(ipAddresses);
        assertTrue(accessLogs.stream().map(AccessLog::getIpAddress).collect(Collectors.toList()).containsAll(ipAddresses));
    }

}
