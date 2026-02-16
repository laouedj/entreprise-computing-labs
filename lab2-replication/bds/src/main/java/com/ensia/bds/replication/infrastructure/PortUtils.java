package com.ensia.bds.replication.infrastructure;

import java.io.IOException;
import java.net.ServerSocket;

public class PortUtils {

    public static boolean isPortFree(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
