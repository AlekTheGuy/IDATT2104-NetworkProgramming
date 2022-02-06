import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        final int PORT = 1250;

        //Uses a scanner to read from commandline
        Scanner readFromCommandLine = new Scanner(System.in);
        System.out.println("Input the name of the machine running the server: ");
        String serverName = readFromCommandLine.nextLine();

        //Establish connection to server.
        Socket connection = new Socket(serverName, PORT);
        System.out.println("Connection established");

        //Creates an avenue of communication to the server
        InputStreamReader readConnection = new InputStreamReader(connection.getInputStream());
        BufferedReader reader = new BufferedReader(readConnection);
        PrintWriter printer = new PrintWriter(connection.getOutputStream(), true);

        runCalculatorLoop(reader, printer, readFromCommandLine);

        //Close connections
        reader.close();
        printer.close();
        connection.close();
        readFromCommandLine.close();
    }

    private static void runCalculatorLoop(BufferedReader reader, PrintWriter printer, Scanner readFromCommandLine) throws IOException {
        System.out.println("in calculator loop!");
        boolean runLoop = true;
        String inputLine;
        while (runLoop == true) {
            inputLine = readFromCommandLine.nextLine();
            if (inputLine.equals("exit")) {
                printer.println(inputLine);
                runLoop = false;
                System.out.println("Exiting loop!");
            } else {
                printer.println(inputLine);
                System.out.println(reader.readLine());
            }
        }
    }
}
