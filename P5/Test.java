import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path fileName = Path.of("test.txt");
        String command = Files.readString(fileName);
        String command2 = "docker run --rm hello-world";

        System.out.println(command);

        System.out.println(execCmd(command));
    }

    public static String execCmd(String cmd) throws java.io.IOException, InterruptedException {
        Process p = Runtime.getRuntime().exec(cmd);
        p.waitFor();
        Scanner s = new Scanner(p.getInputStream());

        // java.util.Scanner s = new
        // java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream());
        StringBuilder outputString = new StringBuilder();
        while (s.hasNextLine()) {
            outputString.append(s.nextLine() + "\n");
        }
        return outputString.toString();
    }
}