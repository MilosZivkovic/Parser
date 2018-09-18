package com.ef;

import com.ef.config.ApplicationConfiguration;
import com.ef.services.CsvFileService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
@MybatisTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@Import(ApplicationConfiguration.class)
public abstract class AbstractApplicationTest {

    protected static final String ACCESS_LOG_FILE = "data/access.log";
    protected static final String REQUESTS_LOG_FILE = "data/requests.log";

    @Autowired
    private CsvFileService csvFileService;

    @Test
    public void testRequestLogFile() {
        testResourceFileExists(REQUESTS_LOG_FILE);
    }

    @Test
    public void testAccessLogFIleExist() {
        testResourceFileExists(ACCESS_LOG_FILE);
    }

    private void testResourceFileExists(String fileName) {
        String path = getResource(fileName);
        File file = new File(path);
        Assert.assertTrue(file.exists());
        Assert.assertTrue(file.canRead());
        Assert.assertTrue(file.isFile());
    }

    protected String getResource(String resourceName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return Objects.requireNonNull(classLoader.getResource(resourceName)).getPath();
    }

    protected List<String> getInsertedIpAddresses() {
        List<String> ips = new ArrayList<>();
        ips.add("192.168.234.82");
        ips.add("192.168.169.194");
        ips.add("192.168.247.138");
        ips.add("192.168.54.139");
        ips.add("192.168.162.248");
        ips.add("192.168.159.178");
        ips.add("192.168.127.83");
        ips.add("192.168.31.251");
        ips.add("192.168.110.220");
        ips.add("192.168.27.46");
        return ips;
    }
}
