package io.github.jeesk;

import java.io.Closeable;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class CloseUtil {

    public static <T extends Closeable> void closeIO(T... io) {
        for (Closeable temp : io) {
            try {
                if (null != temp) {
                    temp.close();
                }
            } catch (Exception e) {
            }
        }
    }

    public static void closeSocket(ServerSocket socket) {
        try {
            if (null != socket) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }

    public static void closeSocket(Socket socket) {
        try {
            if (null != socket) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }

    public static void closeSocket(DatagramSocket socket) {
        try {
            if (null != socket) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }
}
