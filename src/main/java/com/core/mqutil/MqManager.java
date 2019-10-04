package com.core.mqutil;

import com.core.common.CoreException;

import java.io.Closeable;

public interface MqManager extends Closeable {
    /**
     * @see MqManagerImpl#open(MqConfig)
     */
    public void open(MqConfig config);

    /**
     * @see MqManagerImpl#write(String)
     */
    void write(String message)
            throws CoreException;

    /**
     * @see MqManagerImpl#remove()
     */
    void remove()
            throws CoreException;

    /**
     * @see MqManagerImpl#read()
     */
    String read()
            throws CoreException;

    /**
     * @see MqManagerImpl#next()
     */
    void next();
}
