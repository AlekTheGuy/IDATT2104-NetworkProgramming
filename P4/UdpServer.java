
// Java program to illustrate Server side
// Implementation using DatagramSocket
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        // Step 1 : Create a socket to listen at port 1234
        DatagramSocket ds = new DatagramSocket(1234);
        byte[] receive = new byte[65535];
        byte[] send = null;
        InetAddress ip = InetAddress.getLocalHost();
        DatagramPacket DpRecive = null;
        DatagramPacket DpSend = null;
        while (true) {
            String values[] = new String[3];

            for (int i = 0; i < values.length; i++) {
                // Step 2 : create a DatgramPacket to receive the data.
                DpRecive = new DatagramPacket(receive, receive.length);

                // Step 3 : revieve the data in byte buffer.
                ds.receive(DpRecive);

                values[i] = data(receive).toString();
                System.out.println("Client:-" + data(receive));

                // Exit the server if the client sends "bye"
                if (data(receive).toString().equals("bye")) {
                    System.out.println("Client sent bye.....EXITING");
                    break;
                }

                // Clear the buffer after every message.
                receive = new byte[65535];
            }

            int num1 = Integer.parseInt(values[0]);
            int num2 = Integer.parseInt(values[2]);
            String result = "";
            if (values[1].equals("+")) {
                result = num1 + " + " + num2 + " = " + (num1 + num2);
            } else if (values[1].equals("-"))
                result = num1 + " - " + num2 + " = " + (num1 - num2);
            else
                result = "Dette blei jo litt feil";

            int port = DpRecive.getPort();
            send = result.getBytes();
            DpSend = new DatagramPacket(send, send.length, ip, port);
            ds.send(DpSend);

        }
    }

    // A utility method to convert the byte array
    // data into a string representation.
    public static StringBuilder data(byte[] a) {
        if (a == null)
            return null;
        StringBuilder ret = new StringBuilder();
        int i = 0;
        while (a[i] != 0) {
            ret.append((char) a[i]);
            i++;
        }
        return ret;
    }
}