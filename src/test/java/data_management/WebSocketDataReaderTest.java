package cardio_generator.generators;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import com.data_management.WebSocketDataReader;
import org.java_websocket.server.WebSocketServer;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.junit.jupiter.api.*;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class WebSocketDataReaderTest {
    private static final int PORT = 8887;
    private WebSocketTestServer server;
    private DataStorage dataStorage;
    private WebSocketDataReader dataReader;

    @BeforeEach
    public void setUp() throws Exception {
        dataStorage = new DataStorage();
        dataReader = new WebSocketDataReader();
        server = new WebSocketTestServer(new InetSocketAddress(PORT));
        server.start();
        Thread.sleep(1000); // Wait for the server to start
    }

    @AfterEach
    public void tearDown() throws Exception {
        server.stop();
    }

    @Test
    public void testReadData() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        server.setLatch(latch);

        // Start reading data from WebSocket server
        new Thread(() -> dataReader.readData(dataStorage, "ws://localhost:" + PORT)).start();

        // Give some time for the connection to establish
        Thread.sleep(1000);

        long timestamp = System.currentTimeMillis();
        String message = String.format("1,%d,WhiteBloodCells,100.0", timestamp);
        server.broadcast(message);

        latch.await(5, TimeUnit.SECONDS);

        List<PatientRecord> records = dataStorage.getRecords(1, timestamp, timestamp + 1);
        assertEquals(1, records.size(), "The number of records stored should be 1");

        PatientRecord record = records.get(0);
        assertNotNull(record, "The patient record should not be null");
        assertEquals(1, record.getPatientId(), "The patient ID should be 1");
        assertEquals("WhiteBloodCells", record.getRecordType(), "The record type should be WhiteBloodCells");
        assertEquals(100.0, record.getMeasurementValue(), "The measurement value should be 100.0");
        assertEquals(timestamp, record.getTimestamp(), "The timestamp should match the sent timestamp");
    }

    private static class WebSocketTestServer extends WebSocketServer {
        private CountDownLatch latch;

        public WebSocketTestServer(InetSocketAddress address) {
            super(address);
        }

        public void setLatch(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void onOpen(WebSocket conn, ClientHandshake handshake) {
            System.out.println("Server: new connection opened");
        }

        @Override
        public void onClose(WebSocket conn, int code, String reason, boolean remote) {
            System.out.println("Server: connection closed");
        }

        @Override
        public void onMessage(WebSocket conn, String message) {
            System.out.println("Server: message received - " + message);
            if (latch != null) {
                latch.countDown();
            }
        }

        @Override
        public void onError(WebSocket conn, Exception ex) {
            ex.printStackTrace();
        }

        @Override
        public void onStart() {
            System.out.println("Server started successfully");
        }
    }
}