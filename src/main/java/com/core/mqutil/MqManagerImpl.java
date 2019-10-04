package com.core.mqutil;

import com.core.common.Constants;
import com.core.common.CoreException;
import com.ibm.mq.*;
import com.ibm.mq.constants.CMQC;

public class MqManagerImpl implements MqManager {
    private MqFactory mqFactory;
    private MQQueueManager mqManager;
    private MqConfig config;
    private MQGetMessageOptions cursor;
    private MQMessage mqMessage;

    private MQQueueManager getMqManager()
            throws MQException {
        if (this.mqManager == null || this.mqManager.isConnected() == false || this.mqManager.isOpen() == false)
            this.mqManager = new MQQueueManager(this.config.getQueueManager());

        return this.mqManager;
    }

    public void open(MqConfig config) {
        this.mqFactory = new MqFactory();
        MQEnvironment.hostname = config.getHostName();
        MQEnvironment.port = config.getPort();
        MQEnvironment.channel = config.getChannel();
        MQEnvironment.userID = config.getUserId();
        MQEnvironment.password = config.getPassword();
        MQEnvironment.CCSID = config.getCsid();

        this.config = config;
        this.cursor = this.mqFactory.createCursor();
    }

    /**
     * You can send your message directly to MQ
     *
     * @param message
     */
    public void write(String message)
            throws CoreException {
        try {
            MQQueue queue = this.mqFactory.createWriteQueue(this.getMqManager(), this.config.getAccessQueue());
            MQMessage mqMessage = this.mqFactory.createWriteMessage();
            MQPutMessageOptions mqPutMessageOptions = this.mqFactory.createPutMessageOptions();

            mqMessage.clearMessage();
            mqMessage.write(message.getBytes("UTF8"));
            queue.put(mqMessage, mqPutMessageOptions);

            System.out.println("========================================================================================================");
            System.out.println("Write to MQ :" + this.config.getAccessQueue());
            System.out.println(message);
            System.out.println("========================================================================================================");
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new CoreException(ex);
        }
    }

    /**
     * This method close the connection, but messages don't remove yet
     */
    public void close() {
        try {
            this.mqFactory.close();

            if(mqManager != null) {
                if(this.mqManager.isConnected())
                    this.mqManager.disconnect();

                if(this.mqManager.isOpen())
                    this.mqManager.close();

                this.mqManager = null;
            }
        }
        catch(Exception ex) {
            System.out.println("========================================================================================================");
            System.out.println("MQManger => close()");
            ex.printStackTrace();
            System.out.println("========================================================================================================");
        }
    }

    public String read() throws CoreException {
        try {
            MQQueue queue = this.mqFactory.createReadQueue(this.getMqManager(), this.config.getAccessQueue());
            this.mqMessage = this.mqFactory.createReadMessage();
            queue.get(this.mqMessage, this.cursor);

            byte[] array = new byte[this.mqMessage.getMessageLength()];
            this.mqMessage.readFully(array);
            String text = new String(array, "UTF-8");

            System.out.println("========================================================================================================");
            System.out.println("Read from MQ :" + this.config.getAccessQueue());
            System.out.println(text);
            System.out.println("========================================================================================================");

            return text;
        } catch (MQException ex) {
            if (ex.reasonCode == 2033)
                throw new CoreException(Constants.NO_MESSAGE_AVAILABLE_IN_MQ_CODE, Constants.NO_MESSAGE_AVAILABLE_IN_MQ_DESC);

            ex.printStackTrace();
            throw new CoreException(ex);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CoreException(ex);
        }
    }

    public void next() {
        this.cursor.options = CMQC.MQGMO_WAIT | CMQC.MQGMO_BROWSE_NEXT;
        this.cursor.matchOptions = CMQC.MQMO_NONE;
    }

    private void remove(MQMessage message)
            throws CoreException {
        try {
            this.cursor.options = CMQC.MQGMO_MSG_UNDER_CURSOR;
            MQQueue queue = this.mqFactory.createReadQueue(this.getMqManager(), this.config.getAccessQueue());

            queue.get(message, this.cursor);

            System.out.println("This Message removed from MQ");
            System.out.println("========================================================================================================");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new CoreException(ex);
        }
    }

    public void remove() throws CoreException {
        this.remove(this.mqMessage);
    }
}
