package com.michaelmiklavcic.watcher;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.*;
import java.net.*;
import java.nio.file.Path;

import org.junit.*;

import com.michaelmiklavcic.TestParent;

public class EndToEndIT extends TestParent {

    @Before
    public void setUp() throws Exception {
        setupTempDir();
    }

    @After
    public void tearDown() throws Exception {
        cleanupTempDir();
    }

    @Test
    public void picks_up_file_and_executes_registered_handler() throws Exception {
        Server server = new Server();
        server.start();
        WatcherApp.main(new String[] { "com.michaelmiklavcic.watcher.EndToEndIT$Client" });
        server.join();
        assertThat(server.isCalled(), equalTo(true));
        assertThat(server.getPath(), equalTo("path=/foo/bar/baz.txt"));
    }

    public static class Server extends Thread {
        private int portNumber = 25000;
        private boolean called = false;
        private String path;

        public void run() {
            try (ServerSocket serverSocket = new ServerSocket(portNumber);
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
                String line = in.readLine();
                if (null != line && line.length() > 0) {
                    called = true;
                    path = line;
                }
            } catch (IOException e) {
                throw new RuntimeException("Server failed", e);
            }
        }

        public String getPath() {
            return path;
        }

        public boolean isCalled() {
            return called;
        }
    }

    public static class Client implements Handler {
        public void call(Event event) {
            String hostName = "localhost";
            int portNumber = 25000;
            try (Socket kkSocket = new Socket(hostName, portNumber);
                    PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);) {
                Path path = event.getPaths()
                                 .get(0);
                out.println("path=" + path.toString());
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " + hostName);
                System.exit(1);
            }
        }
    }

}
