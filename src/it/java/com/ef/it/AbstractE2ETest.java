package com.ef.it;

import com.ef.config.ApplicationConfiguration;
import com.ef.model.RestrictData;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
@MybatisTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@Import(ApplicationConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public abstract class AbstractE2ETest {

    protected static final String ACCESS_LOG_FILE = "data/access.log";
    protected static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd.HH:mm:ss");
    protected static final String IP_ADDRESS_DAILY = "192.168.102.136";
    protected static final String IP_ADDRESS_HOURLY = "192.168.11.231";


    protected String[] buildArgs(LocalDateTime startDate, RestrictData.Duration duration, int threshold, String fileName) {
        List<String> args = new ArrayList<>();
        if(fileName != null && !fileName.equals("")) {
            args.add("--accesslog=" + fileName);
        }
        args.add("--startDate=" + startDate.format(formatter));
        args.add("--duration=" + duration);
        args.add("--threshold=" + threshold);
        return args.toArray(new String[0]);
    }

    protected String getResource(String resourceName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return Objects.requireNonNull(classLoader.getResource(resourceName)).getPath();
    }

    protected List<String> getIpAddresses() {
        List<String> ipAddress = new ArrayList<>();
        ipAddress.add("192.168.234.82");
        ipAddress.add("192.168.169.194");
        ipAddress.add("192.168.159.178");
        ipAddress.add("192.168.199.209");
        ipAddress.add("192.168.5.4");
        ipAddress.add("192.168.127.51");
        return ipAddress;
    }
}
