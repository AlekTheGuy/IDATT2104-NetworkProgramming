import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    public static void main(String[] args) throws IOException {
        int port = 9000;
        ServerSocket serverSocket = new ServerSocket(port);
        String line = "";

        Socket socket = serverSocket.accept();

        System.out.println("Wow it worked");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        OutputStream outputStream = socket.getOutputStream();

        outputStream.write("HTTP/1.1 200 OK\r\n".getBytes());
        outputStream.write("Content-Type: text/html;charset=utf-8\r\n".getBytes());
        outputStream.write("\r\n".getBytes());
        outputStream.write("<html><body><h1>".getBytes());

        outputStream.write("Heyyyyyyyyyyyyyyyyy".getBytes());
        outputStream.write("</h1>".getBytes());
        outputStream.write("<ul>".getBytes());
        line = bufferedReader.readLine();
        while(!(line == null)){
            outputStream.write("<li>".getBytes());
            outputStream.write(line.getBytes());
            outputStream.write("</li>".getBytes());
            line = bufferedReader.readLine();
        }
        outputStream.write("</ul></body></html>".getBytes());
        
        outputStream.flush();
        bufferedReader.close();
        socket.close();

    }
    
}