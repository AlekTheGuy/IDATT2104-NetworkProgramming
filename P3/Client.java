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

        //Reads input from server and prints to commandline.
        String string1 = reader.readLine();
        String string2 = reader.readLine();
        System.out.println(string1 + "\n" + string2);

        String inputline = readFromCommandLine.nextLine();
        while (!inputline.equals("")) {
            printer.println(inputline);
            String response = reader.readLine();
            System.out.println("Response: " + response);
            inputline = readFromCommandLine.nextLine();
        }

        //Close connections
        reader.close();
        printer.close();
        connection.close();
        readFromCommandLine.close();
    }
}
