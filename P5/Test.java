import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {

        String inputCode = Files.readString(Path.of("test.txt"));

        Path fileName = Path.of("tmp.txt");
        StringBuilder outputString = new StringBuilder();

        Files.writeString(fileName, inputCode);
        Scanner s = new Scanner(Runtime.getRuntime().exec("bash tmp.txt").getInputStream());
        while (s.hasNextLine()) {
            outputString.append(s.nextLine() + "\n");
        }
        Files.delete(fileName);

        System.out.println(outputString.toString());

        // Path fileName = Path.of("test.txt");
        // String command3 = Files.readString(fileName);

        // String command = "bash test.txt";
        // System.out.println(execCmd(command));
        // }

        // public static String execCmd(String cmd) throws java.io.IOException,
        // InterruptedException {
        // java.util.Scanner s = new
        // java.util.Scanner(Runtime.getRuntime().exec(cmd).getInputStream());
        // StringBuilder outputString = new StringBuilder();
        // while (s.hasNextLine()) {
        // outputString.append(s.nextLine() + "\n");
        // }
        // return outputString.toString();
        // }
    }
}
