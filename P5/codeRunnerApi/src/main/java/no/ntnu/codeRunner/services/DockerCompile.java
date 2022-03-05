package no.ntnu.codeRunner.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

@Service
public class DockerCompile {

    public DockerCompile() {
    }

    public String compileCode(String inputCode) throws IOException, InterruptedException {
        Path fileName = Path.of("tmp.txt");
        StringBuilder outputString = new StringBuilder();
        StringBuilder completeInputString = new StringBuilder();
        completeInputString.append("docker run --rm -ti gcc /bin/bash -c \"echo '");
        completeInputString.append(inputCode);
        completeInputString.append("' >> output.cpp && g++ output.cpp -o output && ./output\"");
        inputCode = completeInputString.toString();

        System.out.println(inputCode);

        Files.writeString(fileName, inputCode);
        Process p = Runtime.getRuntime().exec("bash tmp.txt");
        //Process p = Runtime.getRuntime().exec(inputCode);
        p.waitFor();
        Scanner s = new Scanner(p.getInputStream());
        while (s.hasNextLine()) {
            outputString.append(s.nextLine() + "\n");
        }
        Files.delete(fileName);
        System.out.println(outputString.toString());
        return outputString.toString();
    }

}
