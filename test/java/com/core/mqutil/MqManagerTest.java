package com.core.mqutil;

import com.core.common.CoreException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MqManagerTest {
    private final static String TEST_MESSAGE = "This message is just for test";

    private MqManager mqManager;

    @Before
    public void open() {
        this.mqManager = new MqManagerImpl();
        MqConfig config = new MqConfig();
        config.setAccessQueue("Test");
        config.setChannel("Test");
        config.setCsid(1208);
        config.setHostName("Test");
        config.setPort(14024);
        config.setQueueManager("Test");
        config.setUserId("Test");
        config.setPassword("Test");
        this.mqManager.open(config);
    }

    @Test
    @Order(1)
    public void write() throws CoreException {
        this.mqManager.write(TEST_MESSAGE);
    }

    @Test
    @Order(2)
    public void read() throws CoreException {
        this.mqManager.next();
        String message = this.mqManager.read();
        assert message.equals(TEST_MESSAGE);
    }

    @Test
    @Order(3)
    public void remove() throws CoreException {
        this.mqManager.remove();
    }

    @After
    public void close() throws IOException {
        this.mqManager.close();
    }
}
