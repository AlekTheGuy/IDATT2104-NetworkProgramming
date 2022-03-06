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
        String completeInputString = "docker run --rm -i gcc /bin/bash -c \"echo '" + inputCode
                + "' >> test.cpp && g++ test.cpp -o test && ./test\"";
        System.out.println(inputCode);
        Files.writeString(fileName, completeInputString);
        Scanner s = new Scanner(Runtime.getRuntime().exec("bash tmp.txt").getInputStream());
        while (s.hasNextLine()) {
            outputString.append(s.nextLine() + "\n");
        }
        Files.delete(fileName);
        return outputString.toString();
    }
}