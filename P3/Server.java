import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        final int PORT = 1250;

        ServerSocket server = new ServerSocket(PORT);
        boolean runServer = true;
        int threadCounter = 0;
        System.out.println("Server started");

        while(runServer) {
            Socket serverClient = server.accept();
            System.out.println("A client has connected");
            ServerThread sct = new ServerThread(serverClient, threadCounter);
            sct.start();
            threadCounter++;
        }

        server.close();
    }
}

class ServerThread extends Thread{
    Socket serverClient;
    int clientNo;

    public ServerThread(Socket inSocket, int counter) {
        this.serverClient = inSocket;
        this.clientNo = counter;
    }

    public void run() {

        try {        
        //Open for communication with the client
        InputStreamReader readConnection = new InputStreamReader(serverClient.getInputStream());
        BufferedReader reader = new BufferedReader(readConnection);
        PrintWriter printer = new PrintWriter(serverClient.getOutputStream(), true);

        runCalculatorLoop(reader, printer);
        System.out.println("A client has disconnected");

        //Close connection
        reader.close();
        printer.close();
        serverClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int calculateExpression(String inputString) {
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

    private void runCalculatorLoop(BufferedReader reader, PrintWriter printer) throws IOException {
        boolean runLoop = true;
        String inputLine;
        while (runLoop == true){
            inputLine = reader.readLine();
            if (inputLine.equals("exit")) {
                runLoop = false;
            } else {
                printer.println(calculateExpression(inputLine));
            }
        }
    }
}

