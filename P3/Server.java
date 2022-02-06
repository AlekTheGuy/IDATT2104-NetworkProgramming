import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        final int PORT = 1250;
        boolean isRunning = true;

        ServerSocket server = new ServerSocket(PORT);
        System.out.println("waiting...");
        Socket connection = server.accept();

        //Open for communication with the client
        InputStreamReader readConnection = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(readConnection);
        PrintWriter printer = new PrintWriter(connection.getOutputStream(), true);

        //Sends messages to client.
        printer.println("Connected to server!");
        printer.println("Type: ");

        printer.println("2");
        printer.println("message 1");
        printer.println("message 2");

        runCalculatorLoop(reader, printer);

        //Get data from client
        //String inputLine = reader.readLine();
        while (isRunning == true) {
            System.out.println("Ready to read input!");
            String inputLine = reader.readLine();
            while (inputLine != null) {

                System.out.println("Input has been read!");

                int sum = calculateExpression(inputLine); 

                System.out.println(sum);

                /*
                if (inputLine.contains("+")) {
                    input = inputLine.split("[+]");
                } else if (inputLine.contains("-")) {
                    input = inputLine.split("[-]");
                }
                */

                inputLine = null;
    
                /*
                System.out.println("A client wrote: " + inputLine);
                printer.println("you wrote: " + inputLine);
                inputLine = reader.readLine();
                */
            }
        }

        System.out.println("closing connection!");

        //Close connection
        reader.close();
        printer.close();
        connection.close();
        server.close();
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

    private static void runCalculatorLoop(BufferedReader reader, PrintWriter printer) throws IOException {
        System.out.println("in calculator loop!");
        boolean runLoop = true;
        String inputLine;
        while (runLoop == true){
            System.out.println("Ready to read input!");
            inputLine = reader.readLine();
            if (inputLine != "exit") {
                printer.println(calculateExpression(inputLine));
            } else {
                runLoop = false;
                System.out.println("exiting loop!");
            }
        }
    }
}
