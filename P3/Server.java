import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    public static void main(String[] args) throws InterruptedException {
        Server thread1 = new Server();

        thread1.run();
        System.out.println("Thread 1 running");

        thread1.join();
        System.out.println("Thread 1 finished");
    }

    public void run() {
        final int PORT = 1250;
        boolean isRunning = true;

        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("waiting...");
            Socket connection = server.accept();

            //Open for communication with the client
            InputStreamReader readConnection = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(readConnection);
            PrintWriter printer = new PrintWriter(connection.getOutputStream(), true);

            //Sends messages to client.
            printer.println("Connected to server!");
            printer.println("Type: ");

            runCalculatorLoop(reader, printer);
            System.out.println("closing connection!");

            //Close connection
            reader.close();
            printer.close();
            connection.close();
            server.close();
        } catch (IOException e) {
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
        System.out.println("in calculator loop!");
        boolean runLoop = true;
        String inputLine;
        while (runLoop == true){
            System.out.println("Ready to read input!");
            inputLine = reader.readLine();
            if (inputLine == "exit") {
                runLoop = false;
                System.out.println("exiting loop!");
            } else {
                printer.println(calculateExpression(inputLine));
            }
        }
    }
}
