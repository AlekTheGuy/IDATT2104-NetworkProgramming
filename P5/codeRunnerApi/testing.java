package no.ntnu.codeRunner;

import java.io.IOException;
import java.util.Scanner;

public class testing {

    public static void main(String[] args) throws IOException {
        Process p = Runtime.getRuntime().exec("bash tmp.txt");
        StringBuilder outputString = new StringBuilder();

        Scanner s = new Scanner(p.getInputStream());
        while (s.hasNextLine()) {
            outputString.append(s.nextLine() + "\n");
        }
        System.out.println(outputString.toString());
    }
}
