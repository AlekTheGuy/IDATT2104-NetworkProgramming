import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UdpServer {

    public static void main(String[] args)
            throws IOException {

        DatagramSocket ds = new DatagramSocket(1234);

        byte[] buf = null;

        DatagramPacket DpReceive = null;
        DatagramPacket DpSend = null;

        while (true) {
            buf = new byte[65535];

            DpReceive = new DatagramPacket(buf, buf.length);
            ds.receive(DpReceive);
            String inputString = new String(buf, 0, buf.length);
            inputString = inputString.trim();

            if (inputString.equals("exit")) {
                break;
            }

            System.out.println("Equation Received:- " + inputString);

            int result = calculateExpression(inputString);
            String res = Integer.toString(result);
            buf = res.getBytes();
            int port = DpReceive.getPort();

            DpSend = new DatagramPacket(buf, buf.length, InetAddress.getLocalHost(), port);
            ds.send(DpSend);
        }
    }

    private static int calculateExpression(String inputString) {
        if (inputString.contains("+")) {
            String[] arrOfString = inputString.split("[+]");
            int sum = Integer.parseInt(arrOfString[0]) + Integer.parseInt(arrOfString[1]);
            return sum;
        } else if (inputString.contains("-")) {
            String[] arrOfString = inputString.split("[-]");
            int sum = Integer.parseInt(arrOfString[0]) - Integer.parseInt(arrOfString[1]);
            return sum;
        }
        return 0;
    }
}
