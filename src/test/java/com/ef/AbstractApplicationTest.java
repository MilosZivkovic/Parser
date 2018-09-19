package com.ef;

import com.ef.config.ApplicationConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@MybatisTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@Import(ApplicationConfiguration.class)
public abstract class AbstractApplicationTest {

    protected static final String REQUESTS_LOG_FILE = "data/requests.log";

    @Test
    public void testRequestLogFile() {
        testResourceFileExists(REQUESTS_LOG_FILE);
    }

    private void testResourceFileExists(String fileName) {
        String path = getResource(fileName);
        File file = new File(path);
        assertTrue(file.exists());
        assertTrue(file.canRead());
        assertTrue(file.isFile());
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
