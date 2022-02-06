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

        //Get data from client
        String inputLine = reader.readLine();
        while (isRunning == true) {
            while (inputLine != null) {

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
}
