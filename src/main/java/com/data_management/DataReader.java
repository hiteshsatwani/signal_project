package com.data_management;

public interface DataReader {
    /**
     * Connects to a WebSocket server and continuously receives data.
     *
     * @param dataStorage the storage where data will be stored
     * @param url the WebSocket server URL
     */
    void readData(DataStorage dataStorage, String url);
}