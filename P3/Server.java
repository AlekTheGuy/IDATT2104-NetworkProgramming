import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {
        final int PORT = 1250;

        ServerSocket server = new ServerSocket(PORT);
        boolean runServer = true;
        int threadCounter = 0;
        ArrayList<ServerThread> threads = new ArrayList<>();
        System.out.println("Server started");

        while(runServer) {
            System.out.println("Waiting for connection");
            Socket serverClient = server.accept();
            System.out.println("Client: " + threadCounter + " connected successfully!");
            threads.add(new ServerThread(serverClient, threadCounter));
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
        final int PORT = 1250;

        System.out.println("waiting...");

        try {
        
        //Open for communication with the client
        InputStreamReader readConnection = new InputStreamReader(serverClient.getInputStream());
        BufferedReader reader = new BufferedReader(readConnection);
        PrintWriter printer = new PrintWriter(serverClient.getOutputStream(), true);

        printer.println("test");

        runCalculatorLoop(reader, printer);
        System.out.println("closing connection!");

        //Close connection
        reader.close();
        printer.close();
        serverClient.close();

        } catch (Exception e) {
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
        System.out.println("in calculator loop!");
        boolean runLoop = true;
        String inputLine;
        while (runLoop == true){
            System.out.println("Ready to read input!");
            inputLine = reader.readLine();
            if (inputLine.equals("exit")) {
                runLoop = false;
                System.out.println("exiting loop!");
            } else {
                printer.println(calculateExpression(inputLine));
            }
        }
    }
}

