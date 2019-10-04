package com.core.mqutil;

import com.core.common.CoreException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
        config.setPassword("Test");
        config.setPort(1919);
        config.setQueueManager("Test");
        config.setUserId("Test");
        config.setPassword("Test");
        this.mqManager.open(new MqConfig());
    }

    @Test
    public void write() throws CoreException {
        this.mqManager.write(TEST_MESSAGE);
    }

    @Test
    public void read() throws CoreException {
        this.mqManager.next();
        String message = this.mqManager.read();
        assert message.equals(TEST_MESSAGE);
    }

    @Test
    public void remove() throws CoreException {
        this.mqManager.remove();
    }

    @Before
    public void close() throws IOException {
        this.mqManager.close();
    }
}
