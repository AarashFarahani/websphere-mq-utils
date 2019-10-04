package com.core.mqutil;

import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;

import java.io.Closeable;

public class MqFactory implements Closeable {
    private MQQueue readQueue;
    private MQQueue writeQueue;

    protected MQGetMessageOptions createCursor() {
        MQGetMessageOptions cursor = new MQGetMessageOptions();
        cursor.options = CMQC.MQGMO_WAIT | CMQC.MQGMO_BROWSE_FIRST;
        cursor.matchOptions = CMQC.MQMO_NONE;
        cursor.waitInterval = 5000;

        return cursor;
    }

    protected MQQueue createReadQueue(MQQueueManager mqQueueManager, String accessQueue)
            throws MQException {
        if(this.readQueue == null || this.readQueue.isOpen() == false) {
            this.readQueue = mqQueueManager.accessQueue(accessQueue,
                    CMQC.MQOO_FAIL_IF_QUIESCING | CMQC.MQOO_INPUT_SHARED | CMQC.MQOO_BROWSE);
        }

        return this.readQueue;
    }

    protected MQMessage createReadMessage() {
        MQMessage message = new MQMessage();
        message.correlationId = CMQC.MQMI_NONE;
        message.messageId = CMQC.MQMI_NONE;

        return message;
    }

    protected MQQueue createWriteQueue(MQQueueManager mqQueueManager, String accessQueue)
            throws MQException {
        if (this.writeQueue == null || this.writeQueue.isOpen() == false)
            this.writeQueue = mqQueueManager.accessQueue(accessQueue, CMQC.MQOO_OUTPUT);

        return this.writeQueue;
    }

    protected MQMessage createWriteMessage() {
        MQMessage message = new MQMessage();
        message.format = CMQC.MQFMT_STRING;

        return message;
    }

    protected MQPutMessageOptions createPutMessageOptions() {
        MQPutMessageOptions putMessageOptions = new MQPutMessageOptions();
        putMessageOptions.options = CMQC.MQPMO_LOGICAL_ORDER;

        return putMessageOptions;
    }

    public void close() {
        try {
            if (this.readQueue != null)
                this.readQueue.close();
            if (this.writeQueue != null)
                this.writeQueue.close();
        } catch (MQException ex) {
            ex.printStackTrace();
        }
    }
}
